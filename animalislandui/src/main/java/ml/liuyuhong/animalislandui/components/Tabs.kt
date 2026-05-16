package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.R
import ml.liuyuhong.animalislandui.theme.*

data class TabItem(
    val label: String,
    val content: @Composable () -> Unit
)

@Composable
fun AnimalTabs(
    tabs: List<TabItem>,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0
) {
    var selectedIndex by remember { mutableStateOf(initialIndex) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, BorderColor, RoundedCornerShape(20.dp))
            .background(BgColorContent)
    ) {
        // Tab List
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.6f))
                .drawBottomBorder(2.dp, BorderColorLight)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = selectedIndex == index
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isSelected) PrimaryColor.copy(alpha = 0.15f) else Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { selectedIndex = index }
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.label,
                        color = if (isSelected) PrimaryColor else TextColorMuted,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                    
                    if (isSelected) {
                        // Leaf decoration with wiggle
                        WigglingLeaf(modifier = Modifier.align(Alignment.TopEnd).offset(x = 6.dp, y = (-3).dp))
                    }
                }
            }
        }

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            AnimatedContent(
                targetState = selectedIndex,
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) + slideInVertically { it / 4 } togetherWith
                            fadeOut(animationSpec = tween(250))
                },
                label = "tabContent"
            ) { index ->
                tabs[index].content()
            }
        }
    }
}

@Composable
fun WigglingLeaf(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "leafWiggle")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Image(
        painter = painterResource(id = R.drawable.icon_leaf),
        contentDescription = null,
        modifier = modifier
            .size(14.dp)
            .graphicsLayer { rotationZ = rotation }
    )
}

fun Modifier.drawBottomBorder(width: Dp, color: Color): Modifier = this.drawBehind {
    val strokeWidth = width.toPx()
    val y = size.height - strokeWidth / 2
    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth = strokeWidth
    )
}
