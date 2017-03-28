import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF grid;
    private final boolean[] sites;
    private final int topSite;
    private final int bottomSite;
    private int numberOfOpenSites = 0;

    /**
     * Create n-by-n grid, with all sites blocked.
     * <p>
     * Internally, this class uses one virtual row at the top and one virtual row at the bottom.
     * <p>
     * The leftmost site of the top virtual row (topSite) is automatically connected to all sites in the row
     * below.
     * <p>
     * The leftmost site of the bottom virtual site (bottomSite) is automatically connected to all sites in the
     * row above
     * <p>
     * This simplifies the implementation by enabling us to check for percolation by simply checking if topSite and
     * bottomSite are connected.
     *
     * @param n grid size
     */
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be a positive integer");

        this.n = n;

        this.grid = new WeightedQuickUnionUF(n * (n + 2));
        this.sites = new boolean[n * (n + 2)];

        this.topSite = rowColTo1D(-1, 1);
        this.bottomSite = rowColTo1D(n, 1);

        connectTopToFirstRow();
        connectBottomToLastRow();
    }


    /**
     * Open site (row, col) if it is not open already.
     *
     * @param row the row of the site to open.
     * @param col the column of the site to open.
     */
    public void open(int row, int col) {
        assertInsideGrid(row, col);

        int i = rowColTo1D(row, col);

        if (!sites[i]) {

            sites[i] = true;

            connectIfInsideAndOpen(row, col, row, col - 1);
            connectIfInsideAndOpen(row, col, row, col + 1);
            connectIfInsideAndOpen(row, col, row - 1, col);
            connectIfInsideAndOpen(row, col, row + 1, col);

            numberOfOpenSites++;
        }
    }

    /**
     * Is site (row, col) open?
     *
     * @param row the row of the site to check.
     * @param col the column of the site to check.
     * @return true iff site is open.
     */
    public boolean isOpen(int row, int col) {
        assertInsideGrid(row, col);

        return sites[rowColTo1D(row, col)];
    }

    /**
     * Is site full?
     *
     * @param row the row of the site to check.
     * @param col the column of the site to check.
     * @return true iff site is full.
     */
    public boolean isFull(int row, int col) {
        assertInsideGrid(row, col);

        return isOpen(row, col) && grid.connected(topSite, rowColTo1D(row, col));
    }

    /**
     * The number of open sites.
     *
     * @return the number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * Does the system percolate?
     *
     * @return true iff system percolates.
     */
    public boolean percolates() {
        return grid.connected(topSite, bottomSite);
    }

    private int rowColTo1D(final int row, final int col) {
        return (row + 1) * n + col - 1;
    }

    private boolean insideGrid(final int row, final int col) {
        return row <= n && col <= n && row >= 1 && col >= 1;
    }

    private void assertInsideGrid(int row, int col) {
        if (!insideGrid(row, col)) {
            throw new IndexOutOfBoundsException(String.format("(%d,%d) is outside the grid (%d,%d))", row, col, n, n));
        }
    }

    private void connectIfInsideAndOpen(final int row1, final int col1, final int row2, final int col2) {
        if (insideGrid(row2, col2) && isOpen(row2, col2)) {
            grid.union(rowColTo1D(row1, col1), rowColTo1D(row2, col2));
        }
    }

    private void connectTopToFirstRow() {
        for (int column = 1; column <= n; column++) {
            grid.union(topSite, siteOfFirstRow(column));
        }
    }

    private void connectBottomToLastRow() {
        for (int column = 1; column <= n; column++) {
            grid.union(bottomSite, siteOfLastRow(column));
        }
    }

    private int siteOfLastRow(int column) {
        return rowColTo1D(n, column);
    }

    private int siteOfFirstRow(int column) {
        return rowColTo1D(1, column);
    }
}