import org.junit.Test;

public class FastColinearPointsTest {
    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPE() {
        new FastColinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPEWhenPointIsNull() {
        new FastColinearPoints(new Point[]{null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated() {
        new FastColinearPoints(new Point[]{new Point(1, 1), new Point(1, 1)});
    }
}
