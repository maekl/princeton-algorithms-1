import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(JUnitQuickcheck.class)
public class KdTreeTest {


    @Test
    public void newlyCreatedTreeIsEmpty() {
        Assert.assertTrue(new KdTree().isEmpty());
    }

    @Test
    public void newlyCreatedTreeHasSize0() {
        Assert.assertEquals(0, new KdTree().size());
    }

    @Test
    public void insertYieldsNonEmptyTree() {
        final KdTree t = new KdTree();

        t.insert(new Point2D(0, 0));

        Assert.assertFalse(t.isEmpty());
    }

    @Property
    public void treeContainsInsertedPoints(final List<@From(Points.class) Point2D> points) {

        final KdTree tree = new KdTree();

        for (final Point2D p : points) {
            tree.insert(p);
            Assert.assertTrue(tree.contains(p));
        }
    }

    @Property
    public void testRangeUnitSquare(final List<@From(Points.class) Point2D> points) {

        final KdTree tree = new KdTree();

        for (final Point2D p : points) {
            tree.insert(p);
        }

        final Set<Point2D> hashSet = new HashSet<>(points);

        final Set<Point2D> result = query(new RectHV(0, 0, 1, 1), tree);

        Assert.assertEquals(hashSet, result);

    }

    private Set<Point2D> query(RectHV query, final KdTree tree) {
        final Iterable<Point2D> range = tree.range(query);

        final Set<Point2D> ps = new HashSet<>(tree.size());

        for (final Point2D next : range) {
            ps.add(next);
        }
        return ps;
    }

    @Test
    public void testNN() {
        final List<Point2D> points = Arrays.asList(new Point2D(0.19361608355558024, 0.48696759834749914), new Point2D(0.26163643612348675, 0.495236520613617), new Point2D(0.9805169623236308, 0.3910363459256814));

        final KdTree tree = new KdTree();

        for (final Point2D p : points) {
            tree.insert(p);
        }

        final Point2D nearest = tree.nearest(new Point2D(0.4770769286949027, 0.2788334437519454));
        Assert.assertEquals(new Point2D(0.26163643612348675, 0.495236520613617), nearest);

    }

    @Property(maxShrinkDepth = 8000, maxShrinks = 1000000, maxShrinkTime = 100000)
    public void holdThatPointSetAndKdTreeIsSame(final List<@From(Points.class) Point2D> points) {

        final PointSET set = new PointSET();
        final KdTree tree = new KdTree();

        for (final Point2D p : points) {
            set.insert(p);
            tree.insert(p);

        }

        final double x = StdRandom.uniform(0D, 1D);
        final double y = StdRandom.uniform(0D, 1D);


        final Point2D n = new Point2D(x, y);

        final Point2D nearest = tree.nearest(n);
        final Point2D setNearest = set.nearest(n);

        Assert.assertEquals(n.toString(), setNearest, nearest);

//        tree.draw();
//
//        StdDraw.setPenColor(Color.RED);
//
//        n.draw();
//
//        StdDraw.pause(1000);
//
//        System.out.println(tree.size());
//        Assert.assertEquals("Query is " + query, query(query, set), query(query, tree));

        Assert.assertEquals(set.size(), tree.size());
    }

    private Set<Point2D> query(final RectHV query, final PointSET set) {
        final Iterable<Point2D> range = set.range(query);

        final Set<Point2D> ps = new HashSet<>(set.size());

        for (final Point2D next : range) {
            ps.add(next);
        }
        return ps;
    }
}
