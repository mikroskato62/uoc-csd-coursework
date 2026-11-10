# HY-280: Theory of Computation

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-280 — Theory of Computation (Θεωρία Υπολογισμού)  
> **Semester:** Fall (Semester 3)  
> **Category:** Core Theoretical Computer Science  
> **ECTS Credits:** 6  
> **Format:** Problem Sets, Student Submissions & Official Solutions  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-280 introduces the fundamental mathematical models of computation, formal language theory, and computability. It establishes the theoretical limits of what can and cannot be computed algorithmically.

## 📋 Course Topics

1. **Regular Languages & Finite Automata:**
   * Deterministic Finite Automata (DFA), Non-deterministic Finite Automata (NFA), $\epsilon$-transitions, subset construction (Rabin-Scott theorem).
   * Regular Expressions (Regex), equivalence with finite automata (Kleene's Theorem).
   * Non-regular languages and the **Pumping Lemma for Regular Languages**.
   * State minimization algorithms (Myhill-Nerode Theorem, equivalence relations).

2. **Context-Free Languages & Pushdown Automata:**
   * Context-Free Grammars (CFGs), derivation trees, ambiguity.
   * Pushdown Automata (PDA), equivalence with CFGs.
   * Chomsky Normal Form (CNF), CYK parsing algorithm concepts.
   * **Pumping Lemma for Context-Free Languages**.

3. **Turing Machines & Computability Theory:**
   * Standard Turing Machines (TM), multi-tape TMs, non-deterministic TMs, Church-Turing Thesis.
   * Decidable vs. Turing-recognizable (recursively enumerable) languages.
   * The **Halting Problem** ($A_{\text{TM}}$) and proof of undecidability via Cantor diagonalization.
   * Reducibility: Mapping reductions ($L_1 \le_m L_2$), Rice's Theorem, Post Correspondence Problem (PCP).

## 📂 Homework Directory Structure

| Directory | Contents | Focus Areas |
| :--- | :--- | :--- |
| [`HW-01/`](./HW-01) | `Problem-Set-01.pdf`, `Solution-01.pdf`, `Official-Solutions-01.pdf` | DFAs, NFAs, state minimization, $\epsilon$-closure, regular expressions. |
| [`HW-02/`](./HW-02) | `Problem-Set-02.pdf`, `Solution-02.pdf`, `Official-Solutions-02.pdf` | Pumping lemma for regular languages, CFGs, pushdown automata. |
| [`HW-03/`](./HW-03) | `Problem-Set-03.pdf`, `Solution-03.pdf`, `Official-Solutions-03.pdf` | Pumping lemma for CFLs, Turing machines, decidability, halting problem. |
| [`Bonus/`](./Bonus) | `Bonus-Problems.pdf`, `Bonus-Solution.pdf` | Advanced computability, Rice's theorem proofs, mapping reductions. |
