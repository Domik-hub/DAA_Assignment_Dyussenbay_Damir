import org.example.algorithms.Mergesort;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergesortTest {
    @Test
    void sortsAtLeast100RandomArraysLikeArraysSort() {
        Random random = new Random(12345);

        for (int test = 0; test < 100; test++) {
            int n = random.nextInt(250);
            int[] actual = new int[n];
            for (int i = 0; i < n; i++) {
                actual[i] = random.nextInt(101) - 50;
            }

            int[] expected = actual.clone();
            Arrays.sort(expected);

            Mergesort.sort(actual, new Metrics());
            assertArrayEquals(expected, actual, "Failed on random test " + test);
        }
    }

    @Test
    void handlesEdgeCases() {
        int[] empty = {};
        Mergesort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] one = {7};
        Mergesort.sort(one, new Metrics());
        assertArrayEquals(new int[]{7}, one);

        int[] equal = {5, 5, 5, 5, 5};
        Mergesort.sort(equal, new Metrics());
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, equal);

        int[] sorted = {1, 2, 3, 4, 5};
        Mergesort.sort(sorted, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void usesBoundedRecursionDepth() {
        int[] a = new int[100_000];
        Random random = new Random(99);
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt();
        }

        Metrics metrics = new Metrics();
        Mergesort.sort(a, metrics);

        assertTrue(metrics.getMaxDepth() <= 20,
                "Unexpected MergeSort depth: " + metrics.getMaxDepth());
    }
}
