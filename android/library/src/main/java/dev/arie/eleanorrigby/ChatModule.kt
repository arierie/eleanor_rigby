package dev.arie.eleanorrigby

import org.apache.tvm.Device
import org.apache.tvm.Function
import org.apache.tvm.Module

class ChatModule {
    private var reloadFunc: Function
    private val unloadFunc: Function
    private val prefillFunc: Function
    private val decodeFunc: Function
    private val getMessage: Function
    private val stoppedFunc: Function
    private val resetChatFunc: Function
    private val runtimeStatsTextFunc: Function
    private val llmChat: Module

    init {
        val createFunc = Function.getFunction("mlc.llm_chat_create")!!
        llmChat = createFunc.pushArg(Device.opencl().deviceType).pushArg(0).invoke().asModule()
        reloadFunc = llmChat.getFunction("reload")
        unloadFunc = llmChat.getFunction("unload")
        prefillFunc = llmChat.getFunction("prefill")
        decodeFunc = llmChat.getFunction("decode")
        getMessage = llmChat.getFunction("get_message")
        stoppedFunc = llmChat.getFunction("stopped")
        resetChatFunc = llmChat.getFunction("reset_chat")
        runtimeStatsTextFunc = llmChat.getFunction("runtime_stats_text")
    }

    fun unload() {
        unloadFunc.invoke()
    }

    fun reload(modelLib: String, modelPath: String?) {
        val libPrefix = modelLib.replace('-', '_') + "_"
        var systemLibFunc = Function.getFunction("runtime.SystemLib")!!
        systemLibFunc = systemLibFunc.pushArg(libPrefix)
        val lib = systemLibFunc.invoke().asModule()
        reloadFunc = reloadFunc.pushArg(lib).pushArg(modelPath)
        reloadFunc.invoke()
    }

    fun resetChat() {
        resetChatFunc.invoke()
    }

    fun prefill(input: String?) {
        val systemPrompt = "Instruct:You are an AI song writer, your responsible is to auto complete sentence of based on the input that will become a song lyrics. Be concise, clear, and safe. Start with completing the following sentence:"
        val outputPrompt = "Output:"
        val prompt = "$systemPrompt$input\n$outputPrompt"
        prefillFunc.pushArg(prompt).invoke()
    }

    val message: String
        get() = getMessage.invoke().asString()

    fun runtimeStatsText(): String {
        return runtimeStatsTextFunc.invoke().asString()
    }

    fun evaluate() {
        llmChat.getFunction("evaluate").invoke()
    }

    fun stopped(): Boolean {
        return stoppedFunc.invoke().asLong() != 0L
    }

    fun decode() {
        decodeFunc.invoke()
    }
}
