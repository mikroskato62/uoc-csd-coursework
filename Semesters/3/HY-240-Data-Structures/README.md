# HY-240: Data Structures (C Systems Implementation)

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-240 — Data Structures (Δομές Δεδομένων)  
> **Semester:** Fall (Semester 3)  
> **Language:** C (ANSI C / C99), CMake  
> **Author / Student:** Rafail-Ioannis Petrakis (`csd5748`)  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-240 is one of the most demanding core courses in the UoC CSD curriculum. It combines rigorous asymptotic algorithm analysis with extensive low-level systems programming in C, implementing advanced Abstract Data Types (ADTs), non-linear tree structures, dynamic heaps, hash tables, and secondary-memory indexing.

## 🚀 Capstone Term Project: "Automated Library Management System"

The course culminates in a two-phase systems programming project implementing an end-to-end relational library indexing engine in pure C (`Library.c` — over 1,500 lines of robust C code).

### Architecture & Data Structures Implemented

```
                        [Library System Architecture]
                                      │
         ┌────────────────────────────┼──────────────────────────┐
         │                            │                          │
   [Book Catalog]               [User Registry]            [Active Loans]
   • Dynamic Hash Table         • Balanced Tree Index      • Min/Max Priority
   • Polynomial Modulo          • Logarithmic Lookup         Binary Heap
   • Separate Chaining          • Member Record ADT        • Dynamic Re-heapify
```

1. **Phase 1: In-Memory Indexing & ADTs**
   * **Book Catalog ADT**: Dynamic array of genre buckets with linked node chains and custom polynomial hashing.
   * **Member Registry ADT**: Singly and doubly linked list representations supporting fast dynamic insertion, deletion, and chronological traversal.
   * **Loan Registry ADT**: Min-Heap priority queue managing active book loans, overdue detection, and dynamic re-heapification ($O(\log n)$ updates).
   * **Scoring & Review Metric Engine**: Cumulative review tracking, rating averages, and dynamic book ranking.

2. **Phase 2: Advanced Search, Binary Search Trees & Complex Filtering**
   * **Tree Indexing**: Binary Search Tree (BST) indexes mapping book identifiers to dynamic records.
   * **Range Queries & In-Order Traversal**: Performing filtered lookups across genre subtrees and rating thresholds.
   * **Memory Management**: Zero-leak guarantee verified under Valgrind, with dedicated cascading deallocators for all dynamic nodes.

## 📂 Repository Contents

```text
HY-240-Data-Structures/
├── README.md                           # Course & architecture documentation
├── Specifications/                     # Official problem set specifications
│   ├── Course-Exercises-Spec.pdf       # Practical exercises specification
│   └── Theoretical-Homeworks-Spec.pdf  # Theoretical problem sets specification
└── Project/                            # Term Project implementation
    ├── Library.c                       # Master C source implementation (50 KB)
    ├── CMakeLists.txt                  # CMake build configuration
    ├── ReadMe.md                       # Project execution notes
    ├── Documentation/                  # Phase specifications & tutorials
    │   ├── Project-Phase-1-Spec.pdf
    │   ├── Project-Phase-2-Spec.pdf
    │   ├── Project-Tutorial-1.pdf
    │   ├── Project-Tutorial-2.pdf
    │   ├── Theory-Notes.pdf
    │   └── C-Refresher-Tutorial.pdf
    └── tests/                          # Phase 1 & 2 test input datasets
        ├── Phase1/
        └── Phase2/
```

## 🛠️ Build & Execution

```bash
cd Project

# Build using CMake:
cmake -B build
cmake --build build

# Or compile directly using GCC:
gcc -Wall -Wextra -std=c99 Library.c -o library_system
./library_system
```
