package org.example.algorithms;

import org.example.metrics.Metrics;

public final class Quickselect {
    private Quickselect() {
    }

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k must be between 0 and " + (a.length - 1));
        }
        if (metrics == null) {
            throw new IllegalArgumentException("Metrics must not be null");
        }

        metrics.startTimer();
        int result = selectRange(a, k, 0, a.length - 1, 1, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int selectRange(int[] a, int k, int low, int high,
                                   int depth, Metrics metrics) {
        while (true) {
            metrics.updateDepth(depth);

            if (low == high) {
                return a[low];
            }

            int[] p = Quicksort.partition3Way(a, low, high, metrics);
            int lt = p[0];
            int gt = p[1];

            if (k < lt) {
                high = lt - 1;
            } else if (k > gt) {
                low = gt + 1;
            } else {
                return a[k];
            }

            depth++;
        }
    }
}
