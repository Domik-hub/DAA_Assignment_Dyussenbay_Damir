package org.example.benchmark;

import org.example.algorithms.Mergesort;
import org.example.algorithms.Quickselect;
import org.example.algorithms.Quicksort;
import org.example.metrics.Metrics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public final class Benchmark {
    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;
    private static final long RANDOM_SEED = 20260920L;

    private Benchmark() {
    }

    public static void main(String[] args) {
        String output = args.length > 0 ? args[0] : "results.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth");
            writer.newLine();

            runMergeSort(writer);
            runQuickSort(writer);
            runQuickSelect(writer);

            System.out.println("Benchmark successfully written to " + output);
        } catch (IOException e) {
            System.err.println("Could not write benchmark results: " + e.getMessage());
        }
    }

    private static void runMergeSort(BufferedWriter writer) throws IOException {
        for (int n : SIZES) {
            for (String type : TYPES) {
                BenchmarkResult result = benchmark(n, type, "MergeSort");
                writeResult(writer, result);
            }
        }
    }

    private static void runQuickSort(BufferedWriter writer) throws IOException {
        for (int n : SIZES) {
            for (String type : TYPES) {
                BenchmarkResult result = benchmark(n, type, "QuickSort");
                writeResult(writer, result);
            }
        }
    }

    private static void runQuickSelect(BufferedWriter writer) throws IOException {
        for (int n : SIZES) {
            for (String type : TYPES) {
                BenchmarkResult result = benchmark(n, type, "QuickSelect");
                writeResult(writer, result);
            }
        }
    }

    private static BenchmarkResult benchmark(int n, String type, String algorithm) {
        double[] timesMs = new double[RUNS];
        long[] comparisons = new long[RUNS];
        int[] depths = new int[RUNS];

        // Same deterministic sequence for reproducible benchmark input generation.
        Random random = new Random(RANDOM_SEED + n * 31L + type.hashCode());

        // Small warm-up before measuring each case.
        for (int warmup = 0; warmup < 2; warmup++) {
            int[] warmupArray = generateArray(Math.min(n, 10_000), type, random);
            Metrics metrics = new Metrics();
            runAlgorithm(algorithm, warmupArray, metrics);
        }

        for (int run = 0; run < RUNS; run++) {
            int[] array = generateArray(n, type, random);
            Metrics metrics = new Metrics();

            runAlgorithm(algorithm, array, metrics);

            timesMs[run] = metrics.getTotalTimeMs();
            comparisons[run] = metrics.getComparisons();
            depths[run] = metrics.getMaxDepth();
        }

        Arrays.sort(timesMs);
        Arrays.sort(comparisons);
        Arrays.sort(depths);

        return new BenchmarkResult(
                algorithm,
                type,
                n,
                timesMs[RUNS / 2],
                comparisons[RUNS / 2],
                depths[RUNS / 2]
        );
    }

    private static void runAlgorithm(String algorithm, int[] array, Metrics metrics) {
        switch (algorithm) {
            case "MergeSort" -> Mergesort.sort(array, metrics);
            case "QuickSort" -> Quicksort.sort(array, metrics);
            case "QuickSelect" -> Quickselect.select(array, array.length / 2, metrics);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private static int[] generateArray(int n, String type, Random random) {
        int[] array = new int[n];

        switch (type) {
            case "random" -> {
                for (int i = 0; i < n; i++) {
                    array[i] = random.nextInt();
                }
            }
            case "sorted" -> {
                for (int i = 0; i < n; i++) {
                    array[i] = i;
                }
            }
            case "duplicates" -> {
                for (int i = 0; i < n; i++) {
                    array[i] = random.nextInt(10);
                }
            }
            default -> throw new IllegalArgumentException("Unknown input type: " + type);
        }

        return array;
    }

    private static void writeResult(BufferedWriter writer, BenchmarkResult result)
            throws IOException {
        writer.write(String.format(
                Locale.US,
                "%s,%s,%d,%.3f,%d,%d",
                result.algorithm,
                result.inputType,
                result.n,
                result.timeMs,
                result.comparisons,
                result.maxDepth
        ));
        writer.newLine();
    }

    private record BenchmarkResult(
            String algorithm,
            String inputType,
            int n,
            double timeMs,
            long comparisons,
            int maxDepth
    ) {
    }
}
