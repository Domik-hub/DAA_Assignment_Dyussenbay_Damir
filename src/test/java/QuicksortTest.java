import org.example.algorithms.Quicksort;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuicksortTest {
    @Test
    void sortsAtLeast100RandomArraysLikeArraysSort() {
        Random random = new Random(54321);

        for (int test = 0; test < 100; test++) {
            int n = random.nextInt(250);
            int[] actual = new int[n];
            for (int i = 0; i < n; i++) {
                actual[i] = random.nextInt(101) - 50;
            }

            int[] expected = actual.clone();
            Arrays.sort(expected);

            Quicksort.sort(actual, new Metrics());
            assertArrayEquals(expected, actual, "Failed on random test " + test);
        }
    }

    @Test
    void handlesEdgeCases() {
        int[] empty = {};
        Quicksort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] one = {7};
        Quicksort.sort(one, new Metrics());
        assertArrayEquals(new int[]{7}, one);

        int[] equal = new int[1_000];
        Arrays.fill(equal, 42);
        Metrics equalMetrics = new Metrics();
        Quicksort.sort(equal, equalMetrics);
        assertEquals(42, equal[999]);
        assertTrue(equalMetrics.getMaxDepth() <= 2);

        int[] sorted = new int[10_000];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = i;
        }
        Quicksort.sort(sorted, new Metrics());
        assertArrayEquals(IntStreamHelper.range(sorted.length), sorted);
    }

    @Test
    void sorted100000HasLogarithmicRecursionDepth() {
        int[] a = new int[100_000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        Metrics metrics = new Metrics();
        Quicksort.sort(a, metrics);

        double limit = 2.0 * (Math.log(a.length) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= limit,
                "maxDepth=" + metrics.getMaxDepth() + ", limit=" + limit);
        assertArrayEquals(IntStreamHelper.range(a.length), a);
    }

    private static final class IntStreamHelper {
        private static int[] range(int n) {
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                result[i] = i;
            }
            return result;
        }
    }
}
