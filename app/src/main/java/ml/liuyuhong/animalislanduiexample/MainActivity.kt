package ml.liuyuhong.animalislanduiexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalIslandUITheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { AnimalFooter(type = FooterType.SEA) }
                ) { innerPadding ->
                    Showcase(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Showcase(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    var showModal by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var switchChecked by remember { mutableStateOf(true) }
    var checkboxChecked by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<SelectOption?>(null) }
    var typewriterTrigger by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize().background(BgColor)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            AnimalTime()

            AnimalTypewriter(
                text = "Welcome to Animal Island UI Showcase!",
                speed = 60L,
                trigger = typewriterTrigger
            ) { displayedText ->
                Text(
                    text = displayedText,
                    color = TextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            AnimalDivider(type = DividerType.WAVE_YELLOW)

            // Buttons Section
            SectionTitle("Interactive Buttons")
            AnimalCard(type = CardType.DEFAULT, color = AppPurple) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnimalButton(
                            text = "Primary",
                            modifier = Modifier.weight(1f),
                            onClick = { typewriterTrigger++ }
                        )
                        AnimalButton(
                            text = "Danger",
                            type = ButtonType.DANGER,
                            modifier = Modifier.weight(1f),
                            onClick = { showModal = true }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimalButton(
                            text = "Small",
                            size = ButtonSize.SMALL,
                            onClick = {}
                        )
                        AnimalButton(
                            text = "Default",
                            size = ButtonSize.MIDDLE,
                            modifier = Modifier.weight(1f),
                            onClick = {}
                        )
                        AnimalButton(
                            text = "Large",
                            size = ButtonSize.LARGE,
                            onClick = {}
                        )
                    }
                }
            }

            AnimalDivider(type = DividerType.TEAL)

            // Icons Section
            SectionTitle("Custom Icons")
            AnimalCard(color = Color.White) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AnimalIcon(name = AnimalIconName.LEAF, tint = SuccessColor)
                    AnimalIcon(name = AnimalIconName.CAMERA, tint = AppPurple)
                    AnimalIcon(name = AnimalIconName.DIY, tint = AppOrange)
                    AnimalIcon(name = AnimalIconName.MAP, tint = AppTeal)
                    AnimalIcon(name = AnimalIconName.CHAT, tint = LimeGreen)
                }
            }

            // Form Section
            SectionTitle("Form Controls")
            AnimalCard(type = CardType.TITLE, color = AppYellow, title = "Input & Select") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    AnimalInput(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = "Enter your island name...",
                        size = InputSize.LARGE
                    )

                    AnimalSelect(
                        options = listOf(
                            SelectOption("Northern Hemisphere", "north"),
                            SelectOption("Southern Hemisphere", "south")
                        ),
                        selectedOption = selectedOption,
                        onOptionSelected = { selectedOption = it }
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Notifications", color = TextColorBody, fontWeight = FontWeight.SemiBold)
                        AnimalSwitch(
                            checked = switchChecked,
                            onCheckedChange = { switchChecked = it },
                            checkedChildren = "ON",
                            unCheckedChildren = "OFF"
                        )
                    }

                    AnimalCheckbox(
                        checked = checkboxChecked,
                        onCheckedChange = { checkboxChecked = it },
                        label = "I agree to the island rules",
                        size = CheckboxSize.MIDDLE
                    )
                }
            }

            AnimalDivider(type = DividerType.BROWN)

            // Content Section
            SectionTitle("Content Organization")
            val tabs = listOf(
                TabItem("Profile") {
                    Text("User profile content goes here.", color = TextColorBody)
                },
                TabItem("Inventory") {
                    Text("List of items in your bag.", color = TextColorBody)
                },
                TabItem("Settings") {
                    Text("App configuration and options.", color = TextColorBody)
                }
            )
            AnimalTabs(tabs = tabs, modifier = Modifier.fillMaxWidth())

            AnimalCollapse(
                header = "Frequently Asked Questions",
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "This library is inspired by Animal Crossing: New Horizons and ported to Jetpack Compose with pixel-perfect details.",
                    color = TextColorMuted,
                    lineHeight = 22.sp
                )
            }

            AnimalDivider(type = DividerType.YELLOW)

            // Code Section
            SectionTitle("Code Usage")
            AnimalCodeBlock(
                code = """
                    AnimalButton(
                        text = "Click Me",
                        onClick = { doSomething() }
                    )
                """.trimIndent(),
                modifier = Modifier.fillMaxWidth()
            )

            // Device Section
            SectionTitle("NookPhone Device")
            NookPhone()

            Spacer(modifier = Modifier.height(60.dp)) // Padding for bottom bar
        }

        // Modal Example
        if (showModal) {
            AnimalModal(
                title = "Island Alert!",
                onDismissRequest = { showModal = false },
                onConfirm = { showModal = false }
            ) {
                Text(
                    "Are you sure you want to perform this action? It cannot be undone!",
                    color = TextColorMuted,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title.uppercase(),
        color = TextColorSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 2.sp,
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 4.dp)
    )
}
