import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BruteCollinearPointsTest extends CollinearTest {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPE() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPEWhenPointIsNull() {
        new BruteCollinearPoints(new Point[]{null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated() {
        new BruteCollinearPoints(new Point[]{point(1, 1), point(1, 1), point(1, 1), point(1, 1)});
    }

    @Test
    public void testInput1() throws IOException {
        assert (new BruteCollinearPoints(readPoints("input1.txt")).segments().length == 0);
    }

    @Test
    public void testInput2() throws IOException {
        assert (new BruteCollinearPoints(readPoints("input2.txt")).segments().length == 0);
    }

    @Test
    public void testInput3() throws IOException {
        Assert.assertEquals(0, new BruteCollinearPoints(readPoints("input3.txt")).segments().length);
    }

    @Test
    public void testInput8() throws IOException {
        LineSegment[] segments = new BruteCollinearPoints(readPoints("input8.txt")).segments();

        assert (segments.length == 2);
    }


}
