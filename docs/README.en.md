# 🏝 Animal-Island-UI

<div align="center">
    <img src="./img/readme-home.png" alt="animal-island-ui" style="border-radius: 12px; width: 40%; display: block; margin: 0 auto;" />    
</div>

<div align="center">
A lightweight Android Jetpack Compose component library inspired by Animal Crossing: New Horizons
</div>

<br/>

<div align="center">
    <a href="https://github.com/liuyuhong0324/animal-island-ui/stargazers"><img src="https://img.shields.io/github/stars/liuyuhong0324/animal-island-ui?style=flat-square" alt="Stars"></a>
    <a href="LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square" alt="License"></a>
    <a href="https://github.com/liuyuhong0324/animal-island-ui/releases"><img src="https://img.shields.io/github/v/tag/liuyuhong0324/animal-island-ui?label=version&style=flat-square" alt="Version"></a>
</div>

<br/>

<p align="center">
    <a href="../README.md">简体中文</a> | English
</p>

---

## Introduction

**Animal Island UI** is a lightweight Android Jetpack Compose component library. The design style is inspired by Nintendo's Animal Crossing: New Horizons game interface, featuring common UI components such as Button, Card, Modal, and Input, along with a complete design system and theme system.

All visual elements, layouts, icons, and animations are independently designed and implemented, without directly using any official Nintendo artwork, code, or resource files.

