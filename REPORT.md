# Assignment 1 — Divide and Conquer & Asymptotic Notations

## 1. Introduction
This report presents the experimental analysis of the Divide-and-Conquer algorithms **MergeSort**, **QuickSort**, and **QuickSelect** based on the measurements from `results.csv`.

* **Input Sizes ($n$):** 1,000; 10,000; 100,000; 1,000,000.
* **Input Types:** duplicates, random, sorted.
* **Metrics:** Median execution time (ms), total comparisons, and maximum recursion depth (36 benchmark runs).

---

## 2. Asymptotic Bounds

| Algorithm | Best case | Average case | Worst case |
| :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ |
| **QuickSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $O(n^2)$ |
| **QuickSelect** | $\Theta(n)$ | $\Theta(n)$ | $O(n^2)$ |
| **Insertion Sort** | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ |

* **MergeSort:** Array halving and linear merging yield $\Theta(n \log n)$ in all cases.
* **QuickSort:** Random pivot selection yields an expected time of $O(n \log n)$. Poor pivot choices can lead to the $O(n^2)$ worst case.
* **QuickSelect:** Recursing into only one partition side reduces average runtime to $\Theta(n)$.
* **Insertion Sort:** Linear for sorted arrays, quadratic for average and worst cases.

---

## 3. Recurrences & Master Theorem

* **MergeSort:** $T(n) = 2T(n/2) + \Theta(n)$  
  $a=2, b=2, f(n)=\Theta(n) \implies$ **Master Theorem Case 2:** $T(n) = \Theta(n \log n)$.

* **QuickSort (balanced split):** $T(n) = 2T(n/2) + \Theta(n)$  
  $a=2, b=2, f(n)=\Theta(n) \implies$ **Master Theorem Case 2:** $T(n) = \Theta(n \log n)$.  
  *Optimization:* Recursing into the smaller partition first bounds stack depth.

* **QuickSelect (balanced split):** $T(n) = T(n/2) + \Theta(n)$  
  $a=1, b=2, f(n)=\Theta(n) \implies$ **Master Theorem Case 3:** $T(n) = \Theta(n)$.

---

## 4. Benchmark Methods & Plots

1. **Execution Time (`plots/time_vs_n.png`):** Measured on a log-log scale, following predicted asymptotic bounds.
2. **Recursion Depth (`plots/depth_vs_n.png`):** MergeSort maintains strict logarithmic depth. QuickSort’s smaller-partition strategy prevents linear stack growth on sorted inputs.
3. **Empirical $\Theta$ Check (`plots/ratio_vs_n.png`):**
    * For MergeSort/QuickSort: $\frac{\text{comparisons}}{n \log_2 n}$
    * For QuickSelect: $\frac{\text{comparisons}}{n}$  
      For $n \ge 100,000$, ratios stabilize within $[0.0502, 5.4428]$, confirming $c_1 \cdot g(n) \le f(n) \le c_2 \cdot g(n)$.

---

## 5. Discussion & Conclusion

Experimental results align with theoretical bounds. Minor ratio variations stem from JVM warm-up, GC overhead, CPU cache effects, insertion-sort cutoffs, and random pivot selection.

The data confirms $\Theta(n \log n)$ growth for MergeSort and QuickSort, and $\Theta(n)$ growth for QuickSelect.

---

## 6. Files
* `results.csv` — Raw benchmark data.
* `plots/time_vs_n.png` — Execution time plot.
* `plots/depth_vs_n.png` — Maximum depth plot.
* `plots/ratio_vs_n.png` — Empirical $\Theta$ ratio plot.