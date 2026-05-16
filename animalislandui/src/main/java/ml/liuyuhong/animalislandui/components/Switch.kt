package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

enum class SwitchSize {
    DEFAULT, SMALL
}

@Composable
fun AnimalSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: SwitchSize = SwitchSize.DEFAULT,
    enabled: Boolean = true,
    checkedChildren: String? = null,
    unCheckedChildren: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    val trackWidth = when (size) {
        SwitchSize.DEFAULT -> 52.dp
        SwitchSize.SMALL -> 38.dp
    }
    val trackHeight = when (size) {
        SwitchSize.DEFAULT -> 28.dp
        SwitchSize.SMALL -> 20.dp
    }
    val handleSize = when (size) {
        SwitchSize.DEFAULT -> 21.dp
        SwitchSize.SMALL -> 14.dp
    }
    val handlePadding = when (size) {
        SwitchSize.DEFAULT -> 2.dp
        SwitchSize.SMALL -> 1.dp
    }
    val borderWidth = when (size) {
        SwitchSize.DEFAULT -> 2.5.dp
        SwitchSize.SMALL -> 2.dp
    }

    val handlePosition by animateDpAsState(
        targetValue = if (checked) trackWidth - handleSize - handlePadding * 2 else 0.dp,
        label = "handlePosition"
    )

    val trackColor = if (checked) SuccessColor else ShadowInput
    val trackBorderColor = if (checked) SuccessColorActive else BorderColorLight
    val handleShadowColor = if (checked) ShadowSwitchOn else ShadowBtn

    val fontSize = if (size == SwitchSize.DEFAULT) 11.sp else 9.sp
    val textPadding = if (size == SwitchSize.DEFAULT) 8.dp else 6.dp

    Box(
        modifier = modifier
            .graphicsLayer { alpha = if (enabled) 1f else 0.5f }
            .size(trackWidth, trackHeight + 3.dp) // extra for handle float
    ) {
        // Track
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(trackWidth, trackHeight)
                .background(trackColor, RoundedCornerShape(50.dp))
                .border(borderWidth, trackBorderColor, RoundedCornerShape(50.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = { onCheckedChange(!checked) }
                )
        ) {
            // Inner Text
            Box(modifier = Modifier.fillMaxSize()) {
                if (checked && checkedChildren != null) {
                    Text(
                        text = checkedChildren,
                        color = Color.White,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = textPadding)
                    )
                } else if (!checked && unCheckedChildren != null) {
                    Text(
                        text = unCheckedChildren,
                        color = Color.White,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = textPadding)
                    )
                }
            }
        }

        // Handle
        Box(
            modifier = Modifier
                .offset(x = handlePadding + handlePosition, y = -2.dp) // handle floats
                .size(handleSize)
        ) {
            // Handle Shadow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 3.dp)
                    .background(handleShadowColor, CircleShape)
            )
            // Handle Surface
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BgColorInput, CircleShape)
                    .border(2.dp, if (checked) SuccessColor else BorderColorLight, CircleShape)
            )
        }
    }
}
