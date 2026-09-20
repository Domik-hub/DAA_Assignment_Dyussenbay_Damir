import org.example.algorithms.Quickselect;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickselectTest {
    @Test
    void matchesSortedResultOnAtLeast100RandomArrays() {
        Random random = new Random(777);

        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(250);
            int[] actual = new int[n];
            for (int i = 0; i < n; i++) {
                actual[i] = random.nextInt(101) - 50;
            }

            int[] sorted = actual.clone();
            Arrays.sort(sorted);
            int k = random.nextInt(n);

            int result = Quickselect.select(actual, k, new Metrics());
            assertEquals(sorted[k], result, "Failed on random test " + test);
        }
    }

    @Test
    void handlesAllEqualAndBoundaryPositions() {
        int[] equal = new int[1000];
        Arrays.fill(equal, 9);

        assertEquals(9, Quickselect.select(equal.clone(), 0, new Metrics()));
        assertEquals(9, Quickselect.select(equal.clone(), 500, new Metrics()));
        assertEquals(9, Quickselect.select(equal.clone(), 999, new Metrics()));
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> Quickselect.select(new int[]{}, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> Quickselect.select(new int[]{1, 2, 3}, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> Quickselect.select(new int[]{1, 2, 3}, 3, new Metrics()));
    }
}
