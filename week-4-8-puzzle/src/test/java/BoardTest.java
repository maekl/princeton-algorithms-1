import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(JUnitQuickcheck.class)
public class BoardTest {

    @Test
    public void dimensionTestEmpty() {
        final Board board = new Board(new int[0][0]);
        Assert.assertEquals(0, board.dimension());
    }

    @Test
    public void dimensionTestOne() {
        final Board board = new Board(new int[1][1]);
        Assert.assertEquals(1, board.dimension());
    }

    @Test
    public void testHammingZeroBoard() {
        final Board board = new Board(new int[0][0]);

        Assert.assertEquals(0, board.hamming());
    }

    @Test
    public void testHammingOneByOne() {
        final int[][] tiles = new int[1][1];

        tiles[0][0] = 1;

        final Board board = new Board(tiles);

        Assert.assertEquals(0, board.hamming());
    }

    @Test
    public void testHammingTwoByTwo() {
        final int[][] tiles = new int[2][2];

        tiles[0][0] = 1;
        tiles[0][1] = 2;
        tiles[1][0] = 3;
        tiles[1][1] = 4;

        final Board board = new Board(tiles);

        Assert.assertEquals(0, board.hamming());
    }

    @Property
    public void testHammingAllZeroesIsZero(@InRange(minInt = 1, maxInt = 100) final int n) {

        final int[][] cells = new int[n][n];

        for (int row = 0; row < n; row++) {
            Arrays.fill(cells[row], 0);
        }

        final Board board = new Board(cells);

        Assert.assertEquals(0, board.hamming());
    }

    @Property
    public void testHammingAllInPlace(@InRange(minInt = 0, maxInt = 100) final int n) {

        final int[][] cells = TestUtils.cellsAllInPlace(n);

        final Board board = new Board(cells);

        Assert.assertEquals(0, board.hamming());
    }

    @Property
    public void testHammingManyWrong(@InRange(minInt = 0, maxInt = 100) final int n) {

        final int[][] cells = new int[n][n];

        for (int value = 1; value <= n * n - 1; value++) {
            cells[(value - 1) / n][(value - 1) % n] = 1;
        }

        if (n > 0)
            cells[n - 1][n - 1] = 0;

        final Board board = new Board(cells);

        if (n > 1) {
            Assert.assertEquals(n * n - 1 - 1, board.hamming());
        } else {
            Assert.assertEquals(0, board.hamming());
        }
    }

    @Property
    public void testHammingEmptyTiles(@InRange(minInt = 0, maxInt = 100) final int n) {

        final int[][] cells = new int[n][n];

        for (int value = 1; value <= n * n - 1; value++)
            cells[(value - 1) / n][(value - 1) % n] = value;


        if (n > 1) {
            final int randomPosition = new Random().nextInt(n * n) + 1;

            cells[(randomPosition - 1) / n][(randomPosition - 1) % n] = 0;

            final Board board = new Board(cells);

            Assert.assertEquals(0, board.hamming());
        } else {
            Assert.assertEquals(0, new Board(new int[0][0]).hamming());

        }
    }

    @Property
    public void testManhattanAllInPlace(@InRange(minInt = 0, maxInt = 100) final int n) {

        final int[][] cells = TestUtils.cellsAllInPlace(n);

        Assert.assertEquals(0, new Board(cells).manhattan());

    }

