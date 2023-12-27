package com.example.eleanor_rigby

import com.mediamonks.wordfilter.LanguageCheckerImpl
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity: FlutterActivity(), MethodChannel.MethodCallHandler {

    private lateinit var methodChannel:MethodChannel
    private val autoCompleteService: AutoCompleteService by lazy {
        AutoCompleteServiceImpl(applicationContext, LanguageCheckerImpl())
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "Example")
        methodChannel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "generateLyrics") {
            val input = call.argument<String>("input") ?: ""
            generateLyrics(input, result)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun generateLyrics(input: String, methodResult: MethodChannel.Result) {
        GlobalScope.launch {
            when (val result = autoCompleteService.getSuggestion(input = input)) {
                is AutoCompleteService.AutoCompleteResult.Success -> {
                    methodResult.success(result.words)
                }

                is AutoCompleteService.AutoCompleteResult.Error -> {
                    methodResult.error("error", "error", "error")
                }
            }
        }
    }
}
