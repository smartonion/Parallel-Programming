# Parallel Programming Implementations

This repository contains my individual implementations of various projects and assignments from the **CSE 231S: Introduction to Parallel and Concurrent Programming** course at Washington University in St. Louis. Although these projects follow the general framework and learning outcomes provided by Prof. Dennis Cosgrove, the majority of the code in this repository is my own work, reflecting my deep dive into parallel and concurrent programming techniques in Java.

## Overview

- **Goal:** Demonstrate parallel solutions to classical data structures, algorithms, and computing paradigms.
- **Language:** Java (with the built-in concurrency libraries).
- **Focus Areas:** 
  - Fork/Join frameworks
  - Race conditions & thread safety
  - Locking strategies (intrinsic, explicit, read/write, deadlock avoidance)
  - Atomics & lock-free data structures
  - MapReduce and big-data style operations (to be included)
  - Phaser-based coordination (to be included)
  - Executor services & work stealing (to be included)

These implementations were structured around class assignments but go beyond the bare requirements to illustrate additional insights and optimizations whenever possible.

## Key Implementations

A few highlights from the repository include:

- **ForkJoin Implementations:**
  - **Fork Loops** for parallel iteration over arrays and 2D matrices.
  - **N-Queens** parallel solver using divide-and-conquer approaches.

- **MapReduce:**
  - **Framework** that includes the `Mapper`, `AccumulatorCombinerReducer`, and the final reduce steps.
  - **Applications** like word counting, matrix multiplication, and data analytics on real-world scenarios (e.g., cholera outbreak data, Twitter data).

- **Concurrent Data Structures:**
  - **Concurrent Stack** using locks and atomic operations.
  - **Concurrent Hash Table** with read/write locks and specialized concurrency controls.
  
- **Phaser-Based Algorithms:**
  - **Iterative Averaging** and **Legged Races** showcasing advanced synchronization patterns where tasks proceed in multiple phases.

## Usage

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/smartonion/Parallel-Programming.git
   cd parallel-programming-implementations
   ```
2. **Open in Your IDE:**  
   Import the project into IntelliJ, Eclipse, or your preferred Java IDE.


3. **Explore Code and Modules:**  
   Each folder corresponds to a particular assignment or demonstration of a topic. Inside, you’ll find:
   - **`src/main/java`** with the main logic
   - **`src/test/java`** with unit tests and example usage

## Project Structure

Below is a simplified outline:

```
.
├── ForkJoin
│   ├── ForkLoop.java
│   ├── ...
│   └── ParallelNQueens.java
├── MapReduce
│   ├── Mapper.java
│   ├── AccumulatorCombinerReducer.java
│   ├── Framework.java
│   └── ...
├── Concurrency
│   ├── ConcurrentStack.java
│   ├── ConcurrentHashTable.java
│   └── AtomicStack.java
├── PhaserExamples
│   ├── IterativeAveraging.java
│   └── LeggedRaces.java
└── ...
```

Most directories are self-contained and include a `README.md` or comments clarifying the assignment and the approach taken.

## Future Improvements

- **Enhanced Benchmarking:** Integrate a robust benchmarking suite (e.g., JMH) to measure performance improvements from parallelization and concurrency optimizations.
- **Additional Data Structures:** Extend lock-free techniques to more data structures (queues, trees, etc.).
- **Extended MapReduce:** Explore distributed approaches (e.g., adapting these frameworks for Hadoop or Spark-like environments).

## Acknowledgments

- **Professor Dennis Cosgrove** for laying the foundational framework, course materials, and inspiration behind these projects. The structure and concepts originate from his Parallel Programming course at Washington University in St. Louis.
- **Teaching Assistants and Peers** for feedback and lively discussions on Piazza and in office hours.


Feel free to reach out or open an issue if you have any questions or suggestions for improvements!
