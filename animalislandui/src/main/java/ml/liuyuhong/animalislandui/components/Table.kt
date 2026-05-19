package ml.liuyuhong.animalislandui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

data class TableColumn<T>(
    val title: String,
    val dataIndex: String? = null,
    val width: Dp? = null,
    val align: TextAlign = TextAlign.Start,
    val render: @Composable (value: Any?, record: T, index: Int) -> Unit = { value, _, _ ->
        Text(
            text = value?.toString() ?: "",
            color = TextColorBody,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
)

@Composable
fun <T> AnimalTable(
    columns: List<TableColumn<T>>,
    dataSource: List<T>,
    modifier: Modifier = Modifier,
    striped: Boolean = true,
    showHeader: Boolean = true,
    loading: Boolean = false,
    emptyText: String = "暂无数据"
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(BgColorContent)
            .padding(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            if (showHeader) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawDashedBottomLine()
                ) {
                    columns.forEach { column ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .then(if (column.width != null) Modifier.width(column.width) else Modifier)
                                .padding(16.dp, 20.dp),
                            contentAlignment = when (column.align) {
                                TextAlign.Center -> Alignment.Center
                                TextAlign.End -> Alignment.CenterEnd
                                else -> Alignment.CenterStart
                            }
                        ) {
                            Text(
                                text = column.title,
                                color = TextColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.02.sp
                            )
                        }
                    }
                }
            }

            // Body
            if (dataSource.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AnimalIcon(
                            name = AnimalIconName.LEAF,
                            tint = TextColorSecondary.copy(alpha = 0.5f),
                            size = 48.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = emptyText, color = TextColorSecondary, fontSize = 14.sp)
                    }
                }
            } else {
                dataSource.forEachIndexed { index, record ->
                    val isStriped = striped && index % 2 == 1
                    val rowBg = if (isStriped) Color(0xFFF8F8F0).copy(alpha = 0.6f) else Color.Transparent

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(rowBg)
                            .then(if (index < dataSource.size - 1) Modifier.drawDashedBottomLine() else Modifier)
                    ) {
                        columns.forEach { column ->
                            val value = if (column.dataIndex != null) {
                                // Simple reflection-like access for generic Map or basic objects
                                // In a real app, you'd use a better way to extract data
                                if (record is Map<*, *>) record[column.dataIndex] else null
                            } else null

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .then(if (column.width != null) Modifier.width(column.width) else Modifier)
                                    .padding(14.dp, 20.dp),
                                contentAlignment = when (column.align) {
                                    TextAlign.Center -> Alignment.Center
                                    TextAlign.End -> Alignment.CenterEnd
                                    else -> Alignment.CenterStart
                                }
                            ) {
                                column.render(value, record, index)
                            }
                        }
                    }
                }
            }
        }

        // Loading Overlay
        if (loading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(BgColorContent.copy(alpha = 0.8f))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        }
    }
}

private fun Modifier.drawDashedBottomLine(): Modifier = this.drawBehind {
    val strokeWidth = 1.dp.toPx()
    val dashWidth = 6.dp.toPx()
    val gapWidth = 6.dp.toPx()
    val color = Color(0xFFF0E8D8)

    drawLine(
        color = color,
        start = Offset(20.dp.toPx(), size.height - strokeWidth / 2),
        end = Offset(size.width - 20.dp.toPx(), size.height - strokeWidth / 2),
        strokeWidth = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, gapWidth), 0f)
    )
}
