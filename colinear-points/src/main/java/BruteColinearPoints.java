public class BruteColinearPoints {
    /**
     * Finds all line segments containing 4 points
     *
     * @param points the points.
     */
    public BruteColinearPoints(Point[] points) {
        if (points == null || containsNull(points)) {
            throw new NullPointerException();
        }
    }

    private boolean containsNull(Point[] points) {

        for (final Point point : points) {
            if (point == null)
                return true;
        }

        return false;
    }

    /**
     * The number of line segments.
     *
     * @return number of segments.
     */
    public int numberOfSegments() {
        return 0;
    }

    /**
     * The line sgements.
     *
     * @return line segments.
     */
    public LineSegment[] segments() {
        return null;
    }
}
