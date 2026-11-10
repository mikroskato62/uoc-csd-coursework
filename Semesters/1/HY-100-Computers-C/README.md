# HY-100: Introduction to Computer Science

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-100 — Introduction to Computer Science (Εισαγωγή στην Επιστήμη Υπολογιστών)  
> **Semester:** Fall (Semester 1)  
> **Language:** C (ANSI C / C99)  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-100 is the foundational programming and computer science course at UoC CSD. It introduces algorithmic problem solving, imperative programming, memory layout, pointers, and fundamental dynamic data structures in the C programming language.

## 📂 Homework Directory Structure

| Directory | Assignment Focus | Deliverables & Key Concepts |
| :--- | :--- | :--- |
| [`HW-01/`](./HW-01) | Introduction to C & Environment | `hw1.c`, `Problem-Set-01.pdf` — Standard I/O, compilation with `gcc`, control flow. |
| [`HW-02/`](./HW-02) | Basic Computations & Conditionals | `hw2a.c`, `hw2b.c`, `Problem-Set-02.pdf` — Floating-point precision, loops, nested conditionals. |
| [`HW-03/`](./HW-03) | Array Manipulation & Functions | `hw3a.c`, `hw3b.c`, `hw3c.c`, `Problem-Set-03.pdf` — 1D/2D arrays, matrix arithmetic, modular decomposition. |
| [`HW-04/`](./HW-04) | Dynamic Memory & Linked Lists | `hw4.c`, `Problem-Set-04.pdf` — Pointers, `malloc` / `free`, singly linked list insertion and traversal. |
| [`HW-05/`](./HW-05) | Advanced Data Structures & Search | `hw5.c`, `Problem-Set-05.pdf` — "Labyrinthos" randomized maze generator & DFS backtracking with custom stack ADT. |

## 🛠️ Compilation & Execution

All assignments can be compiled using `gcc`:

```bash
# Example: Compile and run Homework 5 (Labyrinthos)
cd HW-05
gcc -Wall -Wextra -std=c99 hw5.c -o labyrinthos
./labyrinthos
```
