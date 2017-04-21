
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        final Point[] sortedAux = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            sortedAux[i] = points[i];
        }

        final int length = points.length;

        Arrays.sort(sortedAux);

        for (int i = 0; i < length - 1; i++) {

            final Point[] sorted = sortedAux.clone();

            final Point origin = sorted[i];

            Arrays.sort(sorted, origin.slopeOrder());

            int offset;

            for (int j = 1; j < length - 1; j += offset) {

                final double slope = origin.slopeTo(sorted[j + 1]);

                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }

                offset = adjacentCollinearPoints(sorted, origin, slope, j);

                if (offset >= 4) {

                    final Point start = sorted[j];
                    final Point end = sorted[offset + j - 2];

                    if (origin.compareTo(start) < 0) {
                        segments.add(new LineSegment(origin, end));
                    }
                }
            }
        }
    }

    private int adjacentCollinearPoints(final Point[] sorted, final Point origin, final double slope, int start) {

        int index = start;

        while (index < sorted.length && Double.compare(slope, origin.slopeTo(sorted[index])) == 0) {
            index++;
        }

        return index - start + 1;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
