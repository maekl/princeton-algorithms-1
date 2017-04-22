
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

            if (sortedAux[i].compareTo(sortedAux[i + 1]) == 0)
                throw new IllegalArgumentException();

            final Point[] sorted = sortedAux.clone();

            final Point origin = sorted[i];


            Arrays.sort(sorted, origin.slopeOrder());

            for (int j = 1, offset; j < length - 1; j += offset) {

                double slope = origin.slopeTo(sorted[j]);

                int k = j + 1;

                while (k < length) {

                    if (Double.compare(slope, sorted[j].slopeTo(sorted[k])) != 0)
                        break;

                    k++;
                }

                if (k - j + 1 >= 4) {

                    final Point start = sorted[j];
                    final Point end = sorted[k - 1];

                    if (origin.compareTo(start) < 0) {
                        segments.add(new LineSegment(origin, end));
                    }
                }

                offset = k - j;
            }
        }
    }


    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
