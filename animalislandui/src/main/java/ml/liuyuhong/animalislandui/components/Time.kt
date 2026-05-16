package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AnimalTime(
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            kotlinx.coroutines.delay(1000)
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "blink")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                1f at 0
                1f at 499
                0f at 500
                0f at 1000
            }
        ),
        label = "alpha"
    )

    val weekday = SimpleDateFormat("EEE", Locale.US).format(currentTime.time).uppercase()
    val monthDay = SimpleDateFormat("M/d", Locale.US).format(currentTime.time)
    val hour = SimpleDateFormat("h", Locale.US).format(currentTime.time)
    val minute = SimpleDateFormat("mm", Locale.US).format(currentTime.time)
    val amPm = SimpleDateFormat("a", Locale.US).format(currentTime.time)

    Row(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, BgColor)
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .border(3.dp, Color(0xFFD4CFC3), RoundedCornerShape(18.dp))
            .padding(horizontal = 36.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Date Block
        Column(
            modifier = Modifier
                .padding(end = 24.dp)
                .drawRightBorder(3.dp, Color(0xFF9F927D).copy(alpha = 0.35f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weekday,
                color = SuccessColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp
            )
            Text(
                text = monthDay,
                color = Color(0xFF8B7355),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // Time Block
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = hour,
                color = Color(0xFF8B7355),
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                text = ":",
                color = Color(0xFF8B7355).copy(alpha = alpha),
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.offset(y = (-4).dp)
            )
            Text(
                text = minute,
                color = Color(0xFF8B7355),
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                text = amPm,
                color = Color(0xFF8B7355),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

fun Modifier.drawRightBorder(width: Dp, color: Color): Modifier = this.drawBehind {
    val strokeWidth = width.toPx()
    val x = size.width + strokeWidth / 2
    drawLine(
        color = color,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = strokeWidth
    )
}
