import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;

public class Solver {
    /**
     *  Find a solution to the initial board (using the A* algorithm)
     *
     * @param initial board.
     */
    public Solver(Board initial) {

    }

    /**
     * Is the initial board solvable?
     *
     * @return true iff board is solvable.
     */
    public boolean isSolvable() {
        return false;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     *
     * @return moves to solve board.
     */
    public int moves() {
        return -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     *
     * @return sequence of boards;
     */
    public Iterable<Board> solution()      {
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        // create initial board from file
        final In in = new In(args[0]);
        final int n = in.readInt();
        final int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        final Board initial = new Board(blocks);
        final Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

