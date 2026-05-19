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
                        containerColor = BgColor,
//                        bottomBar = {
//                            AnimalFooter(
//                                type = FooterType.SEA,
//                                modifier = Modifier.navigationBarsPadding()
//                            )
//                        }
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
                .verticalScroll(rememberScrollState()) // Prevents clipping on 4:3 screens
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
                // Tablet Layout: 3 Columns with balanced weights
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Left: Quick Access
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
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

                    // Center: Main Tasks (Wider for Project Card)
                    Column(modifier = Modifier.weight(1.8f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        SectionLabel("Island Projects")
                        AnimalCard(type = CardType.TITLE, title = "Active Construction", modifier = Modifier.fillMaxWidth()) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                AnimalCollapse(header = "Bridge to North Shore") {
                                    Text("Status: 85% Funded. Requires 15,000 more Bells.", color = TextColorMuted)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    AnimalButton(text = "Donate Now", size = ButtonSize.SMALL, onClick = {})
                                }
                                AnimalCollapse(header = "Museum Art Gallery") {
                                    Text("Status: Planning Phase. Talk to Blathers for details.", color = TextColorMuted)
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    AnimalButton(
                                        text = "New Project", 
                                        modifier = Modifier.weight(1f),
                                        onClick = {}
                                    )
                                    AnimalButton(
                                        text = "Urgent Help", 
                                        type = ButtonType.DANGER, 
                                        modifier = Modifier.weight(1f),
                                        onClick = {}
                                    )
                                }
                            }
                        }
                        
                        SectionLabel("Recent Logs")
                        AnimalCodeBlock(
                            code = "INFO: New resident 'Raymond' arrived.\nSUCCESS: Blue Rose bloomed.\nWARN: Prices dropping!",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Right: Form Controls
                    Column(modifier = Modifier.weight(1.2f), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        SectionLabel("Island Settings")
                        AnimalCard(color = AppYellow, modifier = Modifier.fillMaxWidth()) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                AnimalInput(
                                    value = islandName, 
                                    onValueChange = { islandName = it }, 
                                    placeholder = "Island name..."
                                )
                                AnimalSelect(
                                    options = listOf(
                                        SelectOption("Early Bird", "morning"), 
                                        SelectOption("Night Owl", "night"),
                                        SelectOption("Beautiful Island", "clean")
                                    ),
                                    selectedOption = selectedOrdinance,
                                    onOptionSelected = { selectedOrdinance = it },
                                    placeholder = "Select Ordinance"
                                )
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text("Alerts", fontWeight = FontWeight.Bold, color = TextColorBody)
                                    AnimalSwitch(
                                        checked = alertsEnabled, 
                                        onCheckedChange = { alertsEnabled = it },
                                        checkedChildren = "ON",
                                        unCheckedChildren = "OFF"
                                    )
                                }
                                AnimalCheckbox(
                                    checked = allowVisitors, 
                                    onCheckedChange = { allowVisitors = it }, 
                                    label = "Allow visitors"
                                )
                                AnimalButton(
                                    text = "Save Config", 
                                    modifier = Modifier.fillMaxWidth(), 
                                    onClick = {}
                                )
                            }
                        }
                        
                        AnimalDivider(type = DividerType.TEAL)
                        
                        SectionLabel("Tools")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            AnimalIcon(AnimalIconName.CAMERA, tint = AppPurple, size = 28.dp)
                            AnimalIcon(AnimalIconName.DIY, tint = AppOrange, size = 28.dp)
                            AnimalIcon(AnimalIconName.MAP, tint = AppTeal, size = 28.dp)
                            AnimalIcon(AnimalIconName.MILES, tint = AppBlue, size = 28.dp)
                        }
                    }
                }
            } else {
                // Mobile Layout
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AnimalCard(title = "Welcome") {
                        Text("Rotate to tablet mode for the desk experience.", color = TextColorBody)
                    }
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
