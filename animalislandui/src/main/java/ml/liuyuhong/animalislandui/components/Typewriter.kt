package ml.liuyuhong.animalislandui.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun AnimalTypewriter(
    text: String,
    modifier: Modifier = Modifier,
    speed: Long = 90,
    trigger: Any? = null,
    autoPlay: Boolean = true,
    onDone: (() -> Unit)? = null,
    content: @Composable (String) -> Unit
) {
    var displayedText by remember(trigger) { mutableStateOf(if (autoPlay) "" else text) }

    if (autoPlay) {
        LaunchedEffect(text, trigger) {
            displayedText = ""
            for (i in 0..text.length) {
                displayedText = text.substring(0, i)
                if (i < text.length) {
                    delay(speed)
                }
            }
            onDone?.invoke()
        }
    }

    content(displayedText)
}
