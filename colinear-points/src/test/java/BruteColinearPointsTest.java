import org.junit.Test;

public class BruteColinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPE() {
        new BruteColinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPEWhenPointIsNull() {
        new BruteColinearPoints(new Point[]{null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated() {
        new BruteColinearPoints(new Point[]{new Point(1, 1), new Point(1, 1)});
    }
}
