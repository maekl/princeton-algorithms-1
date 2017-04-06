import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

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
            assert(new Point(x0, y0).slopeTo(new Point(x1, y1)) == ((double) y1 - y0) / (x1 - x0));
    }

    @Property(trials = 1000)
    public void slopOfItselfIsNegativeInfinity2(
            @InRange(minInt = 0, maxInt = 100) int x0,
            @InRange(minInt = 0, maxInt = 100) int y0,
            @InRange(minInt = 0, maxInt = 100) int x1,
            @InRange(minInt = 0, maxInt = 100) int y1,
            @InRange(minInt = 0, maxInt = 100) int x2,
            @InRange(minInt = 0, maxInt = 100) int y2) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);

        assert (point0.slopeOrder().compare(point1, point2) == 0) == (point0.slopeTo(point1) == point0.slopeTo(point2));
        assert((point0.slopeOrder().compare(point1, point2) > 0) == (point0.slopeTo(point1) > point0.slopeTo(point2)));
        assert((point0.slopeOrder().compare(point1, point2) < 0) == (point0.slopeTo(point1) < point0.slopeTo(point2)));
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