package dev.arie.eleanorrigby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by lazy {
        AppViewModel(application)
    }

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                SketchScreen(
                    state = viewModel.chatState,
                    noteColor = Color(color = 0xFFFFE0AE),
                    startSession = {
                        viewModel.modelList.first().startChat()
                    },
                    generateLyrics = { input ->
                        viewModel.chatState.requestGenerate(input)
                    }
                )
            }
        }
    }
}
