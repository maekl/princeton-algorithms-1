import java.util.Collections;

public class Board {
    /**
     * Construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks the blocks.
     */
    public Board(int[][] blocks) {

    }


    /**
     * board dimension n
     *
     * @return the dimension
     */
    public int dimension() {
        return -1;
    }

    /**
     * Number of blocks out of place
     *
     * @return the number of out-of-place blocks.
     */
    public int hamming() {
        return -1;
    }

    /**
     * The sum of Manhattan distances between blocks and goal
     *
     * @return sum of manhattan distances
     */
    public int manhattan() {
        return -1;
    }

    /**
     * Is this board the goal board?
     *
     * @return true iff board is goal
     */
    public boolean isGoal() {
        return false;
    }

    /**
     * Return a board that is obtained by exchanging any pair of blocks
     *
     * @return a twin board.
     */
    public Board twin() {
        return new Board(new int[0][0]);
    }

    /**
     * Does this board equal y?
     *
     * @param y other board
     *
     * @return true iff y equals this
     */
    public boolean equals(Object y) {
        return false;
    }

    /**
     * Return all neighboring boards.
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        return Collections.emptyList();
    }

    /**
     *
     * string representation of this board.
     *
     * @return string representation.
     */
    public String toString() {
        return "";
    }

}