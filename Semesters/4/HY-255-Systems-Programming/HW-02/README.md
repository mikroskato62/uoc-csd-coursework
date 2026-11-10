# [UoC](https://www.uoc.gr/) - [CSD](https://www.csd.uoc.gr/) - [HY-255 Software Lab](https://www.csd.uoc.gr/~hy255/): [Assignment 2](https://www.csd.uoc.gr/~hy255/as2/) - String Module

---

## Author: **Petrakis Rafail Ioannis (csd5748)**

---

### Theoretical Questions

#### 1. Destination Memory Check
- **Question:** Is it possible for `ms_copy`, `ms_ncopy`, `ms_concat`, and `ms_nconcat` to call `assert` to check if the destination memory where the result is written is large enough? Explain.
- **Answer:** No, it is not possible. In C, when an array is passed as an argument to a function, it decays into a pointer to its first element. Consequently, the function only receives the memory address of the start of the buffer and loses all information regarding the total allocated size of that memory block. Because the function does not know the capacity of the `dest` array, `assert` cannot be used to verify if it is large enough to hold the incoming data.

#### 2. Negative Length Check
- **Question:** Is it possible for `ms_ncopy`, `ms_nconcat`, and `ms_ncompare` to call `assert` to check if the `length` parameter has a negative value? Explain.
- **Answer:** No, it is not possible. The `length` parameter is declared with the `size_t` data type, which is an *unsigned* integer type. By definition, unsigned types cannot hold negative numbers. If a negative value (e.g., `-1`) is passed to the function, it will implicitly wrap around (underflow) to a very large positive number (specifically, `SIZE_MAX`). Therefore, an assertion like `assert(length >= 0)` will always evaluate to true and cannot detect a logically "negative" input.

---

### Compilation & Testing

```bash
# Test array-based implementation:
gcc -Wall -ansi -pedantic src/main.c src/mystring_ars.c -o array_test
./array_test

# Test pointer-based implementation:
gcc -Wall -ansi -pedantic src/main.c src/mystring_ptrs.c -o pointers_test
./pointers_test
```

---

### Directory Structure

```text
HW-02/
├── Makefile
├── README.md
└── src/
    ├── main.c
    ├── mystring.h
    ├── mystring_ars.c
    └── mystring_ptrs.c
```
