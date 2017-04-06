import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class PointPropertyTest {

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

    public void testGreaterComparatorThan(
            @InRange(minInt = 0, maxInt = 32767) int x0,
            @InRange(minInt = 0, maxInt = 32767) int y0,
            @InRange(minInt = 0, maxInt = 32767) int x1,
            @InRange(minInt = 0, maxInt = 32767) int y1) {

        Point point0 = new Point(x0, y0);
        Point point1 = new Point(x1, y1);

        assert (point0.compareTo(point1) > 0 == y0 > y1 || y0 == y1 && x0 > x1);
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
