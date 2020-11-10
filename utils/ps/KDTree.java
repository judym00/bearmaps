package bearmaps.utils.ps;

import static bearmaps.utils.ps.Point.distance;
import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;


    private class Node {
        private Point p;
        private boolean orientation;
        private Node leftChild;   //* downchild *//
        private Node rightChild; //* upchild *//

        Node(Point p, boolean orientation) {    /* constructor */
            this.p = p;
            this.orientation = orientation;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }

    }

    /** MY ADD METHOD */
    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }

        int cmp = cmp(n.p, p, orientation);  /** added point */
        if (cmp < 0) {
            n.rightChild = add(p, n.rightChild, !orientation);

        } else {
            n.leftChild = add(p, n.leftChild, !orientation);
        }
        return n;

    }


    /**
     * my COMPARE METHOD
     */
    private int cmp(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }


    /**
     * helper function for my NEAREST Method
     */
    private Node nearesthelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        Node goodSide;
        Node badSide;

        if (cmp(goal, n.p, n.orientation) < 0) { /* using the comparison method */
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }
        best = nearesthelper(goodSide, goal, best);

        /* pruning checking the bad side */
        if (n.orientation == HORIZONTAL) {
            Point x = new Point(n.p.getX(), goal.getY());
            double ok = Math.sqrt(Point.distance(goal, x));

            if (ok < Math.sqrt(distance(goal, best.p))) {
                best = nearesthelper(badSide, goal, best);
            }
        } else {
            Point y = new Point(goal.getX(), n.p.getY());
            double ok = Math.sqrt(Point.distance(goal, y));

            if (ok < Math.sqrt(distance(goal, best.p))) {
                best = nearesthelper(badSide, goal, best);
            }
        }
        return best;
    }


    @Override
    public Point nearest(double x, double y) {
        return nearesthelper(root, new Point(x, y), root).p;
    }

}
