import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {

    private final SearchNode solution;

    /**
     * Find a solution to the initial board (using the A* algorithm)
     *
     * @param initial board.
     */
    public Solver(final Board initial) {

        final PuzzleInstances puzzle = new PuzzleInstances(initial);

        while (puzzle.search()) {
            puzzle.expandSearchToNeighbors();
        }

        this.solution = puzzle.solution();
    }

    /**
     * Is the initial board solvable?
     *
     * @return true iff board is solvable.
     */
    public boolean isSolvable() {
        return solution != null;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     *
     * @return moves to solve board.
     */
    public int moves() {
        return isSolvable() ? solution.moves : -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     *
     * @return sequence of boards;
     */
    public Iterable<Board> solution() {

        if (!isSolvable()) {
            return null;
        }

        final Stack<Board> steps = new Stack<>();

        for (final Board step : solution) {
            steps.push(step);
        }

        return steps;
    }

    public static void main(final String[] args) {
        // create initial board from file
        final In in = new In(args[0]);
        final int n = in.readInt();
        final int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        final Board initial = new Board(tiles);
        final Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (final Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static final class SearchNode implements Comparable<SearchNode>, Iterable<Board> {

        private final Board board;
        private final int moves;
        private final SearchNode prev;

        private SearchNode(final Board board, final int moves, final SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        private SearchNode(final Board board) {
            this.board = board;
            this.moves = 0;
            this.prev = null;
        }

        private SearchNode newNeighbor(final Board neighbor) {
            return new SearchNode(neighbor, moves + 1, this);
        }

        private int priority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(final SearchNode o) {
            final int prio = priority() - o.priority();

            if (prio == 0) {
                final int m = board.manhattan() - o.board.manhattan();

                if (m == 0) {
                    return board.hamming() - o.board.hamming();
                } else {
                    return m;
                }
            } else {
                return prio;
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return new SolutionIterator(this);
        }

        private static final class SolutionIterator implements Iterator<Board> {

            private SearchNode current;

            private SolutionIterator(final SearchNode current) {
                this.current = current;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Board next() {

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                final SearchNode result = current;

                current = current.prev;

                return result.board;
            }
        }
    }


    private static final class PuzzleInstances {

        private final Puzzle puzzle;
        private final Puzzle twin;

        private PuzzleInstances(final Board board) {
            puzzle = new Puzzle(board);
            twin = new Puzzle(board.twin());
        }

        private void expandSearchToNeighbors() {
            puzzle.searchNeighbors();
            twin.searchNeighbors();
        }

        private boolean search() {
            return !puzzle.search() && !twin.search();
        }

        private SearchNode solution() {
            return puzzle.isGoal() ? puzzle.current : null;
        }
    }

    private static final class Puzzle {

        private final MinPQ<SearchNode> queue = new MinPQ<>();
        private SearchNode current;

        private Puzzle(final Board board) {
            this.current = new SearchNode(board);
            queue.insert(this.current);
        }

        private void searchNeighbors() {

            final Iterable<Board> neighbors = current.board.neighbors();

            for (final Board neighbor : neighbors)
                if (current.prev == null || !current.prev.board.equals(neighbor))
                    queue.insert(current.newNeighbor(neighbor));
        }

        private boolean isGoal() {

            return current.board.manhattan() == 0;
        }

        private boolean search() {
            this.current = queue.delMin();

            return isGoal();
        }
    }
}

