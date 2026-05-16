package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

enum class CardType {
    DEFAULT, TITLE
}

@Composable
fun AnimalCard(
    modifier: Modifier = Modifier,
    type: CardType = CardType.DEFAULT,
    color: Color = BgColorContent,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val borderRadius = if (type == CardType.TITLE) {
        RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 35.dp,
            bottomEnd = 45.dp,
            bottomStart = 38.dp
        )
    } else {
        RoundedCornerShape(20.dp)
    }

    val padding = if (type == CardType.TITLE) {
        PaddingValues(horizontal = 32.dp, vertical = 12.dp)
    } else {
        PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    }

    val bgColor = if (type == CardType.TITLE) Color(0xFFFDFDF5) else color
    val textColor = if (color == AppYellow || color == LimeGreen || color == YellowGreen || color == BgColorContent || bgColor == Color.White) {
        TextColorBody
    } else {
        Color.White
    }

    Column(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = borderRadius,
                ambientColor = Color(0xFF3D3428).copy(alpha = 0.1f),
                spotColor = Color(0xFF6B5C43).copy(alpha = 0.42f)
            )
            .background(bgColor, borderRadius)
            .padding(padding)
    ) {
        if (title != null) {
            Text(
                text = title,
                color = TextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        // Use a default text color for content
        CompositionLocalProvider(
            // We could use LocalContentColor here if we wanted to be more idiomatic
        ) {
            content()
        }
    }
}
