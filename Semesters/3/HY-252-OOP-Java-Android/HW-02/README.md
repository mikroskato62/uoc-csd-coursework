# 🛡️ Assignment 2 (HW2): Advanced OOP, GUIs, Multithreading & Sudoku MVC
> **Course:** [HY-252: Object-Oriented Programming](https://elearn.uoc.gr/course/view.php?id=5974) — University of Crete (UoC CSD)  
> **Author / Student:** Rafail Ioannis Petrakis (`csd5748`)  
> **Specification:** [`HW2.pdf`](./HW2.pdf)  
> **Dependencies:** `jfugue-4.0.3.jar` (JFugue Music API), Java Swing/AWT

---

## 📑 Overview

Assignment 2 focuses on advanced Object-Oriented Programming principles: polymorphic hierarchies, interface design, event-driven graphical interfaces (Swing), multithreading with audio synthesis (JFugue), and the Model-View-Controller (MVC) architectural pattern.

*(Note: The standalone mobile Android Sudoku application is located at [`HY-252/Android/`](../Android/README.md)).*

---

## 📂 Sub-Exercises & Structure

```text
HW2/
├── src/
│   ├── A21/
│   │   └── SensorsAssignment.java     # Decoupled sensor hierarchy (Analog & Digital)
│   ├── A22/
│   │   └── HomeSecurityAssignment.java# Multi-zone home security management system
│   ├── A23/
│   │   └── AlarmGUIAssignment.java    # Swing GUI security dashboard & control panel
│   ├── A24/
│   │   └── AlarmThreadsAssignment.java# Multithreaded alarm with JFugue music synthesis
│   ├── A25/                           # Star Wars battle simulation:
│   │   ├── creatures/                 #  - Combatant, Jedi, Sith, Droid, MedicalDroid
│   │   ├── weapons/                   #  - Weapon, Lightsaber
│   │   └── Main.java                  #  - Turn-based battle engine
│   ├── A26/
│   │   └── MVC/                       # Desktop Sudoku Solver (Model, View, Controller, App)
│   ├── Examination/
│   │   └── EA2a.java                  # Laboratory practical examination solution
│   ├── TemplateValidator/
│   │   └── ValidatorScript.java       # Structure and signature compliance checker
│   └── doc/                           # Generated enterprise Javadoc HTML documentation
├── jfugue-4.0.3.jar                   # JFugue MIDI/Music synthesis library
├── HW2.pdf                            # Official assignment problem sheet
└── HW2.iml                            # IntelliJ module definition
```

---

## 🔍 Exercise Descriptions

### 1. `A21/SensorsAssignment.java` — Sensor Interface & Hierarchy
* Models an object-oriented telemetry sensor framework.
* Abstract base classes and decoupled interfaces for `TemperatureSensor`, `SmokeSensor`, `MotionSensor`, and `DoorContactSensor`.
* Polymorphic reading evaluations, threshold triggers, and device status monitoring.

### 2. `A22/HomeSecurityAssignment.java` — Home Security System
* Multi-zone control unit managing a network of security sensors.
* Zone arming/disarming modes, panic triggers, status checks, and security event logging.
* Complete HTML API documentation generated in `src/doc/`.

### 3. `A23/AlarmGUIAssignment.java` — Graphical Security Dashboard
* Desktop Java Swing control panel for the home security system.
* Visual zone status maps, dynamic alarm indicator LEDs, interactive sensor simulation buttons, and keypad arm/disarm controls.

### 4. `A24/AlarmThreadsAssignment.java` — Multithreading & Sound Synthesis
* Concurrent alarm dispatching running background worker threads alongside the Swing Event Dispatch Thread (EDT).
* Integrated with **JFugue 4.0.3** to synthesize musical alarm patterns and acoustic sirens in real time without freezing the UI.

### 5. `A25/` — Star Wars OOP Battle Simulation
* Deep polymorphic inheritance model:
  * `creatures/`: `Combatant` (base), `Humanoid`, `Droid`, `Jedi` (Force abilities), `Sith` (Dark side mechanics), `BattleDroid`, `MedicalDroid` (healing abilities), `WeaponCarrier`.
  * `weapons/`: `Weapon` (abstract), `Lightsaber` (crystal & blade mechanics).
* Dynamic method dispatch simulating turn-based encounters, weapon exchanges, and health state transitions.

### 6. `A26/MVC/` — Desktop Sudoku Solver (MVC Pattern)
* Complete Model-View-Controller desktop solver:
  * **`Model.java`**: Board matrix state, constraint verification, and backtracking solver algorithm.
  * **`View.java`**: Interactive $9 \times 9$ Swing GUI grid, custom fonts, color highlights, and action buttons.
  * **`Controller.java`**: Event routing, input validation, and asynchronous puzzle solving.
  * **`App.java`**: Application launcher.

---

## 🚀 How to Compile & Run

From the `HY-252/HW2` directory:

```bash
# Navigate to the HW2 directory
cd HY-252/HW2

# 1. Run Sensor Simulation (A21)
javac src/A21/*.java
java -cp src A21.SensorsAssignment

# 2. Run Home Security System (A22)
javac src/A22/*.java
java -cp src A22.HomeSecurityAssignment

# 3. Run Swing Alarm GUI (A23)
javac src/A23/*.java
java -cp src A23.AlarmGUIAssignment

# 4. Run Multithreaded Audio Alarm System (A24, requires JFugue)
javac -cp ".;jfugue-4.0.3.jar;src" -d out src/A24/AlarmThreadsAssignment.java
java -cp ".;jfugue-4.0.3.jar;out" A24.AlarmThreadsAssignment

# 5. Run Star Wars Battle Simulation (A25)
javac src/A25/Main.java src/A25/creatures/*.java src/A25/weapons/*.java
java -cp src A25.Main

# 6. Run Desktop Sudoku MVC Solver (A26)
javac src/A26/MVC/*.java
java -cp src A26.MVC.App
```
