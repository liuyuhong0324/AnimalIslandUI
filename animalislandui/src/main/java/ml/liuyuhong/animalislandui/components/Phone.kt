package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.R
import ml.liuyuhong.animalislandui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NookPhone(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(300.dp, 450.dp)
            .background(Color(0xFFF8F4E8), RoundedCornerShape(80.dp))
            .padding(20.dp)
            .clip(RoundedCornerShape(60.dp))
            .background(Color(0xFFF8F4E8))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            PhoneTopBar()
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // App Grid
            AppGrid()
        }
    }
}

@Composable
fun PhoneTopBar() {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            kotlinx.coroutines.delay(1000)
        }
    }

    val timeStr = SimpleDateFormat("h:mm", Locale.US).format(currentTime.time)
    val dayStr = SimpleDateFormat("EEEE", Locale.US).format(currentTime.time)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = timeStr,
            color = Color(0xFFDDDBCC),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
        Text(
            text = dayStr,
            color = Color(0xFF725C4E),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun AppGrid() {
    val apps = listOf(
        AppItemData(AppPurple, "Camera", R.drawable.icon_camera),
        AppItemData(AppBlue, "App", R.drawable.icon_miles),
        AppItemData(AppYellow, "Critter", R.drawable.icon_critterpedia),
        AppItemData(AppOrange, "DIY", R.drawable.icon_diy),
        AppItemData(AppPink, "Shop", R.drawable.icon_shopping),
        AppItemData(AppTeal, "Map", R.drawable.icon_map),
        AppItemData(AppGreen, "Design", R.drawable.icon_design),
        AppItemData(AppRed, "Heli", R.drawable.icon_helicopter),
        AppItemData(LimeGreen, "Chat", R.drawable.icon_chat)
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (i in 0 until 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                for (j in 0 until 3) {
                    val app = apps[i * 3 + j]
                    AppTile(app.color, app.iconRes)
                }
            }
        }
    }
}

data class AppItemData(val color: Color, val label: String, val iconRes: Int)

@Composable
fun AppTile(color: Color, iconRes: Int) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color, RoundedCornerShape(22.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}
