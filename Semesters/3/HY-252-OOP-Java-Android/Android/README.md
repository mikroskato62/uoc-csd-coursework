# 📱 Android Sudoku Solver Mobile App
> **Course:** [HY-252: Object-Oriented Programming](https://elearn.uoc.gr/course/view.php?id=5974) — University of Crete (UoC CSD)  
> **Author / Student:** Rafail Ioannis Petrakis (`csd5748`)  
> **Framework:** Android SDK, Java, Gradle (Kotlin DSL `build.gradle.kts`)

---

## 📑 Overview

A native Android mobile application providing an interactive **Sudoku Grid Interface and Solver**. Designed as part of the HY-252 course to explore mobile UI development, the Android Activity lifecycle, XML view layouts, and touch event handling while maintaining clean Model-View-Controller separation.

---

## 🗂️ Project Structure

```text
Android/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/example/hy252_a26/
│       │   │   ├── MainActivity.java       # Launcher & navigation activity
│       │   │   ├── SolverActivity.java     # Interactive Sudoku grid & solver UI
│       │   │   ├── AndroidController.java  # Controller adapting user touch inputs
│       │   │   └── AndroidModel.java       # Grid state & backtracking solver algorithm
│       │   ├── res/
│       │   │   ├── layout/                 # XML UI layouts (activity_main, activity_solver)
│       │   │   ├── values/                 # Colors, strings, themes & styles
│       │   │   └── drawable/               # Vector icons and grid borders
│       │   └── AndroidManifest.xml         # App configuration & activity declarations
│       └── test/                           # Android local unit tests
├── gradle/                                 # Gradle wrapper files
├── build.gradle.kts                        # Root build configuration
├── settings.gradle.kts                     # Gradle project settings
└── gradlew / gradlew.bat                   # Gradle build scripts
```

---

## ⚙️ Features

* **Interactive $9 \times 9$ Touch Grid**: Tap any cell to input or edit numbers from 1 to 9.
* **Instant Backtracking Solver**: Runs the recursive constraint-satisfaction solving algorithm to solve puzzles in real time.
* **Responsive Layouts**: Custom XML views with 3x3 box boundaries, number keypads, and control buttons (Solve, Clear, Reset).
* **MVC Separation**: Clean decoupling between Android UI activities (`View`), grid logic (`Model`), and event listeners (`Controller`).

---

## 🚀 How to Run in Android Studio

1. Launch **Android Studio**.
2. Click **File ➔ Open...** and select this directory: `HY-252/Android`.
3. Wait for Gradle sync to complete.
4. Select an Android Emulator (AVD) or connect a physical device via USB debugging.
5. Click **Run** (`Shift + F10`) to build, install, and launch the application.
