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
import androidx.core.graphics.PathParser
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
    
    val bobOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bob"
    )

    val swayRotation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sway"
    )

    // SVG Path Strings extracted from React component
    val trunkPathStr = "M213.22 300.79c5.11-16.37 25.14-63.35 19.41-78.67-2.73-6.27-5.74 4.48-7.08 6.09-1.33 1.61-9.85-17.34-13.5-17.37-3.66-.02-2.22 4.79-6.04 3.71-3.82-1.07-11.66-5.68-6.03-12.13 3.44-4.26-.36-7.98-7.01-8.9-6.66-.9 3.72-18.2 10.99-20.22 7.26-2 48.02-11.76 69.87 9.27 13.25 12.6 10.28 12.74 6.7 13.47-3.58.72-8.31-1.49-9.12.87-.81 2.36-.34 7.25-4.09 10.01s-13.19 6.65-12.11 8.26c1.08 1.6 15.9 56.5 2.36 89.27-18.8-.05-47.45 1.59-44.35-3.66z"
    val dirtPathStr = "M 344.8 381.58 c -3.22 -6.65 -224.38 -19.67 -224.1 12.27 v 9.44 c 0 23 7.75 44.2 20.79 61.13 c 2.87 4.2 177.12 3.56 182.8 0.64 a 99.87 99.87 0 0 0 21.3 -61.77 c 0 -18.04 0.38 -19.29 -0.78 -21.7 z"
    val grassPathStr = "M207.31 296.89c1.53-5.52 4.13-14.63 5.08-15.86 1.37-1.8 11.24 11.8 13 12.45 1.77.66 10.36-9.54 12.57-10.46 2.2-.92 13.4 10.9 15 11.69 1.6.79 15.09-13.1 16.87-12.77 1.2.22 1.9 10.6 2.47 17.92 60.25 15.2 73 64.26 73.43 88.25-3.87-.2-8.18 8.96-12.77 10.35-4.6 1.39-16.02-4.5-20.17-3.32-4.16 1.19-14.83 10.42-19.82 10.7-4.99.28-11.34-6.23-16-5.48-4.67.74-12.37 8.56-18.45 8.5-6.08-.08-9.08-6.6-15.72-6.59-6.65.03-16.76 8.65-20.3 9-3.54.37-12.63-10.06-17.24-9.74-4.62.32-13.67 8.36-18.57 8.2-4.91-.16-12.44-9.97-16.59-9.58-4.14.4-15.47 7.5-19.98 5.96-4.5-1.55-7.6-10.46-14.42-11.45-6.78-1-8.81 1.85-15.6 1.53.9-30.06 12.28-88.8 87.21-99.3z"
    val leafPathsStr = listOf(
        "M240.331,175.61C249.784,176.339 250.158,149.143 251.971,143.795C253.784,138.446 265.447,112.749 300.347,88.992C309.312,82.889 273.249,84.744 262.701,95.4C262.408,95.697 299.949,59.14 306.273,31.167C307.299,26.628 249.717,36.275 232.63,81.142C222.01,109.028 230.879,174.881 240.331,175.61Z",
        "M235.235,178.598C230.936,186.776 266.297,154.023 318.903,172.056C323.277,173.556 316.664,157.916 303.38,150.707C300.853,149.335 357.48,165.86 365.744,172.947C369.234,175.941 366.977,149.035 343.343,139.012C342.972,138.854 395.083,146.153 405.989,149.98C402.419,131.963 371.178,102.148 331.289,103.467C302.148,104.431 250.238,129.248 235.235,178.598Z",
        "M225.34,183.401C242.089,183.539 293.669,185.304 293.267,235.202C301.036,226.747 302.632,218.437 301.053,208.836C307.555,214.258 316.799,229.731 326.228,250.463C335.015,247.135 333.311,224.064 332.455,221.081C331.599,218.098 353.49,248.751 367.144,253.999C374.306,246.977 367.541,201.115 334.573,182.932C301.604,164.749 252.16,164.232 222.521,178.152C219.051,180.584 208.59,183.263 225.34,183.401Z",
        "M258.286,187.293C240.185,184.835 213.891,161.373 191.899,150.057C181.762,144.841 155.131,147.377 152.51,147.063C149.889,146.749 160.892,131.003 174.394,130.585C148.357,121.905 126.027,133.793 123.824,131.978C121.622,130.162 125.177,116.202 149.97,106.951C148.994,105.186 114.646,110.911 94.891,95.692C89.271,91.895 124.581,66.427 144.554,68.645C164.527,70.862 192.389,70.409 230.889,143.333C236.093,156.138 252.386,169.599 252.386,169.599C259.636,174.413 265.589,189.696 258.286,187.293Z",
        "M258.286,187.293C240.185,184.835 210.105,169.378 185.032,211.545C181.648,215.311 177.193,210.024 179.458,194.986C169.272,211.871 150.363,232.47 145.605,231.801C140.847,231.132 144.651,202.538 148.831,194.882C141.881,190.227 118.57,237.947 114.874,238.097C104.523,239.756 100.911,141.41 191.899,150.057C234.088,154.067 252.386,169.599 252.386,169.599C259.636,174.413 265.589,189.696 258.286,187.293Z"
    )

    // Pre-parse and remember paths to avoid overhead
    val dirtPath = remember { PathParser.createPathFromPathData(dirtPathStr).asComposePath() }
    val grassPath = remember { PathParser.createPathFromPathData(grassPathStr).asComposePath() }
    val trunkPath = remember { PathParser.createPathFromPathData(trunkPathStr).asComposePath() }
    val leafPaths = remember { leafPathsStr.map { PathParser.createPathFromPathData(it).asComposePath() } }

    Canvas(modifier = modifier) {
        val originalWidth = 446f
        val originalHeight = 540f
        val scaleX = size.width / originalWidth
        val scaleY = size.height / originalHeight
        
        withTransform({
            translate(top = bobOffset)
            scale(scaleX, scaleY, pivot = Offset.Zero)
        }) {
            // Draw Water Circles
            drawOval(
                color = Color(0xFF12B2DA),
                topLeft = Offset(232.22f - 98.92f, 476.77f - 27.85f),
                size = Size(98.92f * 2, 27.85f * 2)
            )
            drawOval(
                color = Color(0xFF12B2DA),
                topLeft = Offset(258.15f - 73.8f, 505.32f - 18.22f),
                size = Size(73.8f * 2, 18.22f * 2)
            )

            // Draw Island components
            drawPath(dirtPath, color = Color(0xFFFED09D))
            drawPath(grassPath, color = Color(0xFF8CC751))
            drawPath(trunkPath, color = Color(0xFFE5B13B))

            // Draw Leaves with swaying animation
            withTransform({
                rotate(swayRotation, pivot = Offset(236.41f, 137.75f))
            }) {
                leafPaths.forEachIndexed { index, path ->
                    drawPath(
                        path = path,
                        color = if (index % 2 == 0) Color(0xFFABD25E) else Color(0xFF8CC751)
                    )
                }
            }
            
            // Draw a simplified fish moving across the water
            val fishProgress = (System.currentTimeMillis() % 4000) / 4000f
            val fishX = 50f + (fishProgress * 300f)
            drawOval(
                color = Color(0xFF0793B8),
                topLeft = Offset(fishX, 480f),
                size = Size(30f, 15f)
            )
        }
    }
}
