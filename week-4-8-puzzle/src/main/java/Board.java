import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {

    private final char[] tiles;
    private final int n;
    private int manhattan;
    private int hamming;

    /**
     * Construct a board from an n-by-n array of tiles (where tiles[i][j] = tile in row i, col j)
     *
     * @param tiles the tiles.
     */
    public Board(final int[][] tiles) {

        assert (tiles.length == 0 || tiles[0].length == tiles.length);

        this.n = tiles.length;

        this.tiles = new char[n * n];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                set(i, j, tiles[i - 1][j - 1]);
            }
        }

        this.manhattan = sumManhattanDistances();
        this.hamming = sumHamming();
    }

    /**
     * Constructs a neighbor. Starting with the given board and swapping the tiles at the given positions.
     *
     * @param board the
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */
    private Board(final Board board, final int row1, final int col1, final int row2, final int col2) {
        this.n = board.dimension();

        this.tiles = Arrays.copyOf(board.tiles, n * n);

        this.manhattan = board.manhattan();

        swap(row1, col1, row2, col2);
    }

    /**
     * board dimension n
     *
     * @return the dimension
     */
    public int dimension() {
        return n;
    }

    /**
     * Number of tiles out of place
     *
     * @return the number of out-of-place tiles.
     */
    public int hamming() {
        return hamming;
    }

    private int sumHamming() {
        int wrong = 0;

        for (int value = 1; value <= n * n; value++) {

            final int tile = tile(value);

            if (tile != value && tile != 0) {
                wrong++;
            }
        }

        return wrong;
    }

    /**
     * The sum of Manhattan distances between tiles and goal
     *
     * @return sum of manhattan distances
     */
    public int manhattan() {
        return manhattan;
    }

    private int sumManhattanDistances() {
        int distance = 0;

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {

                final int actual = tile(row, col);

                if (!space(row, col))
                    distance += manhattan(row, col, row(actual), col(actual));
            }
        }

        return distance;
    }

    /**
     * Is this board the goal board?
     *
     * @return true iff board is goal
     */
    public boolean isGoal() {
        return manhattan == 0;
    }

    /**
     * Return a board that is obtained by exchanging any pair of tiles
     *
     * @return a twin board.
     */
    public Board twin() {
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col < n; col++) {
                if (!space(row, col) && !space(row, col + 1)) {
                    return new Board(this, row, col, row, col + 1);
                }
            }
        }

        if (n <= 1) {
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Does this board equal other?
     *
     * @param obj other board
     * @return true iff other equals this
     */
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;


        final Board board = (Board) obj;

        if (n != board.n)
            return false;

        for (int pos = 1; pos < n * n; pos++) {
            if (tile(pos) != board.tile(pos)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return all neighboring boards.
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {

        if (n == 0) {
            return Collections.emptyList();
        }

        final int i = positionOfEmpty();

        final int row = row(i);
        final int col = col(i);

        final ArrayList<Board> neighbors = new ArrayList<>();

        for (int vertical = -1; vertical <= 1; vertical++) {
            for (int horizontal = -1; horizontal <= 1; horizontal++) {

                if (vertical != horizontal) {

                    final int row1 = row + vertical;
                    final int col1 = col + horizontal;

                    if (inside(row1, col1) && (row == row1 || col == col1)) {
                        neighbors.add(new Board(this, row, col, row1, col1));
                    }
                }
            }
        }

        return neighbors;
    }

    /**
     * string representation of this board.
     *
     * @return string representation.
     */
    public String toString() {

        final StringBuilder s = new StringBuilder();

        s.append(n).append("\n");

        for (int row = 1; row <= n; row++) {

            for (int column = 1; column <= n; column++) {
                s.append(String.format("%2d ", tile(row, column)));
            }

            s.append("\n");
        }
        return s.toString();
    }


    private int to1d(final int row, final int col) {
        return dimension() * (row - 1) + (col - 1);
    }

    /**
     * Get tile value of (row, col). The first row is 1.
     * The first col is 1
     *
     * @param row the row
     * @param col the col
     * @return the tile
     */
    private int tile(final int row, final int col) {
        assert row > 0 && col > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row, col);
        return tiles[to1d(row, col)];
    }

    /**
     * Returns tile value at given position treating the 2d array as an 1d array
     * starting at 1.
     *
     * @param position position, pos, where 1 <= pos <= n * n
     * @return the tile value at position
     */
    private int tile(final int position) {
        assert position > 0 : String.format("Positions start at 1: was %d", position);

        return tiles[position - 1];
    }

    /**
     * Returns the manhattan distance between the two points.
     * <p>
     * The rows & columns starts at 1
     *
     * @param row1 row of first point
     * @param col1 col of first point
     * @param row2 row of second point
     * @param col2 col of second point.
     * @return the manhattan distance
     */
    private int manhattan(final int row1, final int col1, final int row2, final int col2) {
        assert row1 > 0 && col1 > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row1, col1);
        assert row2 > 0 && col2 > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row2, col2);

        return Math.abs(row2 - row1) + Math.abs(col2 - col1);
    }

    /**
     * Returns the manhattan distance from (row, col) to the goal position of value
     *
     * @param row The row
     * @param col The column
     * @return The distance
     */
    private int manhattan(final int row, final int col, final int value) {
        return value != 0 ? manhattan(row, col, row(value), col(value)) : 0;
    }

    /**
     * Swap the values of the the two tiles.
     *
     * @param row1 row tile 1
     * @param col1 col tile 1
     * @param row2 row tile 2
     * @param col2 col tile 2
     */
    private void swap(final int row1, final int col1, final int row2, final int col2) {
        assert row1 > 0 && col1 > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row1, col1);
        assert row2 > 0 && col2 > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row2, col2);

        final int row1col1 = tile(row1, col1);
        final int row2col2 = tile(row2, col2);

        set(row1, col1, tile(row2, col2));
        set(row2, col2, row1col1);

        final int i =
                (manhattan(row1, col1, row2col2) - manhattan(row1, col1, row1col1)) +
                        (manhattan(row2, col2, row1col1) - manhattan(row2, col2, row2col2));


        this.hamming = sumHamming();

        this.manhattan += i;
    }

    private boolean space(final int row, final int col) {
        assert row > 0 && col > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row, col);

        return tile(row, col) == 0;
    }

    /**
     * Return the col of value if tile is in place.
     *
     * @param value the tile
     * @return The correct col for tile
     */
    private int col(final int value) {
        assert value > 0 : String.format("Values start at 1: was %d", value);

        return (value - 1) % n + 1;
    }

    /**
     * Return the col of value if tile is in place.
     *
     * @param value the tile
     * @return The correct col for tile in place
     */
    private int row(final int value) {
        assert value > 0 : String.format("Values start at 1: was %d", value);

        return (value - 1) / n + 1;
    }


    private void set(final int row, final int col, final int value) {
        assert row > 0 && col > 0 : String.format("Rows and columns start at 1: was (%d, %d)", row, col);

        tiles[to1d(row, col)] = (char) value;
    }


    private boolean inside(final int row, final int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }

    private int positionOfEmpty() {
        for (int pos = 1; pos <= n * n; pos++) {
            if (tile(pos) == 0) {
                return pos;
            }
        }

        throw new IllegalStateException("Blank not found");
    }

}