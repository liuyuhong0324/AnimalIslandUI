package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimalCodeBlock(
    code: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF2B2118), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF3D3028), RoundedCornerShape(20.dp))
            .padding(20.dp, 24.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        Text(
            text = code,
            style = TextStyle(
                color = Color(0xFFE8D5BC),
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
