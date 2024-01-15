package dev.arie.eleanorrigby

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun TransparentTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    fontSize: TextUnit,
    textSelectionColor: Color,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = textSelectionColor,
        backgroundColor = textSelectionColor.copy(alpha = 0.4f)
    )
    val noteTextColor = Color(0xB3000000)

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            cursorBrush = SolidColor(noteTextColor),
            textStyle = textStyle.copy(
                fontSize = fontSize,
                color = noteTextColor
            ), keyboardOptions = keyboardOptions
        ) { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (text.isBlank()) {
                    Text(
                        modifier = Modifier.alpha(alpha = 0.5F),
                        text = hint,
                        style = textStyle,
                        fontSize = fontSize,
                        color = Color.Black
                    )
                }
            }
            innerTextField()
        }
    }
}