    @Test
    public void testManhattanHorizontalIncrease() {
        final int[][] tiles1 = new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 2}};
        final int[][] tiles2 = new int[][]{{1, 3, 0}, {4, 5, 6}, {7, 8, 2}};

        final Board board1 = new Board(tiles1);
        final Board board2 = new Board(tiles2);

        assertThat(board2.manhattan() - board1.manhattan(), is(1));
    }

    @Test
    public void testManhattanHorizontalDecrease() {
        final int[][] tiles1 = new int[][]{{1, 3, 0}, {4, 5, 6}, {7, 8, 2}};
        final int[][] tiles2 = new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 2}};

        final Board board1 = new Board(tiles1);
        final Board board2 = new Board(tiles2);

        assertThat(board2.manhattan() - board1.manhattan(), is(-1));

    }

    @Property
    public void testIsGoal(@InRange(minInt = 0, maxInt = 30) final int n) {

        final int[][] permutation = TestUtils.permutation(n);

        final Board board = new Board(permutation);

        Assert.assertTrue(board.hamming() == 0 && board.isGoal() || board.hamming() != 0 && !board.isGoal());
    }

    @Property
    public void testTwin(@InRange(minInt = 0, maxInt = 100) final int n) {

        final int[][] ints = TestUtils.cellsAllInPlace(n);

        if (n > 1)
            Assert.assertEquals(2, new Board(ints).twin().hamming());
    }

    @Property
    public void testEquals(@InRange(minInt = 0, maxInt = 30) final int n) {
        final int[][] permutation = TestUtils.permutation(n);

        final Board board = new Board(permutation);
        final Board other = new Board(permutation);

        final boolean equals = board.equals(other);

        Assert.assertTrue(equals);
    }

    @Property
    public void testNotEqualsTwin(@InRange(minInt = 0, maxInt = 30) final int n) {
        final int[][] permutation = TestUtils.permutation(n);

        final Board board = new Board(permutation);
        final Board other = new Board(permutation).twin();

        final boolean equals = board.equals(other);

        if (n > 1)
            Assert.assertFalse(equals);
    }

    @Test
    public void testNeighborsZeroBoard() {
        final Iterable<Board> neighbors = new Board(new int[0][0]).neighbors();

        assertThat(neighbors, is(emptyIterable()));
    }


    @Test
    public void testNeighborsOneByObe() {
        final int[][] tiles = new int[1][1];

        tiles[0][0] = 0;
        final Iterable<Board> neighbors = new Board(tiles).neighbors();

        assertThat(neighbors, is(emptyIterable()));
    }

    @Property
    public void testNeighborsTwoByTwo() {
        final int[][] tiles = TestUtils.permutation(2);

        final Iterable<Board> neighbors = new Board(tiles).neighbors();

        assertThat(neighbors, is(iterableWithSize(2)));
    }

    @Property
    public void testNeighborsNbyN(@InRange(minInt = 1, maxInt = 30) final int n) {
        final BoardWithBlankPosition board = new BoardWithBlankPosition(TestUtils.permutation(n));
        Assert.assertEquals(board.countNeighbors(), Iterables.size(board.board.neighbors()));
    }

    @Test
    public void testManhattanZeroDimension() {
        Assert.assertEquals(0, new Board(new int[0][0]).manhattan());
    }

    @Test
    public void testManhattanDimensionOne() {
        Assert.assertEquals(0, new Board(new int[][]{{1}}).manhattan());
    }

    @Test
    public void testManhattanDimensionTwo() {
        Assert.assertEquals(0, new Board(new int[][]{{1, 2}, {3, 0}}).manhattan());
    }

    @Test
    public void testNeighbors() {

        final Board board = new Board(new int[][]{{1, 2}, {0, 3}});

        final Board neighbor1 = new Board(new int[][]{{1, 2}, {3, 0}});
        final Board neighbor2 = new Board(new int[][]{{0, 2}, {1, 3}});


        assertThat(neighborsIncludes(board, neighbor1), is(true));
        assertThat(neighborsIncludes(board, neighbor2), is(true));

        assertThat(board.neighbors(), iterableWithSize(2));
    }

    @Test
    public void testNeighbors2() {
        final int[][] tiles = new int[][]{{0, 2}, {3, 1}};

        final Board neighbor1 = new Board(new int[][]{{2, 0}, {3, 1}});
        final Board neighbor2 = new Board(new int[][]{{3, 2}, {0, 1}});

        final Board board = new Board(tiles);

        assertThat(neighborsIncludes(board, neighbor1), is(true));
        assertThat(neighborsIncludes(board, neighbor2), is(true));

        final ArrayList<Board> neighbors = Lists.newArrayList(board.neighbors());

        assertThat(neighbors, hasSize(2));
    }


    private boolean neighborsIncludes(final Board board, final Board n1) {
        return Lists.newArrayList(board.neighbors()).stream().anyMatch(b -> b.equals(n1));
    }

    @Property
    public void testManhattanNeighbor(@InRange(minInt = 2, maxInt = 30) final int n) {

        final Board board = new Board(TestUtils.permutation(n));

        for (final Board neighbor : board.neighbors()) {
            assertThat(neighbor.manhattan() - board.manhattan(), either(is(-1)).or(is(1)));
        }
    }


    private class BoardWithBlankPosition {

        final Board board;
        final Position blank;

        private Iterable<Board> neighbors() {
            return board.neighbors();
        }

        private BoardWithBlankPosition(final int[][] tiles) {
            this.board = new Board(tiles);
            this.blank = blank(tiles);
        }

        private int countNeighbors() {

            if (board.dimension() == 1) {
                return 0;
            }

            if (blank.corner()) {
                return 2;
            } else if (blank.side()) {
                return 3;
            } else {
                return 4;
            }
        }


        private Position blank(final int[][] tiles) {
            for (int row = 0; row < tiles.length; row++) {
                for (int column = 0; column < tiles[row].length; column++) {
                    if (tiles[row][column] == 0) {
                        return new Position(row + 1, column + 1, tiles.length);
                    }
                }
            }
            throw new IllegalStateException("No blank found");
        }
    }


    private final class Position {

        private final int row;
        private final int column;
        private final int n;

        private boolean corner() {
            return (column == 1 || column == n) && (row == 1 || row == n);
        }

        private boolean side() {
            return column == 1 || column == n || row == 1 || row == n;
        }

        private Position(final int row, final int column, final int n) {
            this.row = row;
            this.column = column;
            this.n = n;
        }
    }

}
