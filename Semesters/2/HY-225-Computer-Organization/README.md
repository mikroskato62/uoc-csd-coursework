# HY-225: Computer Organization

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-225 — Computer Organization (Οργάνωση Υπολογιστών)  
> **Semester:** Spring (Semester 2)  
> **Category:** Core Hardware & Architecture  
> **Technologies:** MIPS Assembly (`.asm`), SPIM/MARS Simulators, Processor Pipeline Traces  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-225 examines the interface between software and hardware. It covers instruction set architecture (ISA), processor datapath and control unit design, single-cycle and pipelined processor implementations, pipeline hazards (data, structural, control), caching hierarchies, and virtual memory.

## 📂 Laboratory & Practical Exercises

The laboratory exercises span from low-level assembly routines to processor pipeline execution analysis:

| Directory | Focus & Deliverables | Key Concepts |
| :--- | :--- | :--- |
| [`Exercise-01/`](./Exercise-01) | Baseline Environment Setup | SPIM/MARS simulator configuration and instruction format orientation. |
| [`Exercise-02/`](./Exercise-02) | `ex02.asm` | Basic MIPS instructions, arithmetic, load/store (`lw`, `sw`), register conventions. |
| [`Exercise-03/`](./Exercise-03) | `ex03_4.asm`, `ex03_5.asm` | Branching, conditional execution, array looping, stack frames (`$sp`, `$ra`). |
| [`Exercise-04-05/`](./Exercise-04-05) | `ex045.pdf` | Processor datapath tracing, instruction decode, ALU control logic. |
| [`Exercise-06-07/`](./Exercise-06-07) | `ex07.asm` | Subroutine calls, nested recursion, memory layout, dynamic buffer management. |
| [`Exercise-08-09/`](./Exercise-08-09) | `ex089.pdf`, `Report-08-09.tex` | Single-cycle vs. multi-cycle datapath performance and critical path analysis. |
| [`Exercise-10-11/`](./Exercise-10-11) | `ex10.pdf`, `ex11.pdf`, `Trace-*.txt`, `Report-10-11.tex` | 5-stage RISC pipelining (IF, ID, EX, MEM, WB), data forwarding, stall cycles. |
| [`Exercise-12/`](./Exercise-12) | `ex12.pdf`, `Report-12.tex` | Branch hazard handling, delayed branch slots, dynamic branch prediction. |
| [`Exercise-13-14/`](./Exercise-13-14) | `ex13.pdf`, `ex14.pdf`, `Report-13.tex`, `Report-14.tex` | Memory hierarchies, cache mapping (direct, set-associative), hit/miss rates. |
