import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points = new TreeSet<>();

    /**
     * is the set empty?
     *
     * @return true iff empty
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Number of points in the set
     *
     * @return number of points in set
     */
    public int size() {
        return points.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     *
     * @param p point to add
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null)

            throw new NullPointerException();
        return points.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.setPenRadius(0.01);

        StdDraw.enableDoubleBuffering();

        for (final Point2D p : points)
            p.draw();

        StdDraw.pause(50);
    }

    /**
     * All points that are inside the rectangle
     *
     * @param rect query rectangle
     * @return points inside
     */
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null)
            throw new NullPointerException();


        final List<Point2D> inside = new ArrayList<>();

        for (final Point2D point : points)
            if (rect.contains(point))
                inside.add(point);

        return inside;

    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p the point
     * @return neighbor
     */
    public Point2D nearest(final Point2D p) {
        if (p == null)
            throw new NullPointerException();

        Point2D nearest = null;

        double dist = Double.MAX_VALUE;

        for (final Point2D point : points)
            if (nearest == null || Double.compare(p.distanceTo(point), dist) < 0) {
                nearest = point;
                dist = p.distanceTo(point);
            }

        return nearest;
    }
}
