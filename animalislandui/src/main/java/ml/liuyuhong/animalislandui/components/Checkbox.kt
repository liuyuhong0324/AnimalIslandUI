package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

enum class CheckboxSize {
    SMALL, MIDDLE, LARGE
}

@Composable
fun AnimalCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    size: CheckboxSize = CheckboxSize.MIDDLE,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val boxSize = when (size) {
        CheckboxSize.SMALL -> 18.dp
        CheckboxSize.MIDDLE -> 22.dp
        CheckboxSize.LARGE -> 28.dp
    }

    val borderWidth = when (size) {
        CheckboxSize.SMALL -> 2.dp
        CheckboxSize.MIDDLE -> 2.5.dp
        CheckboxSize.LARGE -> 3.dp
    }

    val fontSize = when (size) {
        CheckboxSize.SMALL -> 12.sp
        CheckboxSize.MIDDLE -> 14.sp
        CheckboxSize.LARGE -> 16.sp
    }

    val iconSize = when (size) {
        CheckboxSize.SMALL -> 11.sp
        CheckboxSize.MIDDLE -> 13.sp
        CheckboxSize.LARGE -> 16.sp
    }

    val scale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "scale"
    )

    Row(
        modifier = modifier
            .graphicsLayer { alpha = if (enabled) 1f else 0.55f }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = { onCheckedChange(!checked) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .background(
                    if (checked) PrimaryColor else BgColorInput,
                    RoundedCornerShape(8.dp)
                )
                .border(
                    borderWidth,
                    if (checked) PrimaryColorActive else BorderColorLight,
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize.value.dp)
                    .scale(scale)
            )
        }

        if (label != null) {
            Text(
                text = label,
                style = TextStyle(
                    color = TextColorBody,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.01.sp
                )
            )
        }
    }
}
