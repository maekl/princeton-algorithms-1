import com.pholser.junit.quickcheck.Property;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FastCollinearPointsTest extends CollinearTest {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPEWhenPointIsNull() {
        new FastCollinearPoints(new Point[]{null});
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNPE() {
        new FastCollinearPoints(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated() {
        new FastCollinearPoints(new Point[]{point(1, 1), point(1, 1), point(1, 1), point(1, 1)});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated2() {

        final List<Point> points = new ArrayList<>(
                Arrays.asList(
                        point(27039, 21985),
                        point(11481, 20878),
                        point(27039, 21985),
                        point(17443, 7822),
                        point(5446, 7822)
                )
        );


        Collections.shuffle(points);


        new FastCollinearPoints(points.toArray(new Point[points.size()]));
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated3() {
        List<Point> points = Arrays.asList(
                point(13837, 8278),
                point(547, 29344),
                point(547, 29344)
        );

        Collections.shuffle(points);

        new FastCollinearPoints(points.toArray(new Point[points.size()]));
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated4() {
        new FastCollinearPoints(new Point[]{
                point(30122, 32094),
                point(2604, 29481),
                point(2604, 29481)
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIAEIfPointsAreRepeated5() {
        new FastCollinearPoints(new Point[]{
                point(26612, 19929),
                point(26612, 19929)

        });
    }

    @Test
    public void testFourHorizontalPoints() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0),
                new Point(3, 0)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertSameSegments(collinear.segments(), new Point(0, 0), new Point(3, 0));
    }

    @Test

    public void testFourHorizontalPointsWithPseudoOrigin() {
        Point[] points = new Point[]{
                new Point(2, 0),
                new Point(0, 0),
                new Point(1, 0),
                new Point(3, 0)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertSameSegments(collinear.segments(), new Point(0, 0), new Point(3, 0));
    }

    @Test
    public void testFiveHorizontalPoints() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0),
                new Point(3, 0),
                new Point(4, 0)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertEquals(1, collinear.numberOfSegments());

        assertSameSegments(collinear.segments(), new Point(0, 0), new Point(4, 0));
    }

    @Test
    public void testFourVerticalPoints() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertSameSegments(collinear.segments(), new Point(0, 0), new Point(0, 3));
    }

    @Test
    public void testFiveVerticalPoints() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertSameSegments(collinear.segments(), new Point(0, 0), new Point(0, 4));
    }

    @Test

    public void testFiveVerticalAndHorizontalPoints() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4),

                new Point(1, 0),
                new Point(2, 0),
                new Point(3, 0),
                new Point(4, 0)

        };

        final FastCollinearPoints collinear = new FastCollinearPoints(points);

        assertSameSegments(
                collinear.segments(),
                new Point(0, 0), new Point(0, 4),
                new Point(0, 0), new Point(4, 0)
        );
    }


    @Test
    public void testInput8() throws IOException {
        LineSegment[] segments = new FastCollinearPoints(readPoints("input8.txt")).segments();

        assertSameSegments(
                segments,
                new Point(10000, 0), new Point(0, 10000),
                new Point(3000, 4000), new Point(20000, 21000)
        );

    }

    @Test
    public void testNegativeSlope() {
        final List<Point> points = Arrays.asList(
                new Point(0, 7),
                new Point(1, 6),
                new Point(2, 5),
                new Point(3, 4),
                new Point(4, 3),
                new Point(5, 2),
                new Point(6, 1),
                new Point(7, 0));

        for (int i = 0; i < 20; i++) {

            final FastCollinearPoints collinear = new FastCollinearPoints(points.toArray(new Point[points.size()]));

            assertSameSegments(
                    collinear.segments(),
                    new Point(7, 0), new Point(0, 7)
            );

            Collections.shuffle(points);
        }
    }

    @Test
    public void testPositiveSlope() {
        final List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5),
                new Point(6, 6),
                new Point(7, 7));

        for (int i = 0; i < 20; i++) {

            final FastCollinearPoints collinear = new FastCollinearPoints(points.toArray(new Point[points.size()]));

            assertSameSegments(
                    collinear.segments(),
                    new Point(0, 0), new Point(7, 7)
            );

            Collections.shuffle(points);
        }
    }


    @Test
    public void testInput1() throws IOException {
        assertEquals(0, new FastCollinearPoints(readPoints("input1.txt")).segments().length);
    }

    @Test
    public void testInput2() throws IOException {
        assertEquals(0, new FastCollinearPoints(readPoints("input2.txt")).segments().length);
    }

    @Test
    public void testInput3() throws IOException {
        assertEquals(0, new FastCollinearPoints(readPoints("input3.txt")).segments().length);
    }

    @Test
    public void testInput6() throws IOException {

        assertSameSegments(
                new FastCollinearPoints(readPoints("input6.txt")).segments(),
                new Point(14000, 10000), new Point(32000, 10000));
    }

    @Test
    public void testInput10() throws IOException {

        assertSameSegments(
                new FastCollinearPoints(readPoints("input10.txt")).segments(),
                new Point(28000, 13500), new Point(3000, 26000),
                new Point(1000, 18000), new Point(4000, 30000));
    }

    @Test
    public void testInput20() throws IOException {

        assertSameSegments(
                new FastCollinearPoints(readPoints("input20.txt")).segments(),
                new Point(4096, 20992), new Point(8128, 20992),
                new Point(4096, 20992), new Point(4096, 25088),
                new Point(4096, 25088), new Point(8192, 25088),
                new Point(8192, 25088), new Point(8192, 29184),
                new Point(4160, 29184), new Point(8192, 29184));
    }

    @Test
    public void testInputThreeHorizontalLines() throws IOException {

        Point[] points = new Point[]{
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0),
                new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3), new Point(5, 3),
                new Point(0, 5), new Point(1, 5), new Point(2, 5), new Point(3, 5), new Point(4, 5), new Point(5, 5)
        };

        assertSameSegments(
                new FastCollinearPoints(points).segments(),
                new Point(0, 0), new Point(5, 0),
                new Point(0, 3), new Point(5, 3),
                new Point(0, 5), new Point(5, 5));
    }
}
