# ⚡ Assignment 3 (HW3): Modern Java & Functional Programming
> **Course:** [HY-252: Object-Oriented Programming](https://elearn.uoc.gr/course/view.php?id=5974) — University of Crete (UoC CSD)  
> **Author / Student:** Rafail Ioannis Petrakis (`csd5748`)  
> **Specification:** [`HW3.pdf`](./HW3.pdf)  
> **Language Features:** Java 8+ / Modern Java Functional Programming

---

## 📑 Overview

Assignment 3 is an advanced bonus assignment exploring modern Java paradigms: functional interfaces, Lambda expressions, constructor method references, default & static interface methods, concurrent thread execution via lambdas, and complex data querying pipelines powered by the Java Stream API and Collectors.

All modular exercises are implemented within [`src/Ask3.java`](./src/Ask3.java).

---

## 📂 Project Structure

```text
HW3/
├── src/
│   ├── Ask3.java            # Comprehensive Suite of Java 8+ Functional Modules (1–4)
│   └── Examination/
│       └── EA3a.java        # Laboratory practical exam solution (Streams & Lambdas)
├── HW3.pdf                  # Official assignment problem sheet
├── HW3.iml                  # IntelliJ module definition
└── README.md                # Dedicated Assignment 3 Documentation
```

---

## 🔍 Modular Breakdown & Concepts

### 1. Module 1: Interfaces, Default Methods & Lambda Ambiguity
* **Default & Static Interface Methods**: Implements `SmartDevice` and `NetworkComponent` interfaces. Resolves diamond conflict ambiguity in default method overrides using `NetworkComponent.super.reset()`.
* **Static Interface Utilities**: Tests static methods in interfaces (`SmartDevice.isDeviceOnline(...)`), verifying that static interface methods are not inherited by implementing class instances.
* **Target-Type Ambiguity Resolution**: Resolves overloaded method calls with functional interfaces (`Predicate<Long>` vs. `LongPredicate`) by supplying explicit lambda type casts.

### 2. Module 2: Lambdas, Concurrency & Stream Math
* **Event-Driven Lambdas & Concurrency**: Spawns concurrent daemon threads within Swing `ActionListener` lambda callbacks.
* **Stream Summary Statistics**: Aggregates statistical metrics over computed floating-point streams using `DoubleSummaryStatistics` (`getMin()`, `getMax()`, `getAverage()`) and custom reductions (`.reduce(1.0, (a, b) -> a * b)`).
* **Higher-Order Predicate Utilities**: Implements generic filtering utility `calculate(List<Integer> list, Predicate<Integer> pred)` tested against various numerical predicates.

### 3. Module 3: Stream Pipelines & Movie Dataset Queries
* **Factory Pattern with Constructor Reference**: Generates `Movie` records using `MovieFactory<P>` bound to `Movie::new`.
* **Data Processing Pipelines**:
  * **Filtering**: Queries movies by director (`Stanley Kubrick`, `Francis Coppola`, `Denis Villeneuve`).
  * **Multi-Key Sorting**: Sorts dataset by rating and year (both ascending and descending using `Comparator.comparingInt(...).reversed()`).
  * **Extrema & Limits**: Locates the top-rated movie (`.max()`) and extracts top 3 rated titles (`.limit(3)`).
  * **Downstream Collectors**: Groups movies by year using `Collectors.groupingBy()` coupled with downstream collectors:
    * `Collectors.counting()`: Counts releases per year.
    * `Collectors.averagingDouble()`: Computes average ratings per year.

### 4. Module 4: Laptop Inventory Query Engine
* **Factory Pattern**: Models laptop inventory via `LaptopFactory<P>` bound to `Laptop::new`.
* **Stream Queries**:
  * **Multi-Criteria Filter**: Filters models meeting compound constraints (RAM $\ge$ 16GB AND Price $\le$ 1500€).
  * **Deduplication**: Extracts unique brands using `.map(Laptop::getBrand).distinct()`.
  * **Price Sorting**: Sorts devices in ascending order of price.
  * **Existential Quantification**: Evaluates inventory conditions using `.anyMatch(l -> l.getPrice() < 400)`.
  * **String Joining**: Aggregates all model names into a single formatted string via `Collectors.joining(", ")`.

---

## 🚀 How to Compile & Run

From the `HY-252/HW3/src` directory:

```bash
# Navigate to the source directory
cd HY-252/HW3/src

# Compile the suite
javac Ask3.java

# Execute all modules
java Ask3
```
