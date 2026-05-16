package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

enum class ButtonSize {
    SMALL, MIDDLE, LARGE
}

enum class ButtonType {
    DEFAULT, PRIMARY, DANGER, GHOST, LINK
}

@Composable
fun AnimalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.MIDDLE,
    enabled: Boolean = true,
    loading: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val height = when (size) {
        ButtonSize.SMALL -> 32.dp
        ButtonSize.MIDDLE -> 45.dp
        ButtonSize.LARGE -> 48.dp
    }

    val padding = when (size) {
        ButtonSize.SMALL -> PaddingValues(horizontal = 16.dp)
        ButtonSize.MIDDLE -> PaddingValues(horizontal = 20.dp)
        ButtonSize.LARGE -> PaddingValues(horizontal = 32.dp)
    }

    val fontSize = when (size) {
        ButtonSize.SMALL -> 12.sp
        ButtonSize.MIDDLE -> 14.sp
        ButtonSize.LARGE -> 16.sp
    }

    val borderRadius = when (size) {
        ButtonSize.SMALL -> 12.dp
        ButtonSize.MIDDLE -> 50.dp
        ButtonSize.LARGE -> 24.dp
    }

    val shadowHeight = if (isPressed) 1.dp else 5.dp

    val bgColor = when (type) {
        ButtonType.PRIMARY -> BgColor
        ButtonType.DANGER -> ErrorColor
        else -> BgColor
    }

    val textColor = when (type) {
        ButtonType.PRIMARY -> TextColor
        ButtonType.DANGER -> Color.White
        else -> TextColor
    }

    val shadowColor = when (type) {
        ButtonType.PRIMARY -> ShadowBtn
        ButtonType.DANGER -> ErrorColorActive
        else -> ShadowBtn
    }

    Column(
        modifier = modifier
            .graphicsLayer { alpha = if (enabled) 1f else 0.5f }
            .height(height + 5.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            // Shadow Layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .background(shadowColor, RoundedCornerShape(borderRadius))
            )

            // Button Layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .offset(y = -shadowHeight)
                    .clip(RoundedCornerShape(borderRadius))
                    .background(bgColor)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled && !loading,
                        onClick = onClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val textStyle = TextStyle(
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.02.sp
                    )
                    
                    CompositionLocalProvider(
                        // Could provide custom text style here if needed
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.MIDDLE,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    AnimalButton(
        onClick = onClick,
        modifier = modifier,
        type = type,
        size = size,
        enabled = enabled,
        loading = loading
    ) {
        val fontSize = when (size) {
            ButtonSize.SMALL -> 12.sp
            ButtonSize.MIDDLE -> 14.sp
            ButtonSize.LARGE -> 16.sp
        }
        val textColor = when (type) {
            ButtonType.PRIMARY -> TextColor
            ButtonType.DANGER -> Color.White
            else -> TextColor
        }
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.02.sp
            )
        )
    }
}
