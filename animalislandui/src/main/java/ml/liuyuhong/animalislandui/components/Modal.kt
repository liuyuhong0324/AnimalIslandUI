package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ml.liuyuhong.animalislandui.theme.*

val AnimalModalShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    
    moveTo(0.501f * w, 0.005f * h)
    cubicTo(0.704f * w, 0.01f * h, 0.796f * w, 0.017f * h, 0.825f * w, 0.027f * h)
    cubicTo(0.872f * w, 0.045f * h, 0.939f * w, 0.044f * h, 0.978f * w, 0.17f * h)
    cubicTo(1f * w, 0.254f * h, 1f * w, 0.365f * h, 0.99f * w, 0.505f * h)
    cubicTo(0.979f * w, 0.558f * h, 0.971f * w, 0.598f * h, 0.965f * w, 0.633f * h)
    cubicTo(0.956f * w, 0.689f * h, 0.979f * w, 0.77f * h, 0.964f * w, 0.865f * h)
    cubicTo(0.953f * w, 0.928f * h, 0.921f * w, 0.966f * h, 0.869f * w, 0.979f * h)
    cubicTo(0.821f * w, 0.986f * h, 0.773f * w, 0.992f * h, 0.726f * w, 0.995f * h)
    lineTo(0.507f * w, 1f * h)
    lineTo(0.283f * w, 0.995f * h)
    cubicTo(0.234f * w, 0.992f * h, 0.184f * w, 0.987f * h, 0.133f * w, 0.979f * h)
    cubicTo(0.081f * w, 0.966f * h, 0.05f * w, 0.928f * h, 0.039f * w, 0.865f * h)
    cubicTo(0.023f * w, 0.77f * h, 0.047f * w, 0.689f * h, 0.037f * w, 0.633f * h)
    cubicTo(0.031f * w, 0.595f * h, 0.023f * w, 0.552f * h, 0.013f * w, 0.505f * h)
    cubicTo(-0.006f * w, 0.365f * h, -0.002f * w, 0.254f * h, 0.024f * w, 0.17f * h)
    cubicTo(0.064f * w, 0.045f * h, 0.13f * w, 0.045f * h, 0.174f * w, 0.028f * h)
    cubicTo(0.204f * w, 0.017f * h, 0.303f * w, 0.009f * h, 0.474f * w, 0.005f * h)
    close()
}

@Composable
fun AnimalModal(
    onDismissRequest: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    onConfirm: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(AnimalModalShape)
                .background(BgColorContent)
                .padding(48.dp, 48.dp, 32.dp, 48.dp)
        ) {
            // Close Button
            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 16.dp, y = (-16).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = TextColorBody.copy(alpha = 0.6f)
                )
            }

            Column {
                Text(
                    text = title,
                    color = TextColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                Box(modifier = Modifier.padding(bottom = 20.dp)) {
                    Column {
                        content()
                    }
                }

                if (onConfirm != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimalButton(
                            text = "Cancel",
                            type = ButtonType.DEFAULT,
                            onClick = onDismissRequest
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        AnimalButton(
                            text = "Confirm",
                            type = ButtonType.PRIMARY,
                            onClick = onConfirm
                        )
                    }
                }
            }
        }
    }
}
