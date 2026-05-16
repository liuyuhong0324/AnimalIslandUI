package ml.liuyuhong.animalislandui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.theme.*

data class SelectOption(
    val label: String,
    val value: String
)

@Composable
fun AnimalSelect(
    options: List<SelectOption>,
    selectedOption: SelectOption?,
    onOptionSelected: (SelectOption) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select an option..."
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(BgColorInput, RoundedCornerShape(50.dp))
                .border(2.5.dp, BorderColorLight, RoundedCornerShape(50.dp))
                .clip(RoundedCornerShape(50.dp))
                .clickable { expanded = true }
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedOption?.label ?: placeholder,
                color = if (selectedOption != null) TextColorBody else TextColorDisabled,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = TextColorSecondary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(12.dp),
            containerColor = BgColorContent,
            modifier = Modifier.border(1.dp, BorderColorLight, RoundedCornerShape(12.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.label,
                            color = TextColorBody,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
