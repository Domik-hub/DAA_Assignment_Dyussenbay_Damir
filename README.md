# DAA Assignment 1 - Divide and Conquer

Java implementation of MergeSort, QuickSort and QuickSelect with metrics and JUnit 5 tests.

## Requirements

- Java 21 or newer
- Maven 3.9+

## Build

```bash
mvn clean test
```

## Run tests

```bash
mvn test
```

## Run benchmark

```bash
mvn -q -DskipTests package
java -cp target/classes org.example.Main
```

The benchmark creates `results.csv` in the project directory.

The CSV columns are:

```text
algorithm,input,n,time_ms,comparisons,max_depth
```

The benchmark uses:

- n = 1,000; 10,000; 100,000; 1,000,000
- random, sorted and duplicates inputs
- 5 measured runs per case
- median measured time

QuickSelect uses `k = n / 2` (the median position).

## Algorithms

### MergeSort

- one reusable helper array
- insertion-sort cutoff for subarrays of 15 elements or fewer
- linear merge

### QuickSort

- random pivot
- 3-way partition
- recurse into the smaller side
- process the larger side using a loop

### QuickSelect

- reuses the same 3-way partition as QuickSort
- continues only in the side containing k
- zero-based k
- invalid input throws `IllegalArgumentException`
