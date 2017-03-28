import org.junit.Assert;
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpenThrowsIndexOutOfBounds() {
        new Percolation(2).open(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpenThrowsIndexOtOfBounds2() {
        new Percolation(2).open(0, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsOpenThrowsIndexOutOfBounds() {
        new Percolation(2).isOpen(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsOpenThrowsIndexOutOfBounds2() {
        new Percolation(2).isOpen(0, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsFullThrowsIndexOutOfBounds() {
        new Percolation(2).isFull(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsFullThrowsIndexOutOfBounds2() {
        new Percolation(2).isFull(0, 1);
    }

    @Test
    public void testOpen() {

        final Percolation p = new Percolation(5);

        p.open(1, 1);
        p.open(5, 5);

        Assert.assertTrue(p.isOpen(1, 1));
        Assert.assertTrue(p.isOpen(5, 5));

        Assert.assertFalse(p.isOpen(2, 2));
    }


}
