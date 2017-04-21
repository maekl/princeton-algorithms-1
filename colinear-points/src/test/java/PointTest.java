import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

@RunWith(JUnitQuickcheck.class)
public class PointTest {

    @Property
    public void testComparatorLessThan(
            @InRange(minInt = 0, maxInt = 32767) int x0,
            @InRange(minInt = 0, maxInt = 32767) int y0,
            @InRange(minInt = 0, maxInt = 32767) int x1,
            @InRange(minInt = 0, maxInt = 32767) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert (point0.compareTo(point1) < 0 == y0 < y1 || y0 == y1 && x0 < x1);
    }

    @Property
    public void testComparatorEqual(
            @InRange(minInt = 0, maxInt = 32767) int x0,
            @InRange(minInt = 0, maxInt = 32767) int y0,
            @InRange(minInt = 0, maxInt = 32767) int x1,
            @InRange(minInt = 0, maxInt = 32767) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert point0.compareTo(point1) == 0 == (y0 == y1 && x0 == x1);
    }

    @Property
    public void testGreaterComparatorThan(
            @InRange(minInt = 0, maxInt = 32767) int x0,
            @InRange(minInt = 0, maxInt = 32767) int y0,
            @InRange(minInt = 0, maxInt = 32767) int x1,
            @InRange(minInt = 0, maxInt = 32767) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert (point0.compareTo(point1) > 0 == y0 > y1 || y0 == y1 && x0 > x1);
    }

    @Property(trials = 1000)
    public void slopOfHorizontalLinesIsPositiveZero(

            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert (y0 == y1 && x0 != x1) == ((point0.slopeTo(point1)) == +0D);
    }

    @Property(trials = 1000)
    public void slopOfVerticalLineIsPositiveInfinity(

            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert ((x0 == x1 && y0 != y1) == new Double(point0.slopeTo(point1)).equals(Double.POSITIVE_INFINITY));
    }

    @Property(trials = 1000)
    public void slopOfItselfIsNegativeInfinity(
            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert ((x0 == x1 && y0 == y1) == new Double(point0.slopeTo(point1)).equals(Double.NEGATIVE_INFINITY));
    }

    @Property(trials = 10000)
    public void slopeFormula(
            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1) {

        if (x0 != x1 && y0 != y1)
            assert (new Point(x0, y0).slopeTo(new Point(x1, y1)) == ((double) y1 - y0) / (x1 - x0));
    }


//    @Property(trials = 1000)
//    public void slopOfItselfIsNegativeInfinity2(
//            @InRange(minInt = 0, maxInt = 100) int x0,
//            @InRange(minInt = 0, maxInt = 100) int y0,
//            @InRange(minInt = 0, maxInt = 100) int x1,
//            @InRange(minInt = 0, maxInt = 100) int y1,
//            @InRange(minInt = 0, maxInt = 100) int x2,
//            @InRange(minInt = 0, maxInt = 100) int y2) {
//
//        Point point0 = new Point(x0, y0);
//        Point point1 = new Point(x1, y1);
//        Point point2 = new Point(x2, y2);
//
//        assert (point0.slopeOrder().compare(point1, point2) == 0) == (point0.slopeTo(point1) == point0.slopeTo(point2));
//        assert((point0.slopeOrder().compare(point1, point2) > 0) == (point0.slopeTo(point1) > point0.slopeTo(point2)));
//        assert((point0.slopeOrder().compare(point1, point2) < 0) == (point0.slopeTo(point1) < point0.slopeTo(point2)));
//    }

    @Test
    public void testSimpleSlopeOrder() {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(1, 2);

        Comparator<Point> pointComparator = p1.slopeOrder();

        Assert.assertTrue(pointComparator.compare(p2, p3) < 0);
    }

    @Test
    public void testEqual() {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(1, 1);

        Assert.assertTrue(p1.slopeOrder().compare(p2, p3) == 0);
    }

    @Test
    public void testGreaterThan() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(1, 5);

        Assert.assertTrue(p1.slopeOrder().compare(p3, p2) > 0);
    }

    @Test
    public void testLessThan() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(3, 3);
        Point p3 = new Point(2, 5);

        Assert.assertTrue(p1.slopeOrder().compare(p2, p3) < 0);
    }

    @Property(trials = 100000)
    public void comparatorInvariants(
            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1,
            @InRange(minInt = 0, maxInt = 100) int x2,
            @InRange(minInt = 0, maxInt = 100) int y2,
            @InRange(minInt = 0, maxInt = 100) int x3,
            @InRange(minInt = 0, maxInt = 100) int y3) {

        Point p0 = new Point(x0, y0);
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        Point p3 = new Point(x3, y3);

        Comparator<Point> slopeOrder = p0.slopeOrder();

        Assert.assertTrue(slopeOrder.compare(p0, p0) == 0);
        Assert.assertTrue(slopeOrder.compare(p1, p2) * slopeOrder.compare(p2, p1) <= 0);

        if (slopeOrder.compare(p1, p2) > 0 && slopeOrder.compare(p2, p3) > 0) {
            Assert.assertTrue(slopeOrder.compare(p1, p3) > 0);
        }
    }

    public class Points extends Generator<Point> {

        protected Points() {
            super(Point.class);
        }

        public Point generate(final SourceOfRandomness rand, final GenerationStatus status) {
            return new Point(rand.nextInt(0, 32767), rand.nextInt(0, 32767));
        }
    }
}