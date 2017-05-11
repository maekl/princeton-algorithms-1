import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class KdTree {

    private int size;
    private VerticalNode root = null;

    /**
     * Construct an empty set of points
     */
    public KdTree() {

    }

    /**
     * is the set empty?
     *
     * @return true iff empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Number of points in the set
     *
     * @return number of points in set
     */
    public int size() {
        return size;
    }

    /**
     * Add the point to the set (if it is not already in the set)
     *
     * @param p point to add
     */
    public void insert(final Point2D p) {
        if (p == null)
            throw new NullPointerException();

        if (root == null) {
            root = new VerticalNode(p);
            size = 1;
        } else {
            insert(p, root);
        }
    }

    private void insert(final Point2D p, final Node position) {
        put(p, location(p, position));
    }

    private void put(final Point2D p, final Node n) {
        if (!n.point.equals(p)) {
            n.addChild(p);
            size++;
        }
    }

    /**
     * Does the set contain point p?
     */
    public boolean contains(final Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return location(p, root).point.equals(p);
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        StdDraw.clear();

        final RectHV r = new RectHV(0, 0, 1, 1);


        draw(root, r);

        StdDraw.show();
        StdDraw.pause(250);
    }

    /**
     * All points that are inside the rectangle
     *
     * @param rect query rectangle
     * @return points inside rectangle
     */
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        final RangeIterator rangeIterator = new RangeIterator(rect, root);

        final List<Point2D> points = new ArrayList<>();

        while (rangeIterator.hasNext()) {
            points.add(rangeIterator.next());
        }

        return points;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p the point
     * @return neighbor
     */
    public Point2D nearest(final Point2D p) {
        return nearest(p, root, null, new RectHV(0, 0, 1, 1));
    }

    private static Node location(final Point2D p, final Node position) {

        if (position.point.equals(p)) {
            return position;
        }

        if (position.rightOf(p)) {
            if (position.left == null) {
                return position;
            } else {
                return location(p, position.left);
            }
        } else {
            if (position.right == null) {
                return position;
            } else {
                return location(p, position.right);
            }
        }
    }

    /**
     * Draw all points to standard draw
     */
    private static void draw(final Node node, final RectHV enclosing) {
        if (node == null) {
            return;
        }

        node.draw(enclosing);

        draw(node.left, node.lspace(enclosing));
        draw(node.right, node.rspace(enclosing));

    }

    private static Point2D nearest(final Point2D p, final Node node, final Point2D best, final RectHV space) {
        if (node == null) {
            return best;
        }

        Point2D nearest = best != null ? nearest(p, best, node.point) : node.point;

        if (Double.compare(node.component(p), node.component(node.point)) <= 0) {
            nearest = traverseLeftFirst(p, node, space, nearest);
        } else {
            nearest = traverseRightFirst(p, node, space, nearest);
        }

        return nearest;
    }

    private static Point2D traverseRightFirst(final Point2D p, final Node node, final RectHV space, final Point2D nearest) {
        return nearest(node.left, p, node.lspace(space), nearest(node.right, p, node.rspace(space), nearest));
    }

    private static Point2D traverseLeftFirst(final Point2D p, final Node node, final RectHV space, final Point2D nearest) {
        return nearest(node.right, p, node.rspace(space), nearest(node.left, p, node.lspace(space), nearest));
    }

    private static Point2D nearest(final Point2D p, final Point2D best, final Point2D point) {
        return Double.compare(point.distanceSquaredTo(p), best.distanceSquaredTo(p)) < 0 ? point : best;
    }

    private static Point2D nearest(final Node node, final Point2D p, final RectHV space, final Point2D best) {
        return nearest(p, space, best) ? nearest(p, node, best, space) : best;
    }

    private static boolean nearest(final Point2D p, final RectHV space, final Point2D best) {
        return Double.compare(space.distanceTo(p), best.distanceTo(p)) < 0;
    }

    private static final class RangeIterator implements Iterator<Point2D> {

        private final RectHV query;
        private final Stack<Node> stack = new Stack<>();
        private Point2D next;

        private RangeIterator(final RectHV query, final Node root) {
            this.query = query;

            if (root != null) {
                stack.push(root);
            }

            next = findNext();
        }

        private Point2D findNext() {

            if (stack.isEmpty()) {
                return null;
            }

            final Node n = stack.pop();

            if (n.leftOf(query) && n.right != null)
                stack.push(n.right);

            if (n.rightOf(query) && n.left != null)
                stack.push(n.left);

            if (query.contains(n.point))
                return n.point;
            else
                return findNext();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Point2D next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final Point2D point = this.next;

            this.next = findNext();

            return point;

        }
    }

    private static class Node {

        private final Point2D point;

        private Node left;
        private Node right;

        public Node(final Point2D point) {
            this.point = point;
        }

        public Point2D point() {
            return point;
        }

        public void draw(final RectHV space) {
        }

        public boolean rightOf(final RectHV r) {
            return Double.compare(component(this.point), min(r)) >= 0;
        }

        public double component(final Point2D p) {
            throw new RuntimeException("Implement in subclass");
        }

        public double min(final RectHV r) {
            throw new RuntimeException("Implement in subclass");
        }

        public boolean leftOf(final RectHV r) {
            return Double.compare(component(this.point), max(r)) <= 0;
        }

        public double max(final RectHV r) {
            throw new RuntimeException("Implement in subclass");
        }

        private void addChild(final Point2D p) {
            if (rightOf(p))
                left = child(p);
            else
                right = child(p);
        }

        private boolean rightOf(final Point2D p) {
            return Double.compare(component(this.point), component(p)) > 0;
        }

        public Node child(final Point2D p) {
            throw new RuntimeException("Implement in subclass");
        }

        public RectHV lspace(final RectHV enclosing) {
            throw new RuntimeException("Implement in subclass");
        }

        public RectHV rspace(final RectHV enclosing) {
            throw new RuntimeException("Implement in subclass");
        }
    }

    private static final class VerticalNode extends Node {
        public VerticalNode(final Point2D point) {
            super(point);
        }

        @Override
        public void draw(final RectHV space) {
            StdDraw.setPenColor(Color.BLACK);
            point().draw();
            StdDraw.setPenColor(Color.RED);

            StdDraw.line(point().x(), space.ymin(), point().x(), space.ymax());
            StdDraw.setPenColor(Color.BLACK);
        }

        public RectHV lspace(final RectHV parentSubspace) {
            return new RectHV(min(parentSubspace), parentSubspace.ymin(), component(point()), parentSubspace.ymax());
        }

        @Override
        public double min(final RectHV r) {
            return r.xmin();
        }

        @Override
        public double component(final Point2D p) {
            return p.x();
        }

        public RectHV rspace(final RectHV parentSubspace) {
            return new RectHV(component(point()), parentSubspace.ymin(), max(parentSubspace), parentSubspace.ymax());
        }

        @Override
        public double max(final RectHV r) {
            return r.xmax();
        }

        @Override
        public Node child(final Point2D p) {
            return new HorizontalNode(p);
        }
    }

    private static final class HorizontalNode extends Node {

        public HorizontalNode(final Point2D point) {
            super(point);
        }

        @Override
        public void draw(final RectHV space) {
            StdDraw.setPenColor(Color.BLACK);
            point().draw();
            StdDraw.setPenColor(Color.BLUE);

            StdDraw.line(space.xmin(), point().y(), space.xmax(), point().y());
            StdDraw.setPenColor(Color.BLACK);
        }

        public RectHV lspace(final RectHV enclosing) {
            return new RectHV(enclosing.xmin(), min(enclosing), enclosing.xmax(), point().y());
        }

        @Override
        public double min(final RectHV r) {
            return r.ymin();
        }

        public RectHV rspace(final RectHV enclosing) {
            return new RectHV(enclosing.xmin(), component(point()), enclosing.xmax(), max(enclosing));
        }

        public double component(final Point2D p) {
            return p.y();
        }

        @Override
        public double max(final RectHV r) {
            return r.ymax();
        }

        @Override
        public Node child(final Point2D p) {
            return new VerticalNode(p);
        }
    }
}
