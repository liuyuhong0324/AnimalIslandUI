package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ml.liuyuhong.animalislandui.R

enum class FooterType {
    TREE, SEA
}

@Composable
fun AnimalFooter(
    modifier: Modifier = Modifier,
    type: FooterType = FooterType.TREE
) {
    val height = if (type == FooterType.SEA) 80.dp else 60.dp
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val resId = if (type == FooterType.TREE) R.drawable.footer_tree else R.drawable.footer_sea
        
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(height),
            contentScale = if (type == FooterType.TREE) ContentScale.FillWidth else ContentScale.FillHeight
        )
    }
}
