package dev.arie.eleanorrigby

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import java.util.concurrent.Executors

class AppViewModel(application: Application) : AndroidViewModel(application) {
    val modelList = emptyList<ModelState>().toMutableStateList()
    val chatState = ChatState()
    val modelSampleList = emptyList<ModelRecord>().toMutableStateList()
    private var appConfig = AppConfig(
        emptyList(),
        emptyList<ModelRecord>().toMutableList()
    )
    private val application = getApplication<Application>()
    private val appDirFile = application.getExternalFilesDir("")
    private val gson = Gson()
    private val localIdSet = emptySet<String>().toMutableSet()

    companion object {
        const val AppConfigFilename = "app-config.json"
        const val ModelConfigFilename = "mlc-chat-config.json"
        const val ParamsConfigFilename = "ndarray-cache.json"
    }

    init {
        loadAppConfig()
    }

    private fun loadAppConfig() {
        val appConfigFile = File(appDirFile, AppConfigFilename)
        val jsonString: String = if (!appConfigFile.exists()) {
            application.assets.open(AppConfigFilename).bufferedReader().use { it.readText() }
        } else {
            appConfigFile.readText()
        }
        appConfig = gson.fromJson(jsonString, AppConfig::class.java)
        modelList.clear()
        localIdSet.clear()
        modelSampleList.clear()
        for (modelRecord in appConfig.modelList) {
            val modelDirFile = File(appDirFile, modelRecord.localId)
            val modelConfigFile = File(modelDirFile, ModelConfigFilename)
            if (modelConfigFile.exists()) {
                val modelConfigString = modelConfigFile.readText()
                val modelConfig = gson.fromJson(modelConfigString, ModelConfig::class.java)
                modelConfig.localId = modelRecord.localId
                modelConfig.modelLib = modelRecord.modelLib
                addModelConfig(modelConfig)
            } else {
                // dwnld
            }
        }
    }

    private fun addModelConfig(modelConfig: ModelConfig) {
        require(!localIdSet.contains(modelConfig.localId))
        localIdSet.add(modelConfig.localId)
        modelList.add(
            ModelState(
                modelConfig,
                File(appDirFile, modelConfig.localId)
            )
        )
    }

    inner class ModelState(
        val modelConfig: ModelConfig,
        private val modelDirFile: File
    ) {
        var modelInitState = mutableStateOf(ModelInitState.Initializing)
        private var paramsConfig = ParamsConfig(emptyList())
        val progress = mutableStateOf(0)
        val total = mutableStateOf(1)
        private val gson = Gson()

        init {
            switchToInitializing()
        }

        private fun switchToInitializing() {
            val paramsConfigFile = File(modelDirFile, ParamsConfigFilename)
            if (paramsConfigFile.exists()) {
                loadParamsConfig()
                switchToIndexing()
            } else {
                // dwnld
            }
        }

        private fun loadParamsConfig() {
            val paramsConfigFile = File(modelDirFile, ParamsConfigFilename)
            require(paramsConfigFile.exists())
            val jsonString = paramsConfigFile.readText()
            paramsConfig = gson.fromJson(jsonString, ParamsConfig::class.java)
        }

        private fun switchToIndexing() {
            modelInitState.value = ModelInitState.Indexing
            progress.value = 0
            total.value = modelConfig.tokenizerFiles.size + paramsConfig.paramsRecords.size
            for (tokenizerFilename in modelConfig.tokenizerFiles) {
                val file = File(modelDirFile, tokenizerFilename)
                if (file.exists()) {
                    ++progress.value
                } else {
                    // dwnld
                }
            }
            for (paramsRecord in paramsConfig.paramsRecords) {
                val file = File(modelDirFile, paramsRecord.dataPath)
                if (file.exists()) {
                    ++progress.value
                } else {
                    // dwnld
                }
            }
            if (progress.value < total.value) {
                switchToPaused()
            } else {
                switchToFinished()
            }
        }

        private fun switchToPaused() {
            modelInitState.value = ModelInitState.Paused
        }

        private fun switchToFinished() {
            modelInitState.value = ModelInitState.Finished
        }

        fun startChat() {
            chatState.requestReloadChat(
                modelConfig.localId,
                modelConfig.modelLib,
                modelDirFile.absolutePath
            )
        }
    }

