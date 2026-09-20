package org.example.algorithms;

import org.example.metrics.Metrics;

public final class Mergesort {
    private static final int CUTOFF = 15;

    private Mergesort() {
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (metrics == null) {
            throw new IllegalArgumentException("Metrics must not be null");
        }

        metrics.startTimer();
        if (a.length > 0) {
            int[] helper = new int[a.length];
            sort(a, helper, 0, a.length - 1, 1, metrics);
        }
        metrics.stopTimer();
    }

    private static void sort(int[] a, int[] helper, int low, int high,
                             int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        int length = high - low + 1;
        if (length <= CUTOFF) {
            insertionSort(a, low, high, metrics);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, helper, low, mid, depth + 1, metrics);
        sort(a, helper, mid + 1, high, depth + 1, metrics);

        // Already sorted: avoid an unnecessary merge.
        metrics.addComparison();
        if (a[mid] <= a[mid + 1]) {
            return;
        }

        merge(a, helper, low, mid, high, metrics);
    }

    private static void merge(int[] a, int[] helper, int low, int mid,
                              int high, Metrics metrics) {
        for (int k = low; k <= high; k++) {
            helper[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = helper[j++];
            } else if (j > high) {
                a[k] = helper[i++];
            } else {
                metrics.addComparison();
                if (helper[j] < helper[i]) {
                    a[k] = helper[j++];
                } else {
                    a[k] = helper[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high,
                                      Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= low) {
                metrics.addComparison();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}
