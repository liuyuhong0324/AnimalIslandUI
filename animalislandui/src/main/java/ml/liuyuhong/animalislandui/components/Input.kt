package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

enum class InputSize {
    SMALL, MIDDLE, LARGE
}

@Composable
fun AnimalInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    size: InputSize = InputSize.MIDDLE,
    placeholder: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    val height = when (size) {
        InputSize.SMALL -> 32.dp
        InputSize.MIDDLE -> 40.dp
        InputSize.LARGE -> 48.dp
    }

    val padding = when (size) {
        InputSize.SMALL -> PaddingValues(horizontal = 14.dp)
        InputSize.MIDDLE -> PaddingValues(horizontal = 18.dp)
        InputSize.LARGE -> PaddingValues(horizontal = 22.dp)
    }

    val fontSize = when (size) {
        InputSize.SMALL -> 12.sp
        InputSize.MIDDLE -> 14.sp
        InputSize.LARGE -> 16.sp
    }

    val borderRadius = 50.dp
    val borderWidth = when (size) {
        InputSize.SMALL -> 2.5.dp
        InputSize.MIDDLE -> 2.5.dp
        InputSize.LARGE -> 3.dp
    }

    val shadowHeight = when (size) {
        InputSize.SMALL -> 2.dp
        InputSize.MIDDLE -> 3.dp
        InputSize.LARGE -> 4.dp
    }

    val currentBorderColor = when {
        !enabled -> BorderColorLight.copy(alpha = 0.6f)
        isError -> ErrorColor
        isFocused -> FocusYellow
        else -> BorderColorLight
    }

    val currentShadowColor = when {
        !enabled -> Color.Transparent
        isError -> ErrorColorActive
        isFocused -> FocusYellowDark
        else -> ShadowInput
    }

    val currentBgColor = if (enabled) BgColorInput else BgColorInputDis

    Column(
        modifier = modifier
            .graphicsLayer { alpha = if (enabled) 1f else 0.6f }
            .height(height + shadowHeight)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Shadow Layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .offset(y = shadowHeight)
                    .background(currentShadowColor, RoundedCornerShape(borderRadius))
            )

            // Input Layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .background(currentBgColor, RoundedCornerShape(borderRadius))
                    .border(borderWidth, currentBorderColor, RoundedCornerShape(borderRadius))
                    .padding(padding),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isFocused = it.isFocused },
                    enabled = enabled,
                    textStyle = TextStyle(
                        color = TextColorBody,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.01.sp
                    ),
                    keyboardOptions = keyboardOptions,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                style = TextStyle(
                                    color = TextColorDisabled,
                                    fontSize = fontSize,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}
