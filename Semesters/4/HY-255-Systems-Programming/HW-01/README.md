# [UoC](https://www.uoc.gr/) - [CSD](https://www.csd.uoc.gr/) - [HY-255 Software Lab](https://www.csd.uoc.gr/~hy255/): [Assignment 1](https://www.csd.uoc.gr/~hy255/as1/) - Translate Program

---

## Author: **Petrakis Rafail Ioannis (csd5748)**

---

### Compilation & Execution

#### Option 1: Using Makefile & MyTest (recommended - verifies diff is clean)
```bash
make translate
iconv -f UTF-8 -t ISO-8859-7 tests/grin.txt | ./translate > tests/enout.txt
diff tests/enout.txt tests/test3
```

#### Option 2: Using Makefile & TestFile
```bash
make translate
iconv -f UTF-8 -t ISO-8859-7 tests/grin.txt > tests/test3.7
cp tests/test3 tests/test3.1
rm tests/enout.txt
./translate < tests/test3.7 > tests/enout.txt
diff tests/enout.txt tests/test3.1
```

#### Option 3: Direct Compilation
```bash
gcc -Wall -ansi -pedantic -g src/main.c -o translate
iconv -f UTF-8 -t ISO-8859-7 tests/grin.txt > tests/test3.7
./translate < tests/test3.7
```

*Note:* `test3` is a comprehensive test that includes cases from `test1` and `test2`.

---

### Directory Structure

```text
HW-01/
├── Makefile
├── README.md
├── src/
│   └── main.c
└── tests/
    ├── grin.txt
    ├── test1.1
    ├── test1.7
    ├── test2.1
    ├── test2.7
    └── test3
```
