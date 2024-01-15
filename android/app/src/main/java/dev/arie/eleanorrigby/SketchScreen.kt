package dev.arie.eleanorrigby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchScreen(
    state: AppViewModel.ChatState,
    noteColor: Color,
    startSession: () -> Unit,
    generateLyrics: (input: String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = noteColor,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = "Eleanor Rigby",
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.alpha(alpha = 0.5F)
                    )
                },
                actions = {
                    IconButton(onClick = { startSession() }) {
                        Icon(
                            imageVector = Icons.Outlined.RestartAlt,
                            contentDescription = "Start session",
                            tint = Color(0xB3000000)
                        )
                    }
                    IconButton(onClick = { generateLyrics(state.content.value) }) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = "Generate lyrics",
                            tint = Color(0xB3000000)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteColor)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            TransparentTextField(
                text = state.title.value,
                hint = "Title",
                onValueChange = {
                    state.title.value = it
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 25.sp,
                textSelectionColor = Color(0xFF3369ff),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentTextField(
                modifier = Modifier.fillMaxHeight(),
                text = state.content.value,
                hint = "Text",
                onValueChange = {
                    state.content.value = it
                },
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                textSelectionColor = Color(0xFF3369ff),
            )
        }
    }
}
