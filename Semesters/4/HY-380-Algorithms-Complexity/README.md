# HY-380: Algorithms & Complexity

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-380 — Algorithms & Complexity (Αλγόριθμοι & Πολυπλοκότητα)  
> **Semester:** Spring (Semester 4)  
> **Category:** Core Theoretical Computer Science  
> **ECTS Credits:** 8  
> **Format:** 5 Problem Sets & Mathematical Proof Solutions  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-380 is the advanced core algorithms curriculum at UoC CSD. It covers algorithm design paradigms, rigorous asymptotic runtime analysis, computational graph theory, network flows, and the theory of NP-Completeness.

## 📋 Course Topics

1. **Foundations & Analysis Paradigms:**
   * Asymptotic notation ($O, \Omega, \Theta, o, \omega$), Master Theorem, recursion tree analysis, substitution method.
   * Divide-and-Conquer: MergeSort, QuickSort (randomized pivot selection, expected runtime analysis), order statistics (Median-of-Medians $O(n)$ selection).

2. **Greedy Algorithms & Dynamic Programming:**
   * Greedy choice property and optimal substructure: Huffman coding, interval scheduling, minimum spanning trees (Kruskal's with Disjoint Set Union - DSU, Prim's algorithm).
   * Dynamic Programming (DP): Longest Common Subsequence (LCS), 0/1 Knapsack, Matrix Chain Multiplication, Bellman-Ford shortest paths, Floyd-Warshall all-pairs shortest paths.

3. **Graph Algorithms & Network Flows:**
   * Breadth-First Search (BFS), Depth-First Search (DFS), topological sorting, strongly connected components (Kosaraju-Sharir, Tarjan).
   * Single-source shortest paths: Dijkstra's algorithm with priority queues.
   * Maximum Network Flow: Ford-Fulkerson method, Edmonds-Karp algorithm, Max-Flow Min-Cut Theorem, bipartite maximum matching.

4. **NP-Completeness & Computational Intractability:**
   * Complexity classes $\mathbf{P}$, $\mathbf{NP}$, $\mathbf{co\text{-}NP}$, $\mathbf{NP\text{-}hard}$, and $\mathbf{NP\text{-}complete}$.
   * Polynomial-time reductions ($A \le_p B$).
   * Cook-Levin Theorem (SAT is NP-Complete).
   * Classic NP-Complete reductions: 3-SAT $\le_p$ Independent Set $\le_p$ Vertex Cover $\le_p$ Set Cover; Hamiltonian Cycle $\le_p$ Traveling Salesperson Problem (TSP).

## 📂 Homework Directory Structure

| Directory | Contents | Focus Areas |
| :--- | :--- | :--- |
| [`HW-01/`](./HW-01) | `Problem-Set-01.pdf`, `Solution-01.pdf` | Asymptotic bounds, recurrence relations, Master Theorem proofs. |
| [`HW-02/`](./HW-02) | `Problem-Set-02.pdf`, `Solution-02.pdf` | Divide-and-conquer paradigms, order statistics, randomized algorithms. |
| [`HW-03/`](./HW-03) | `Problem-Set-03.pdf`, `Solution-03.pdf` | Greedy algorithms, Huffman coding, minimum spanning trees (Kruskal, Prim). |
| [`HW-04/`](./HW-04) | `Problem-Set-04.pdf`, `Solution-04.pdf` | Dynamic programming (LCS, Knapsack, all-pairs shortest paths). |
| [`HW-05/`](./HW-05) | `Problem-Set-05.pdf`, `Solution-05.pdf` | Graph connectivity, topological ordering, network flows (Ford-Fulkerson). |
| [`Bonus/`](./Bonus) | `Bonus-Problems.pdf`, `Bonus-Solution.pdf` | NP-completeness reductions, 3-SAT, Vertex Cover, and TSP hardness. |
