package org.example.metrics;

public class Metrics {
    private long comparisons;
    private int maxDepth;
    private long startTimeNs;
    private long totalTimeNs;

    public void addComparison() {
        comparisons++;
    }

    public void updateDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void startTimer() {
        startTimeNs = System.nanoTime();
    }

    public void stopTimer() {
        totalTimeNs = System.nanoTime() - startTimeNs;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getTotalTimeNs() {
        return totalTimeNs;
    }

    public double getTotalTimeMs() {
        return totalTimeNs / 1_000_000.0;
    }
}