> 💡 **Special Thanks** to [@guokaigdg](https://github.com/guokaigdg) for the original project [animal-island-ui](https://github.com/guokaigdg/animal-island-ui). This project is an Android version implementation based on that design.

### Technical Features

- 🎨 **Complete Design System** - Systematized definitions of colors, fonts, spacing, and corner radius
- 🚀 **Jetpack Compose** - Built on the latest Android declarative UI framework
- 📦 **Lightweight Design** - Modular components that are easy to integrate

## Preview

|                               |
|-------------------------------|
| ![](../docs/img/showcase.png) |

## Getting Started

### Requirements

| Item | Version |
|---|---|
| Android SDK | API 24+ |
| Compose | 1.6.0+ |
| Kotlin | 1.9.0+ |
| Java | 11+ |

### Installation

1. **Add Dependencies** - Add the local module to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app", ":animalislandui")
```

2. **Reference in Your Application**:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":animalislandui"))
}
```

3. **Apply the Theme**:

```kotlin
// MainActivity.kt
import ml.liuyuhong.animalislandui.theme.AnimalIslandUITheme

@Composable
fun App() {
    AnimalIslandUITheme {
        // Your content here
    }
}
```

## Usage Examples

### Button

```kotlin
import ml.liuyuhong.animalislandui.components.AnimalButton
import ml.liuyuhong.animalislandui.components.ButtonType
import ml.liuyuhong.animalislandui.components.ButtonSize

// Simple button
AnimalButton(
    text = "Click Me",
    onClick = { /* handle click */ }
)

// Customized style
AnimalButton(
    text = "Delete",
    onClick = { /* handle click */ },
    type = ButtonType.DANGER,
    size = ButtonSize.LARGE,
    enabled = true
)

// Loading state
AnimalButton(
    text = "Loading...",
    onClick = { },
    loading = true
)
```

### Card

```kotlin
import ml.liuyuhong.animalislandui.components.AnimalCard
import ml.liuyuhong.animalislandui.components.CardType
import ml.liuyuhong.animalislandui.theme.AppYellow

AnimalCard(
    title = "Card Title",
    color = AppYellow,
    type = CardType.TITLE
) {
    Text("Card content")
    // More content
}
```

### Icon

```kotlin
import ml.liuyuhong.animalislandui.components.Icon

Icon(
    iconType = "camera", // icon_camera, icon_chat, icon_map, etc.
    size = 24.dp,
    tint = TextColor
)
```

### Modal

```kotlin
import ml.liuyuhong.animalislandui.components.Modal

var showModal by remember { mutableStateOf(false) }

if (showModal) {
    Modal(
        onDismiss = { showModal = false }
    ) {
        Text("Modal content")
        AnimalButton(
            text = "Close",
            onClick = { showModal = false }
        )
    }
}
```

## Core Components

| Component | Description | Purpose |
|---|---|---|
| **Button** | Various button types | Primary interaction entry point |
| **Card** | Card container component | Information grouping display |
| **Input** | Text input field | User text input |
| **Select** | Dropdown selector | Option selection |
| **Checkbox** | Checkbox component | Multiple selection |
| **Switch** | Toggle switch | Binary state switching |
| **Modal** | Modal dialog | Confirmation or form filling |
| **Tabs** | Tabbed content | Content categorization display |
| **Collapse** | Collapsible panel | Hierarchical content display |
| **Loading** | Loading animation | Async operation indication |
| **Divider** | Divider line | Content separation |
| **Icon** | Icon set | UI decoration elements |
| **Typewriter** | Typewriter effect | Animated text display |
| **Time** | Time display | Time-related information |
| **CodeBlock** | Code block | Code display |
| **Phone** | Phone frame | Content display container |
| **Footer** | Footer | Page footer information |

## Project Structure

```
AnimalIslandUIExample/
├── animalislandui/              # Component library module
│   ├── src/main/java/
│   │   └── ml/liuyuhong/animalislandui/
│   │       ├── components/      # UI components
│   │       │   ├── Button.kt
│   │       │   ├── Card.kt
│   │       │   ├── Modal.kt
│   │       │   └── ...
│   │       ├── theme/           # Theme system
│   │       │   ├── Color.kt
│   │       │   ├── Type.kt
│   │       │   └── Theme.kt
│   │       └── ...
│   └── build.gradle.kts
├── app/                         # Example application
│   ├── src/main/
│   └── build.gradle.kts
```

## Design System

### Color System

The component library includes a complete set of color definitions:

- **Primary Colors** - `PrimaryColor`, `PrimaryColorHover`, `PrimaryColorActive`
- **Background Colors** - `BgColor`, `BgColorContent`, `BgColorSecondary`
- **Text Colors** - `TextColor`, `TextColorSecondary`, `TextColorBody`
- **Functional Colors** - `SuccessColor`, `WarningColor`, `ErrorColor`
- **Other Colors** - Border colors, shadow colors, etc.

All colors are defined in `theme/Color.kt` and can be imported directly for use.

## Documentation & Resources

| Resource | Description |
|---|---|
| [`DESIGN_PROMPT.md`](../DESIGN_PROMPT.md) | Design system prompt - Color palettes, fonts, sizing standards, etc. |
| [`SKILL.md`](../SKILL.md) | Development specification document - Pixel-level style guide and new component checklist |

## Important Notes

⚠️ **Commercial Use Restrictions**

- This project is for personal learning, research, and non-commercial display only
- Commercial use, resale, or any profit-making behavior is prohibited
- Not intended for commercial products, enterprise projects, external services, or paid templates
- Users assume all risks resulting from the use of this component library

## Copyright & Disclaimer

- This project is **not an official Nintendo product** and has no affiliation, authorization, or partnership with Nintendo Co., Ltd.
- The game name in the project title is merely a stylistic reference and does not constitute trademark use or brand association
- All interface styles are only for design inspiration and do not constitute reproduction or infringement of the original work
- If the copyright holder believes there is infringement, please contact via email and we will make corrections or deletions immediately

## Contributing

We welcome issue reports and pull requests! Please read [CONTRIBUTING.md](./CONTRIBUTING.md) for contribution guidelines.

## Contact

- 📧 Have questions? Submit an [Issue](https://github.com/liuyuhong0324/animal-island-ui/issues)
- 🐛 Found a bug? Submit an [Issue](https://github.com/liuyuhong0324/animal-island-ui/issues)
- 💡 Feature suggestions? Welcome to discuss
- 🤝 Copyright-related inquiries? Contact via email

## License

MIT

This project is released under the MIT open-source license and is limited to educational use. The author is not responsible for any legal issues or damages resulting from the use of this library.

---

<div align="center">
  Made with ❤️ by <a href="https://github.com/liuyuhong0324">@liuyuhong0324</a>
</div>

## Acknowledgments

Special thanks to [@guokaigdg](https://github.com/guokaigdg) for creating the original project [animal-island-ui](https://github.com/guokaigdg/animal-island-ui). This project is an Android Jetpack Compose implementation based on that design concept.
