package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
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

    val borderRadius = when (size) {
        ButtonSize.SMALL -> 12.dp
        ButtonSize.MIDDLE -> 50.dp
        ButtonSize.LARGE -> 24.dp
    }

    val shadowHeight = if (isPressed || loading) 1.dp else 5.dp

    val bgColor = when {
        loading -> Color(0xFF0EC4B6)
        type == ButtonType.PRIMARY -> BgColor
        type == ButtonType.DANGER -> ErrorColor
        else -> BgColor
    }

    val shadowColor = when {
        loading -> Color(0xFF01B0A7)
        type == ButtonType.PRIMARY -> ShadowBtn
        type == ButtonType.DANGER -> ErrorColorActive
        else -> ShadowBtn
    }

    // Loading Animation Value
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 28.28f, // background-size
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

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
                    .then(
                        if (loading) {
                            Modifier
                                .border(4.dp, Color(0xFF4DE2DA), RoundedCornerShape(borderRadius))
                                .drawBehind {
                                    // Draw striped background for loading
                                    val stripeWidth = 28.28f
                                    val brush = Brush.linearGradient(
                                        0.0f to Color(0xFF0EC4B6),
                                        0.5f to Color(0xFF0EC4B6),
                                        0.5f to Color(0xFF01B0A7),
                                        1.0f to Color(0xFF01B0A7),
                                        start = Offset(animatedOffset, animatedOffset),
                                        end = Offset(animatedOffset + stripeWidth, animatedOffset + stripeWidth),
                                        tileMode = TileMode.Repeated
                                    )
                                    drawRect(brush = brush)
                                }
                        } else {
                            Modifier.background(bgColor)
                        }
                    )
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
                    content()
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
        val textColor = when {
            loading -> Color.White
            type == ButtonType.PRIMARY -> TextColor
            type == ButtonType.DANGER -> Color.White
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
