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
                var islandLoadingActive by remember { mutableStateOf(false) }

                AnimalLoading(active = islandLoadingActive) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = BgColor,
                        bottomBar = {
                            AnimalFooter(
                                type = FooterType.SEA,
                                modifier = Modifier.navigationBarsPadding()
                            )
                        }
                    ) { innerPadding ->
                        Showcase(
                            onStartLoading = { islandLoadingActive = true },
                            modifier = Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                        )
                    }
                }

                if (islandLoadingActive) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(3000)
                        islandLoadingActive = false
                    }
                }
            }
        }
    }
}

@Composable
fun Showcase(
    onStartLoading: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        TabItem("Buttons") { ButtonTab() },
        TabItem("Forms") { FormTab() },
        TabItem("Containers") { ContainerTab() },
        TabItem("Extra") { ExtraTab(onStartLoading = onStartLoading) }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimalTime()
        AnimalTabs(tabs = tabs, modifier = Modifier.fillMaxWidth().weight(1f))
    }
}

@Composable
fun ButtonTab() {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionTitle("Button Types")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AnimalButton(text = "Primary", onClick = {}, modifier = Modifier.weight(1f))
            AnimalButton(text = "Danger", type = ButtonType.DANGER, onClick = {}, modifier = Modifier.weight(1f))
        }

        SectionTitle("Button States")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AnimalButton(text = "Disabled", enabled = false, onClick = {}, modifier = Modifier.weight(1f))
            AnimalButton(text = "Loading", loading = true, onClick = {}, modifier = Modifier.weight(1f))
        }

        SectionTitle("Button Sizes")
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AnimalButton(text = "Small Size", size = ButtonSize.SMALL, onClick = {})
            AnimalButton(text = "Middle Size", size = ButtonSize.MIDDLE, onClick = {})
            AnimalButton(text = "Large Size", size = ButtonSize.LARGE, onClick = {})
        }
    }
}

@Composable
fun FormTab() {
    var inputText by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<SelectOption?>(null) }
    var switchChecked by remember { mutableStateOf(true) }
    var checkboxChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionTitle("Input Fields")
        AnimalInput(value = inputText, onValueChange = { inputText = it }, placeholder = "Normal input...")
        AnimalInput(value = "Disabled Text", onValueChange = {}, enabled = false)
        AnimalInput(value = "Error State", onValueChange = {}, isError = true)

        SectionTitle("Select & Dropdown")
        AnimalSelect(
            options = listOf(SelectOption("Apple", "1"), SelectOption("Orange", "2"), SelectOption("Peach", "3")),
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )

        SectionTitle("Switches")
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
            AnimalSwitch(checked = switchChecked, onCheckedChange = { switchChecked = it }, checkedChildren = "ON", unCheckedChildren = "OFF")
            AnimalSwitch(checked = !switchChecked, onCheckedChange = {}, enabled = false)
            AnimalSwitch(checked = switchChecked, onCheckedChange = { switchChecked = it }, size = SwitchSize.SMALL)
        }

        SectionTitle("Checkboxes")
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AnimalCheckbox(checked = checkboxChecked, onCheckedChange = { checkboxChecked = it }, label = "Middle (Default)")
            AnimalCheckbox(checked = !checkboxChecked, onCheckedChange = {}, label = "Disabled", enabled = false)
            AnimalCheckbox(checked = checkboxChecked, onCheckedChange = { checkboxChecked = it }, label = "Large Size", size = CheckboxSize.LARGE)
        }
    }
}

@Composable
fun ContainerTab() {
    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionTitle("Cards")
        AnimalCard(title = "Default Card") {
            Text("This is a standard card with a shadow and padding.", color = TextColorBody)
        }
        AnimalCard(type = CardType.TITLE, color = AppPink, title = "NookPhone Style") {
            Text("Organic rounded corners and custom colors.", color = TextColorBody)
        }

        SectionTitle("Collapse")
        AnimalCollapse(header = "Click to Expand") {
            Text("Hidden content revealed with a smooth animation!", color = TextColorMuted)
        }
        AnimalCollapse(header = "Disabled Panel", enabled = false) {}

        SectionTitle("Modal Dialog")
        AnimalButton(text = "Open Blob Modal", onClick = { showModal = true }, modifier = Modifier.fillMaxWidth())

        if (showModal) {
            AnimalModal(
                title = "Important!",
                onDismissRequest = { showModal = false },
                onConfirm = { showModal = false }
            ) {
                Text("This modal uses a unique SVG path for its organic shape.", color = TextColorMuted)
            }
        }
    }
}

@Composable
fun ExtraTab(onStartLoading: () -> Unit) {
    var trigger by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionTitle("Island Loading")
        AnimalButton(
            text = "Launch Island Loading",
            onClick = onStartLoading,
            modifier = Modifier.fillMaxWidth()
        )

        SectionTitle("Typewriter & Icons")
        AnimalCard {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AnimalTypewriter(text = "Hello! Welcome to my island.", trigger = trigger) {
                    Text(it, color = TextColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                AnimalButton(text = "Replay", size = ButtonSize.SMALL, onClick = { trigger++ })
                
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    AnimalIcon(name = AnimalIconName.LEAF, tint = SuccessColor)
                    AnimalIcon(name = AnimalIconName.DIY, tint = AppOrange)
                    AnimalIcon(name = AnimalIconName.MILES, tint = AppBlue)
                }
            }
        }

        SectionTitle("Dividers")
        AnimalDivider(type = DividerType.BROWN)
        AnimalDivider(type = DividerType.TEAL)
        AnimalDivider(type = DividerType.WAVE_YELLOW)

        SectionTitle("Code Block")
        AnimalCodeBlock(code = "val theme = AnimalIslandUITheme {\n  // Content\n}")

        SectionTitle("NookPhone")
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            NookPhone()
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
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp)
    )
}
