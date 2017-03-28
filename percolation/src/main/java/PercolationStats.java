import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private final double[] results;
    private final int trials;

    /**
     * Run `trials` trials on n-by-n grid.
     *
     * @param n      grid width & height
     * @param trials number of trials to run
     */
    public PercolationStats(int n, int trials) {

        if (n <= 0)
            throw new IllegalArgumentException("n not a positive integer, was " + n);
        if (trials <= 0) {
            throw new IllegalArgumentException("trials not a positive integer, was " + trials);
        }

        this.n = n;
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {

            final Percolation grid = new Percolation(n);

            while (!grid.percolates()) {
                openRandom(grid);
            }

            results[i] = ((double) grid.numberOfOpenSites()) / (n * n);
        }
    }


    private void openRandom(Percolation grid) {
        while (true) {

            final int row = StdRandom.uniform(1, n + 1);
            final int col = StdRandom.uniform(1, n + 1);

            if (!grid.isOpen(row, col)) {
                grid.open(row, col);
                return;
            }
        }
    }

    /**
     * The sample mean of the percolation threshold
     *
     * @return sample mean
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * The sample standard deviation of the percolation threshold
     *
     * @return standard deviation
     */
    public double stddev() {
        return StdStats.stddev(results);

    }

    /**
     * The low endpoint of the 95% confidence interval
     *
     * @return low endpoint of confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    /**
     * The high endpoint of the 95% confidence interval
     *
     * @return high endpoint of confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);

    }

    public static void main(String[] args) {

        try {

            final int gridSize = Integer.parseInt(args[0]);
            final int trials = Integer.parseInt(args[1]);

            final PercolationStats stats = new PercolationStats(gridSize, trials);

            StdOut.println(String.format("mean                     = %s", stats.mean()));
            StdOut.println(String.format("stddev                   = %s", stats.stddev()));
            StdOut.println(String.format("95%% confidence interval = [%s, %s]", stats.confidenceLo(), stats.confidenceHi()));


        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The program expects two integer arguments. n (grid size) and T (# of trials)");
        }
    }
}
