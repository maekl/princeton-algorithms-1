import org.junit.Test;

public class PercolationTest {
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentExceptionOnZero() {
        new Percolation(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsIllegalArgumentExceptionNegative() {
        new Percolation(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenThrowsIllegalArgumentExceptionIfOutside() {
        new Percolation(2).open(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenThrowsIllegalArgumentExceptionIfOutside2() {
        new Percolation(2).open(0, 1);
    }
}