    inner class ChatState {
        val messages = emptyList<MessageData>().toMutableStateList()
        val report = mutableStateOf("")
        val modelName = mutableStateOf("")
        private var modelChatState = mutableStateOf(ModelChatState.Ready)
            @Synchronized get
            @Synchronized set
        private val backend = ChatModule()
        private var modelLib = ""
        private var modelPath = ""
        private val executorService = Executors.newSingleThreadExecutor()

        private fun mainResetChat() {
            executorService.submit {
                callBackend { backend.resetChat() }
                viewModelScope.launch {
                    clearHistory()
                    switchToReady()
                }
            }
        }

        private fun clearHistory() {
            messages.clear()
            report.value = ""
        }


        private fun switchToResetting() {
            modelChatState.value = ModelChatState.Resetting
        }

        private fun switchToGenerating() {
            modelChatState.value = ModelChatState.Generating
        }

        private fun switchToReloading() {
            modelChatState.value = ModelChatState.Reloading
        }

        private fun switchToReady() {
            modelChatState.value = ModelChatState.Ready
        }

        private fun switchToFailed() {
            modelChatState.value = ModelChatState.Failed
        }

        private fun callBackend(callback: () -> Unit): Boolean {
            try {
                callback()
            } catch (e: Exception) {
                viewModelScope.launch {
                    val stackTrace = e.stackTraceToString()
                    val errorMessage = e.localizedMessage
                    appendMessage(
                        MessageRole.Bot,
                        "EleanorRigby failed\n\nStack trace:\n$stackTrace\n\nError message:\n$errorMessage"
                    )
                    switchToFailed()
                }
                return false
            }
            return true
        }

        fun requestResetChat() {
            require(interruptable())
            interruptChat(
                prologue = {
                    switchToResetting()
                },
                epilogue = {
                    mainResetChat()
                }
            )
        }

        private fun interruptChat(prologue: () -> Unit, epilogue: () -> Unit) {
            // prologue runs before interruption
            // epilogue runs after interruption
            require(interruptable())
            when (modelChatState.value) {
                ModelChatState.Ready -> {
                    prologue()
                    epilogue()
                }

                ModelChatState.Generating -> {
                    prologue()
                    executorService.submit {
                        viewModelScope.launch { epilogue() }
                    }
                }

                else -> {
                    require(false)
                }
            }
        }

        fun requestReloadChat(modelName: String, modelLib: String, modelPath: String) {
            if (this.modelName.value == modelName && this.modelLib == modelLib && this.modelPath == modelPath) {
                return
            }
            require(interruptable())
            interruptChat(
                prologue = {
                    switchToReloading()
                },
                epilogue = {
                    mainReloadChat(modelName, modelLib, modelPath)
                }
            )
        }

        private fun mainReloadChat(modelName: String, modelLib: String, modelPath: String) {
            clearHistory()
            this.modelName.value = modelName
            this.modelLib = modelLib
            this.modelPath = modelPath
            executorService.submit {
                viewModelScope.launch {
                    Toast.makeText(application, "Initialize...", Toast.LENGTH_SHORT).show()
                }
                if (!callBackend {
                        backend.unload()
                        backend.reload(modelLib, modelPath)
                    }) return@submit
                viewModelScope.launch {
                    Toast.makeText(application, "Ready to chat", Toast.LENGTH_SHORT).show()
                    switchToReady()
                }
            }
        }

        fun requestGenerate(prompt: String) {
            require(chatable())
            switchToGenerating()

            executorService.submit {
                appendMessage(MessageRole.User, prompt)
                appendMessage(MessageRole.Bot, "")

                val requestOne = callBackend {
                    backend.prefill(prompt)
                    Log.e("Arie-Log: ", "backend.prefill(prompt)")
                }
                if (!requestOne) {
                    return@submit
                }

                while (!backend.stopped() && !backend.message.contains("Instruct")) {
                    val requestTwo = callBackend {
                        backend.decode()
                        Log.e("Arie-Log: ", "backend.decode(): ${backend.message}")
                        viewModelScope.launch { updateMessage(MessageRole.Bot, backend.message) }
                    }

                    if (!requestTwo) {
                        Log.e("Arie-Log: ", "!requestTwo")
                        return@submit
                    }

                    if (modelChatState.value != ModelChatState.Generating) {
                        Log.e("Arie-Log: ", "!= ModelChatState.Generating")
                        return@submit
                    }
                }

                viewModelScope.launch {
                    report.value = backend.runtimeStatsText()
                    if (modelChatState.value == ModelChatState.Generating) switchToReady()
                }
            }
        }

        private fun appendMessage(role: MessageRole, text: String) {
            messages.add(MessageData(role, text))
        }


        private fun updateMessage(role: MessageRole, text: String) {
            messages[messages.size - 1] = MessageData(role, text)
        }

        fun chatable(): Boolean {
            return modelChatState.value == ModelChatState.Ready
        }

        fun interruptable(): Boolean {
            return modelChatState.value == ModelChatState.Ready
                    || modelChatState.value == ModelChatState.Generating
                    || modelChatState.value == ModelChatState.Failed
        }
    }
}

enum class ModelInitState {
    Initializing,
    Indexing,
    Paused,
    Finished
}

enum class ModelChatState {
    Generating,
    Resetting,
    Reloading,
    Ready,
    Failed
}

enum class MessageRole {
    Bot,
    User
}

data class MessageData(val role: MessageRole, val text: String, val id: UUID = UUID.randomUUID())

data class AppConfig(
    @SerializedName("model_libs") val modelLibs: List<String>,
    @SerializedName("model_list") val modelList: MutableList<ModelRecord>,
)

data class ModelRecord(
    @SerializedName("local_id") val localId: String,
    @SerializedName("model_lib") val modelLib: String
)

data class ModelConfig(
    @SerializedName("model_lib") var modelLib: String,
    @SerializedName("local_id") var localId: String,
    @SerializedName("tokenizer_files") val tokenizerFiles: List<String>
)

data class ParamsRecord(
    @SerializedName("dataPath") val dataPath: String
)

data class ParamsConfig(
    @SerializedName("records") val paramsRecords: List<ParamsRecord>
)
