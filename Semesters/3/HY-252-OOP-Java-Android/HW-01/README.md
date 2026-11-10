# 📝 Assignment 1 (HW1): Java Fundamentals & Core Algorithms
> **Course:** [HY-252: Object-Oriented Programming](https://elearn.uoc.gr/course/view.php?id=5974) — University of Crete (UoC CSD)  
> **Author / Student:** Rafail Ioannis Petrakis (`csd5748`)  
> **Specification:** [`HW1.pdf`](./HW1.pdf)

---

## 📑 Overview

Assignment 1 focuses on core Java syntax, control flow, stream I/O, 2D matrix manipulation, recursion, backtracking algorithms, basic GUI dialogs, and symmetric stream encryption.

---

## 📂 Sub-Exercises & Structure

```text
HW1/
├── src/
│   ├── A11/
│   │   └── DrawAlpha.java             # Multimodal rendering of the Greek letter 'A'
│   ├── A12/
│   │   ├── MagicSquareChecker.java    # Magic Square algebraic validation
│   │   └── MySet.java                 # Custom integer set implementation
│   ├── A13/
│   │   ├── Downloader.java            # Network stream reader via HTTP URL
│   │   └── MagicSquareIO.java         # Disk file I/O for magic squares
│   ├── A14/
│   │   ├── SudokuGenerator.java       # Backtracking Sudoku generator & solver
│   │   └── SudokuUtils.java           # Grid validation & solving utilities
│   ├── A15/
│   │   └── Encrypt.java               # Caesar stream encryption/decryption (CLI & GUI)
│   ├── Examination/
│   │   └── A1EB.java                  # Midterm laboratory examination solution
│   └── TemplateValidator/
│       └── ValidatorScript.java       # Structure and signature compliance checker
├── assets/                            # Test input datasets & cipher text files
├── HW1.pdf                            # Official assignment problem sheet
├── HW1.iml                            # IntelliJ module definition
└── README.md                          # Dedicated Assignment 1 Documentation
```

---

## 🔍 Exercise Descriptions

### 1. `A11/DrawAlpha.java` — Multimodal Letter Rendering
Renders the capital Greek letter $\Alpha$ of variable scale $L \in [4, 20]$ across four different output modes ($M$):
* **Console Mode (`c`)**: Formatted ASCII star matrix printed to standard output.
* **Window Dialog Mode (`w`)**: Graphical `JOptionPane` message dialog with custom monospaced font (`Lucida Console`).
* **HTML File Mode (`f`)**: Generates an `A.html` web page displaying the letter with dynamically computed font sizes.
* **Graphics2D Mode (`g`)**: Native AWT `Frame` rendering vector geometry (`Line2D.Double`).
* **Features**: Cascading recursive rendering ($L-1$) and interactive input loops.

### 2. `A12/MagicSquareChecker.java` & `MySet.java` — Magic Square Verification
* Reads an $N \times N$ matrix ($2 < n < 10$) and verifies whether it forms a valid Magic Square (equal row, column, and diagonal sums over distinct integers in $[1, n^2]$).
* Includes a custom, lightweight integer set (`MySet`) managing set membership (`add`, `contains`) without using Java collection framework classes.

### 3. `A13/Downloader.java` & `MagicSquareIO.java` — Network Streams & File I/O
* **`Downloader.java`**: Connects to remote HTTP endpoints via `java.net.URL` streams and outputs remote text content.
* **`MagicSquareIO.java`**: Reads and parses magic square matrices from local disk files (`MagicSquareSavedFile.txt`) and serializes formatted tables back to disk.

### 4. `A14/SudokuGenerator.java` & `SudokuUtils.java` — Sudoku Backtracking Engine
* Generates $N$ valid $9 \times 9$ Sudoku puzzle boards with $X$ empty cells/clues.
* Solves puzzles using recursive backtracking, validates grid constraints, and benchmarks runtime with built-in execution timeout safeguards.

### 5. `A15/Encrypt.java` — Caesar Stream Cipher & GUI FileChooser
* Additive modulo Caesar cipher supporting file encryption and decryption.
* **Dual Execution Modes**:
  1. **CLI Mode**: `java A15.Encrypt <inputFile> <K>`
  2. **GUI Mode**: Interactive file selection via `JFileChooser` with graphical input prompts.
* Measures transformation duration down to nanoseconds.

---

## 🚀 How to Compile & Run

From the `HY-252/HW1/src` directory:

```bash
# Navigate to the source directory
cd HY-252/HW1/src

# 1. Run DrawAlpha (Console mode, scale 10)
javac A11/DrawAlpha.java
java A11.DrawAlpha c 10

# 2. Run Magic Square Checker
javac A12/*.java
java A12.MagicSquareChecker

# 3. Run Sudoku Generator (generate 5 boards with 50 empty cells)
javac A14/*.java
java A14.SudokuGenerator 5 50

# 4. Run Encrypt / Decrypt Utility (CLI mode with key K=3)
javac A15/Encrypt.java
java A15.Encrypt ../Test_Original.txt 3

# Or launch the interactive GUI file chooser:
java A15.Encrypt
```
