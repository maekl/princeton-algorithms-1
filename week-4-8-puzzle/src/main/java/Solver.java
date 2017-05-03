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

        final Puzzle puzzle = new Puzzle(initial);

        final SearchNode solution = solve(puzzle);

        this.solution = isSolution(initial, solution) ? solution : null;
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

    private boolean isSolution(final Board initial, final SearchNode solution) {
        return initial(solution).equals(initial);
    }

    private static Board initial(final SearchNode solution) {

        final Iterator<Board> node = solution.iterator();

        while (true) {
            final Board current = node.next();
            if (!node.hasNext()) {
                return current;
            }
        }
    }

    private SearchNode solve(final Puzzle puzzle) {

        SearchNode solution;

        while ((solution = puzzle.search()) == null) {
            puzzle.searchNeighbors();
        }

        return solution;
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

    private static final class Puzzle {

        private final MinPQ<SearchNode> queue = new MinPQ<>();
        private SearchNode current;

        private Puzzle(final Board board) {
            this.current = new SearchNode(board);
            queue.insert(new SearchNode(this.current.board.twin()));
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

        private SearchNode search() {
            this.current = queue.delMin();

            return isGoal() ? this.current : null;
        }
    }
}

