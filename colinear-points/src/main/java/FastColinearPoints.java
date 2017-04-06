public class FastColinearPoints {

    public FastColinearPoints(Point[] points) {
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

}
