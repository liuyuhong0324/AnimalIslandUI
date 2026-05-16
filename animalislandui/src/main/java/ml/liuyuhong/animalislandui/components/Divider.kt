package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ml.liuyuhong.animalislandui.R
import ml.liuyuhong.animalislandui.theme.*

enum class DividerType {
    BROWN, TEAL, WHITE, YELLOW, WAVE_YELLOW
}

@Composable
fun AnimalDivider(
    modifier: Modifier = Modifier,
    type: DividerType = DividerType.BROWN
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {
        if (type == DividerType.WHITE) {
            Image(
                painter = painterResource(id = R.drawable.divider_line_white),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillHeight
            )
        } else {
            val resId = when (type) {
                DividerType.BROWN -> R.drawable.divider_line_brown
                DividerType.TEAL -> R.drawable.divider_line_teal
                DividerType.YELLOW -> R.drawable.divider_line_yellow
                DividerType.WAVE_YELLOW -> R.drawable.wave_yellow
                else -> null
            }
            if (resId != null) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}
