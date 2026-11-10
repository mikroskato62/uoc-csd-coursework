# [UoC](https://www.uoc.gr/) - [CSD](https://www.csd.uoc.gr/) - [HY-255 Software Lab](https://www.csd.uoc.gr/~hy255/): [Assignment 3](https://www.csd.uoc.gr/~hy255/as3/) - ADTs (Symbol Table)

---

## Author: **Petrakis Rafail Ioannis (csd5748)**

---

### Overview

Assignment 3 focuses on implementing an Abstract Data Type (ADT) for a **Symbol Table** (`SymTable`) in ANSI C with two separate underlying data structure implementations:
1. **Linked List Implementation** (`symtablelist.c`)
2. **Expanding Hash Table Implementation** (`symtablehash.c`)

---

### Compilation Target (Makefile)

```bash
# Clean previous build artifacts
make clean

# Build and run linked list implementation
make list
./testsymtab

# Clean and build hash table implementation
make clean
make hash
./testsymtab
```

---

### Directory Contents

```text
HW-03/
├── Makefile
├── README.md
└── assignment.txt      # Official assignment specification
```
