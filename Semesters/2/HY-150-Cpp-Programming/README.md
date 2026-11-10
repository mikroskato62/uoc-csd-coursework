# HY-150: Programming in C++ (OOP & 3D Ray Tracing)

> **University of Crete (UoC) — Department of Computer Science (CSD)**  
> **Course:** HY-150 — Programming in C++ (Προγραμματισμός σε C++)  
> **Semester:** Spring (Semester 2)  
> **Language & Standards:** C++17, CMake, Netpbm PPM Graphics, FLTK GUI  
> **Status:** Completed / Passed

---

## 📖 Course Overview

HY-150 transitions from procedural C to modern Object-Oriented Programming (OOP) conforming to C++17 standards. The coursework culminates in building a full-featured **3D Ray Tracing Engine** utilizing vector algebra and computational geometry.

## 📂 Sub-Exercise Breakdown

### 🌟 [`HW-01/`](./HW-01) — Procedural 2D Image Synthesis & Vector Math
* **Objective**: Generate synthetic 2D image compositions rendered in the Netpbm portable pixmap format (`.ppm`).
* **Concepts**: Standard Library encapsulation, header-only modularity, vector geometry foundation (`vec3.h`), and RGB clamping (`color.h`).
* **Deliverables**: `Assignment1.cpp`, `Example.cpp`, `Problem-Set-01.pdf`, `Problem-Set-01-GR.pdf`, `CMakeLists.txt`.

### 🚀 [`HW-02/`](./HW-02) — Flagship 3D Ray Tracing Engine
* **Objective**: A complete ray-tracer capable of rendering complex 3D scenes with perspective cameras, diffuse shading, specular highlights, and geometric primitives.
* **Core Architecture**:
  * `ray.h`: Parametric ray representation $\mathbf{P}(t) = \mathbf{A} + t\mathbf{b}$.
  * `Camera.h` / `Camera.cpp`: Pinhole camera model with configurable field of view (FOV), eye position, look-at target, and focal distance.
  * `Triangle.h` / `Triangle.cpp`: 3D triangular mesh primitives implementing the **Möller–Trumbore intersection algorithm** for fast ray-triangle collision testing.
  * `World.h` / `World.cpp`: Scene graph parser reading geometry definitions from `world.txt`, computing lighting and reflections.
* **Build & Run**:
  ```bash
  cd HW-02
  cmake -B build
  cmake --build build
  ./build/assignment2
  ```

### 🎨 [`HW-03/`](./HW-03) — Graphical Visualization with FLTK
* **Objective**: GUI application utilizing the Fast Light Toolkit (FLTK) for rendering graphical shapes, geometric coordinate transformations, and data plotting.
* **Deliverable**: `csd5748-hy150-assignment3-CPP.PP.Edu.main-chapter.12.3.cpp`, `Problem-Set-03.pdf`, `Problem-Set-03-GR.pdf`.

### 📁 [`Other/`](./Other)
* `HelloWorld/`: CMake project setup baseline and educational templates.
* `HY-150.ipynb`: Jupyter notebook notes and language exploration.
