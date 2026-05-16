package ml.liuyuhong.animalislandui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryColorBg,
    onPrimaryContainer = PrimaryColorActive,
    secondary = SidebarActiveBg,
    onSecondary = Color.White,
    background = BgColor,
    onBackground = TextColor,
    surface = BgColorContent,
    onSurface = TextColorBody,
    error = ErrorColor,
    onError = Color.White,
    outline = BorderColorLight
)

// Custom tokens for Animal Island UI
data class AnimalIslandColors(
    val primaryHover: Color,
    val primaryActive: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val textDisabled: Color,
    val borderColor: Color,
    val borderColorHover: Color,
    val bgColorSecondary: Color,
    val bgColorDisabled: Color,
    val focusYellow: Color,
    val focusYellowDark: Color,
    val shadowBtn: Color,
    val shadowInput: Color,
    val success: Color,
    val warning: Color
)

val LocalAnimalIslandColors = staticCompositionLocalOf {
    AnimalIslandColors(
        primaryHover = PrimaryColorHover,
        primaryActive = PrimaryColorActive,
        textSecondary = TextColorSecondary,
        textMuted = TextColorMuted,
        textDisabled = TextColorDisabled,
        borderColor = BorderColor,
        borderColorHover = BorderColorHover,
        bgColorSecondary = BgColorSecondary,
        bgColorDisabled = BgColorDisabled,
        focusYellow = FocusYellow,
        focusYellowDark = FocusYellowDark,
        shadowBtn = ShadowBtn,
        shadowInput = ShadowInput,
        success = SuccessColor,
        warning = WarningColor
    )
}

object AnimalIslandTheme {
    val colors: AnimalIslandColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAnimalIslandColors.current
}

@Composable
fun AnimalIslandUITheme(
    content: @Composable () -> Unit
) {
    val animalIslandColors = AnimalIslandColors(
        primaryHover = PrimaryColorHover,
        primaryActive = PrimaryColorActive,
        textSecondary = TextColorSecondary,
        textMuted = TextColorMuted,
        textDisabled = TextColorDisabled,
        borderColor = BorderColor,
        borderColorHover = BorderColorHover,
        bgColorSecondary = BgColorSecondary,
        bgColorDisabled = BgColorDisabled,
        focusYellow = FocusYellow,
        focusYellowDark = FocusYellowDark,
        shadowBtn = ShadowBtn,
        shadowInput = ShadowInput,
        success = SuccessColor,
        warning = WarningColor
    )

    CompositionLocalProvider(
        LocalAnimalIslandColors provides animalIslandColors
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = AnimalIslandTypography,
            shapes = AnimalIslandShapes,
            content = content
        )
    }
}
