/******************************************************************************
 *  Compilation:  javac PuzzleChecker.java
 *  Execution:    java PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class PuzzleChecker {

    public static void main(final String[] args) throws URISyntaxException {

        final URL resource = Resources.getResource("puzzles");

        final ArrayList<File> files;

        files = Lists.newArrayList(new File(resource.toURI()).listFiles());

        // for each command-line argument
        for (final File filename : files) {

            // read in the board specified in the filename
            final In in = new In("src/test/resources/puzzles/puzzle15.txt");
            final int n = in.readInt();
            final int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            final Board initial = new Board(tiles);
            final Stopwatch timer = new Stopwatch();
            final Solver solver = new Solver(initial);

            // check if puzzle is solvable; if so, solve it print out number of moves
            final int moves = solver.moves();
            final double time = timer.elapsedTime();


            if (solver.isSolvable()) {
                final Board last = Iterables.getLast(solver.solution());

                System.out.println(last);
            }

            StdOut.printf("%-25s %7d %8.2f\n", filename, moves, time);
        }
    }
}
