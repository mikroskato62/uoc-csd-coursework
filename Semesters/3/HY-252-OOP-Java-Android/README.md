# HY-252: Object-Oriented Programming (Java & Android)

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-252 — Object-Oriented Programming (Αντικειμενοστραφής Προγραμματισμός)  
> **Semester:** Fall (Semester 3)  
> **Author / Student:** Rafail-Ioannis Petrakis (`csd5748`)  
> **Language & Platforms:** Java 8+ / 17 / 25, Swing & AWT, Android SDK, JUnit 4  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-252 covers foundational and advanced Object-Oriented Programming principles using the Java programming language and Android ecosystem. Topics include class hierarchies, inheritance, polymorphism, abstract classes, interfaces, generic programming, event-driven GUI development (Swing/AWT), multithreading, audio synthesis with JFugue, functional programming (Streams & Lambdas), and native Android mobile application engineering.

The course culminates in a comprehensive capstone term project implementing the **"Amphipolis" Desktop Board Game** using a strict **Model-View-Controller (MVC)** architectural pattern.

## 📂 Homework & Project Directory Structure

| Directory | Focus & Components | Deliverables & Technologies |
| :--- | :--- | :--- |
| [`HW-01/`](./HW-01) | Java Fundamentals & Core OOP | Card game simulation, encapsulation, class inheritance, unit verification. |
| [`HW-02/`](./HW-02) | Advanced GUIs & Audio Synthesis | Java Swing UI, event dispatching, multithreading, and acoustic playback with JFugue. |
| [`HW-03/`](./HW-03) | Modern Java & Functional Paradigms | Lambda expressions, Stream API processing, method references, collections. |
| [`Android/`](./Android) | Native Mobile Development | Mobile Sudoku Solver app built with Android SDK, XML layouts, and backtracking algorithms. |
| [`Project/`](./Project) | Capstone MVC Term Project | **"Amphipolis" Board Game** — Standalone executable (`Amphipolis.jar`), Swing canvas, audio engine, state serialization (`cookies.txt`), JUnit 4 suite, and full Javadoc. *(See dedicated [`Project/README.md`](./Project/README.md))* |

## 🛠️ Execution & Launch Instructions

### 1. Launching the "Amphipolis" Board Game (Standalone JAR)
```bash
cd Project
java -jar Amphipolis.jar
```

### 2. Running the JUnit 4 Test Suite
```bash
cd Project
javac -cp ".;lib/junit-4.13.1.jar;lib/hamcrest-core-1.3.jar;src" -d out src/PhaseB/JUnitTests/GameTests.java
java -cp ".;lib/junit-4.13.1.jar;lib/hamcrest-core-1.3.jar;out" org.junit.runner.JUnitCore PhaseB.JUnitTests.GameTests
```

### 3. Compiling and Running Assignments
```bash
# Example: Compile and run Homework 1
cd HW-01
javac -d bin src/*.java
java -cp bin Main
```

---