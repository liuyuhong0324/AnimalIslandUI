package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlin.math.hypot

@Composable
fun AnimalLoading(
    active: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(active) }
    val transitionFraction = remember { Animatable(0f) }

    LaunchedEffect(active) {
        if (active) {
            isVisible = true
            transitionFraction.snapTo(0f)
        } else {
            transitionFraction.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
            )
            isVisible = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        content()

        if (isVisible || transitionFraction.value < 1f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        if (transitionFraction.value > 0f) {
                            val maxRadius = hypot(size.width, size.height)
                            val radius = maxRadius * transitionFraction.value
                            drawCircle(
                                color = Color.Transparent,
                                radius = radius,
                                center = Offset(size.width / 2, size.height / 2),
                                blendMode = BlendMode.Clear
                            )
                        }
                    }
                    .background(Color.Black)
            ) {
                IslandAnimation(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 40.dp, end = 40.dp)
                        .size(200.dp)
                )
            }
        }
    }
}

@Composable
fun IslandAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "island")
    
    // Bobbing motion
    val bobOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bob"
    )

    // Swaying motion
    val swayRotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sway"
    )

    Canvas(modifier = modifier) {
        withTransform({
            translate(top = bobOffset)
        }) {
            // Draw Water Circles
            drawOval(
                color = Color(0xFF12B2DA),
                topLeft = Offset(size.width * 0.2f, size.height * 0.7f),
                size = Size(size.width * 0.6f, size.height * 0.2f)
            )

            // Draw Island Dirt
            val islandPath = Path().apply {
                moveTo(size.width * 0.2f, size.height * 0.65f)
                quadraticTo(
                    size.width * 0.5f, size.height * 0.85f,
                    size.width * 0.8f, size.height * 0.65f
                )
                close()
            }
            drawPath(islandPath, color = Color(0xFFFED09D))

            // Draw Grass
            drawOval(
                color = Color(0xFF8CC751),
                topLeft = Offset(size.width * 0.25f, size.height * 0.55f),
                size = Size(size.width * 0.5f, size.height * 0.15f)
            )

            // Draw Tree Trunk
            val trunkPath = Path().apply {
                moveTo(size.width * 0.45f, size.height * 0.58f)
                lineTo(size.width * 0.48f, size.height * 0.35f)
                lineTo(size.width * 0.52f, size.height * 0.35f)
                lineTo(size.width * 0.55f, size.height * 0.58f)
                close()
            }
            drawPath(trunkPath, color = Color(0xFFE5B13B))

            // Draw Leaves (Swaying)
            withTransform({
                rotate(swayRotation, pivot = Offset(size.width * 0.5f, size.height * 0.4f))
            }) {
                drawCircle(
                    color = Color(0xFFABD25E),
                    radius = size.width * 0.15f,
                    center = Offset(size.width * 0.5f, size.height * 0.3f)
                )
                drawCircle(
                    color = Color(0xFF8CC751),
                    radius = size.width * 0.1f,
                    center = Offset(size.width * 0.45f, size.height * 0.28f)
                )
            }
            
            // Simplified Fish motion simulation
            val fishProgress = (System.currentTimeMillis() % 4000) / 4000f
            val fishX = fishProgress * size.width
            drawOval(
                color = Color(0xFF0793B8),
                topLeft = Offset(fishX, size.height * 0.75f),
                size = Size(40f, 20f)
            )
        }
    }
}
