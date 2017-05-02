import com.google.common.io.Resources;
import edu.princeton.cs.algs4.In;

import java.net.URL;

public class TestUtils {

    private TestUtils() {
    }

    static int[][] cellsAllInPlace(final int n) {

        final int[][] cells = new int[n][n];

        for (int value = 1; value <= n * n - 1; value++) {
            cells[(value - 1) / n][(value - 1) % n] = value;
        }

        if (n > 0)
            cells[n - 1][n - 1] = 0;

        return cells;
    }

    static Board boardFromFile(final String name) {

        final URL resource = Resources.getResource("puzzles" + "/" + name);


        final In in = new In(resource);
        final int n = in.readInt();
        final int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        return new Board(tiles);
    }

    static int[][] permutation(final int n) {

        final int[] ints = new int[n * n];

        for (int i = 0; i < n * n; i++)
            ints[i] = i;

        // shuffle
        for (int i = 0; i < n * n; i++) {
            final int r = (int) (Math.random() * (i + 1));
            final int swap = ints[r];
            ints[r] = ints[i];
            ints[i] = swap;
        }

        final int[][] ints2d = new int[n][n];

        for (int i = 0; i < ints2d.length; i++) {
            for (int j = 0; j < ints2d[i].length; j++) {
                ints2d[i][j] = ints[i * n + j];
            }
        }

        return ints2d;
    }
}