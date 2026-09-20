package org.example.algorithms;

import org.example.metrics.Metrics;

import java.util.concurrent.ThreadLocalRandom;

public final class Quicksort {
    private Quicksort() {
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (metrics == null) {
            throw new IllegalArgumentException("Metrics must not be null");
        }

        metrics.startTimer();
        if (a.length > 1) {
            sort(a, 0, a.length - 1, 1, metrics);
        }
        metrics.stopTimer();
    }

    private static void sort(int[] a, int low, int high, int depth,
                             Metrics metrics) {
        while (low < high) {
            metrics.updateDepth(depth);

            int[] p = partition3Way(a, low, high, metrics);
            int lt = p[0];
            int gt = p[1];

            // Recurse only into the smaller side.
            // Continue with the larger side using the loop.
            if (lt - low < high - gt) {
                sort(a, low, lt - 1, depth + 1, metrics);
                low = gt + 1;
            } else {
                sort(a, gt + 1, high, depth + 1, metrics);
                high = lt - 1;
            }
        }
    }

    public static int[] partition3Way(int[] a, int low, int high,
                                      Metrics metrics) {
        int pivotIndex = ThreadLocalRandom.current().nextInt(low, high + 1);
        swap(a, low, pivotIndex);
        int pivot = a[low];

        int lt = low;
        int i = low + 1;
        int gt = high;

        while (i <= gt) {
            metrics.addComparison();
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else {
                metrics.addComparison();
                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }
        }

        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
