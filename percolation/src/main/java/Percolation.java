

public class Percolation {

    private final int n;

    /**
     * Create n-by-n grid, with all sites blocked.
     *
     * @param n grid width/height
     */
    public Percolation(int n) {
        if (n > 0) {
            this.n = n;
        } else {
            throw new IllegalArgumentException("n must be a positive integer");
        }
    }
    // create n-by-n grid, with all sites blocked

    /**
     * Open site (row, col) if it is not open already.
     *
     * @param row the row of the site to open.
     * @param col the column of the site to open.
     */
    public void open(int row, int col) {
        if (row < n || col < n) {
            throw new IllegalArgumentException(String.format("(%d,%d) is outside the grid (%d,$d))", row, col, n, n));
        }
        throw new RuntimeException();
    }

    /**
     * Is site (row, col) open?
     *
     * @param row the row of the site to check.
     * @param col the column of the site to check.
     * @return true iff site is open.
     */
    public boolean isOpen(int row, int col)  {
        return false;
    }

    /**
     * Is site full?
     *
     * @param row the row of the site to check.
     * @param col the column of the site to check.
     * @return true iff site is full.
     */
    public boolean isFull(int row, int col)  {
        return false;
    }

    /**
     * The number of open sites.
     *
     * @return the number of open sites.
     */
    public int numberOfOpenSites() {
        return 0;
    }

    /**
     * Does the system percolate?
     *
     * @return true iff system percolates.
     */
    public boolean percolates()  {
        return false;
    }

    /**
     * The test client.
     *
     * @param args
     */
    public static void main(String[] args)   {

    }
}