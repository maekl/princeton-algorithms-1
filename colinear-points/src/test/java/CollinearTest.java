import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

class CollinearTest {

    Point[] readPoints(final String file)  {
        final In in = new In(getClass().getResource(file));
        final int n = in.readInt();
        final Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = point(x, y);
        }

        return points;
    }

    protected static Point point(final int x0, final int y0) {
        return new Point(x0, y0);
    }

    static void assertSameSegments(final LineSegment[] segments, final Point... points) {

        assertEquals(points.length / 2, segments.length);

        final List<Endpoints> endpoints = Endpoints.from(points);

        for (final LineSegment segment : segments) {

            boolean condition = endpoints.removeIf(e -> e.equivalent(segment));

            if (!condition) {
                fail(segment.toString() + " does not exist in " + endpoints.stream().map(Endpoints::toString).collect(Collectors.joining(",")));
            }
        }


        assertEquals(0, endpoints.size());
    }

    private static final class Endpoints {

        private final Point p1;
        private final Point p2;

        private Endpoints(final Point p1, final Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        private boolean equivalent(final LineSegment other) {
            return other.toString().equals(asSegment().toString()) || other.toString().equals(asReverseSegment().toString());
        }

        private LineSegment asSegment() {
            return new LineSegment(p1, p2);
        }

        private LineSegment asReverseSegment() {
            return new LineSegment(p2, p1);
        }

        private static List<Endpoints> from(Point... points) {
            final List<Endpoints> endpoints = new ArrayList<>(points.length / 2);

            for (int i = 0; i < points.length; i += 2) {
                endpoints.add(new Endpoints(points[i], points[i + 1]));
            }

            return endpoints;
        }

        @Override
        public String toString() {
            return String.format("Endpoint: (%s, %s)", p1.toString(), p2.toString());
        }
    }

}
