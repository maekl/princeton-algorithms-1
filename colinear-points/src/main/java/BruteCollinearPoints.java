import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> collinear = new ArrayList<>();

    /**
     * Finds all line segments containing 4 points
     *
     * @param points the points.
     */
    public BruteCollinearPoints(Point[] points) {

        final Point[] sorted = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            sorted[i] = points[i];
        }

        Arrays.sort(sorted);

        for (int i = 0; i < sorted.length - 3; i++) {

            notNull(sorted[i]);

            for (int j = i + 1; j < sorted.length - 2; j++) {

                final double slope = sorted[i].slopeTo(sorted[j]);

                validateSlope(slope);

                for (int k = j + 1; k < sorted.length - 1; k++) {

                    final double slope2 = sorted[i].slopeTo(sorted[k]);

                    validateSlope(slope2);

                    if (Double.compare(slope, slope2) == 0) {

                        for (int l = k + 1; l < sorted.length; l++) {

                            double slope3 = sorted[l].slopeTo(sorted[k]);

                            validateSlope(slope3);

                            if (Double.compare(slope3, slope2) == 0) {
                                collinear.add(new LineSegment(sorted[i], sorted[l]));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void notNull(final Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
    }

    private static void validateSlope(final double slope) {
        if (slope == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * The number of line segments.
     *
     * @return number of segments.
     */
    public int numberOfSegments() {
        return collinear.size();
    }

    /**
     * The line sgements.
     *
     * @return line segments.
     */
    public LineSegment[] segments() {
        return collinear.toArray(new LineSegment[collinear.size()]);
    }
}
