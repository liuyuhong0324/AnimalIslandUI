package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ml.liuyuhong.animalislandui.R

enum class AnimalIconName {
    CAMERA, CHAT, CRITTERPEDIA, DESIGN, DIY, HELICOPTER, LEAF, MAP, MILES, SHOPPING, VARIANT
}

@Composable
fun AnimalIcon(
    name: AnimalIconName,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: Dp = 24.dp
) {
    val resId = when (name) {
        AnimalIconName.CAMERA -> R.drawable.icon_camera
        AnimalIconName.CHAT -> R.drawable.icon_chat
        AnimalIconName.CRITTERPEDIA -> R.drawable.icon_critterpedia
        AnimalIconName.DESIGN -> R.drawable.icon_design
        AnimalIconName.DIY -> R.drawable.icon_diy
        AnimalIconName.HELICOPTER -> R.drawable.icon_helicopter
        AnimalIconName.LEAF -> R.drawable.icon_leaf
        AnimalIconName.MAP -> R.drawable.icon_map
        AnimalIconName.MILES -> R.drawable.icon_miles
        AnimalIconName.SHOPPING -> R.drawable.icon_shopping
        AnimalIconName.VARIANT -> R.drawable.icon_variant
    }

    Icon(
        painter = painterResource(id = resId),
        contentDescription = name.name,
        modifier = modifier.size(size),
        tint = tint
    )
}
