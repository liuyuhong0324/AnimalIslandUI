package ml.liuyuhong.animalislanduiexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.liuyuhong.animalislandui.components.*
import ml.liuyuhong.animalislandui.theme.*
import kotlinx.coroutines.delay

class ShowcaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalIslandUITheme {
                var isLoading by remember { mutableStateOf(true) }
                
                LaunchedEffect(Unit) {
                    delay(2500)
                    isLoading = false
                }

                AnimalLoading(active = isLoading) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = BgColor
                    ) { innerPadding ->
                        IslandDeskContent(
                            modifier = Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IslandDeskContent(modifier: Modifier = Modifier) {
    // Interactive States
    var islandName by remember { mutableStateOf("My Paradise") }
    var selectedOrdinance by remember { mutableStateOf<SelectOption?>(null) }
    var allowVisitors by remember { mutableStateOf(true) }
    var alertsEnabled by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isTablet = maxWidth > 800.dp
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header Area
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    AnimalTypewriter(text = "Resident Representative's Desk", speed = 50L) {
                        Text(it, style = AnimalIslandTypography.titleLarge, color = TextColor)
                    }
                    Text("Managing Animal Island with style.", color = TextColorSecondary, fontSize = 14.sp)
                }
                AnimalTime()
            }

            AnimalDivider(type = DividerType.WAVE_YELLOW)

            if (isTablet) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Column 1
                    Column(modifier = Modifier.weight(1.4f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        SectionLabel("Quick Access")
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            NookPhone()
                        }
                        AnimalCard(color = AppTeal) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                AnimalIcon(AnimalIconName.LEAF, tint = SuccessColor, size = 32.dp)
                                Text("Island Rating: ★★★★★", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Column 2
                    Column(modifier = Modifier.weight(1.5f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        SectionLabel("Construction & Logs")
                        AnimalCard(type = CardType.TITLE, title = "Projects", modifier = Modifier.fillMaxWidth()) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                AnimalCollapse(header = "Bridge Project") {
                                    Text("Status: 85% Funded.", color = TextColorMuted)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    AnimalButton(text = "New", modifier = Modifier.weight(1f), onClick = {})
                                    AnimalButton(text = "Urgent", type = ButtonType.DANGER, modifier = Modifier.weight(1f), onClick = {})
                                }
                            }
                        }
                        
                        AnimalCodeBlock(
                            code = "Resident 'Raymond' arrived.\nSuccess: Blue Rose bloomed.",
                            modifier = Modifier.fillMaxWidth()
                        )

                        SectionLabel("Island Registry")
                        val columns = listOf(
                            TableColumn<Map<String, String>>(title = "Name", dataIndex = "name"),
                            TableColumn<Map<String, String>>(title = "Status", render = { _, record, _ ->
                                Text(record["status"] ?: "", color = SuccessColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            })
                        )
                        val data = listOf(
                            mapOf("name" to "Raymond", "status" to "Resident"),
                            mapOf("name" to "Marshal", "status" to "Resident"),
                            mapOf("name" to "Raymond", "status" to "Resident"),
                            mapOf("name" to "Marshal", "status" to "Resident")
                        )
                        AnimalTable(columns = columns, dataSource = data, modifier = Modifier.fillMaxWidth())
                    }

                    // Column 3
                    Column(modifier = Modifier.weight(1.1f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        SectionLabel("Settings")
                        AnimalCard(color = AppYellow, modifier = Modifier.fillMaxWidth()) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                AnimalInput(value = islandName, onValueChange = { islandName = it })
                                AnimalSelect(
                                    options = listOf(SelectOption("Early Bird", "1"), SelectOption("Night Owl", "2")),
                                    selectedOption = selectedOrdinance,
                                    onOptionSelected = { selectedOrdinance = it }
                                )
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text("Alerts", fontWeight = FontWeight.Bold, color = TextColorBody)
                                    AnimalSwitch(checked = alertsEnabled, onCheckedChange = { alertsEnabled = it })
                                }
                                AnimalCheckbox(checked = allowVisitors, onCheckedChange = { allowVisitors = it }, label = "Visitors")
                                AnimalButton(text = "Save", modifier = Modifier.fillMaxWidth(), onClick = {})
                            }
                        }
                        
                        AnimalDivider(type = DividerType.TEAL)
                        
                        SectionLabel("Tools")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            AnimalIcon(AnimalIconName.CAMERA, tint = AppPurple, size = 28.dp)
                            AnimalIcon(AnimalIconName.DIY, tint = AppOrange, size = 28.dp)
                            AnimalIcon(AnimalIconName.MAP, tint = AppTeal, size = 28.dp)
                        }
                    }
                }
            } else {
                // Mobile
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    AnimalCard(title = "Welcome") { Text("Rotate to tablet mode.", color = TextColorBody) }
                    NookPhone()
                }
            }
        }
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = AnimalIslandTypography.labelLarge,
        color = TextColorSecondary,
        letterSpacing = 2.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
}
