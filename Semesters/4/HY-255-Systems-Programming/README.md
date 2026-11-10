# HY-255: Software Systems & Systems Programming Lab (ANSI C)

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-255 — Software Systems (Εργαστήριο Λογισμικού)  
> **Semester:** Spring (Semester 4)  
> **Language & Standards:** Strict ANSI C (C89/C90: `-Wall -ansi -pedantic -g`), GNU Make, GDB, Valgrind  
> **Author / Student:** Rafail-Ioannis Petrakis (`csd5748`)  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-255 is the benchmark systems programming laboratory course at UoC CSD. It enforces strict compliance with the **ANSI C (C89/C90)** language standard, defensive programming, automated compilation with GNU Make, memory leak prevention with Valgrind, and low-level architectural efficiency.

## 📂 Laboratory Assignments

### 🔹 [`HW-01/`](./HW-01) — DFA Greek-to-Greeklish Text Stream Filter
* **Architecture**: Deterministic Finite Automaton (DFA) state machine processing character streams from standard input (`stdin`) and converting Greek characters to standard phonetic Greeklish.
* **Concepts**: Handling vowel-consonant diphthongs (e.g., `μπ`, `ντ`, `γκ`, `ου`), accent markers, capitalization preservation, and punctuation formatting via state transition dispatch.
* **Standard**: Strict ANSI C (`-Wall -ansi -pedantic`).
* **Execution**:
  ```bash
  cd HW-01
  make translate
  ./translate < tests/test1.7
  ```

### 🔹 [`HW-02/`](./HW-02) — String Module: Array Indexing vs. Pointer Arithmetic
* **Objective**: Re-implement standard C library string manipulation functions (`strlen`, `strcpy`, `strncpy`, `strcat`, `strncat`, `strcmp`, `strncmp`, `strstr`) under two distinct programming paradigms:
  1. `src/mystring_ars.c`: Pure array indexing (`str[i]`).
  2. `src/mystring_ptrs.c`: Pure pointer arithmetic (`*(str + i)` or `*ptr++`), with zero array indexing.
* **Testing**: Rigorous assertion test suite checking boundary conditions, `NULL` byte handling, and buffer limits.
* **Execution**:
  ```bash
  cd HW-02
  make all
  ./ar.exe # Tests array implementation
  ./ps.exe # Tests pointer arithmetic implementation
  ```

### 🔹 [`HW-03/`](./HW-03) — Generic Symbol Table ADT (Linked List & Hash Table)
* **Objective**: Design and implement a reusable, generic Abstract Data Type (ADT) `SymTable` managing string keys and generic `void*` value payloads.
* **Dual Backing Implementations**:
  1. `symtablelist.c`: Singly linked list representation with linear search and dynamic node insertion.
  2. `symtablehash.c`: Modulo hash table with separate chaining (collision resolution via dynamic buckets).
* **Hash Function**: Polynomial rolling hash with constant multiplier $65599$:
  $$\text{hash}(s) = \sum_{i=0}^{n-1} s[i] \times 65599^{n-1-i} \pmod{2^{32}}$$
* **Execution**:
  ```bash
  cd HW-03
  make list
  ./testsymtab
  make clean
  make hash
  ./testsymtab
  ```
