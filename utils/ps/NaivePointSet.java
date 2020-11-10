package bearmaps.utils.ps;

import java.util.ArrayList;
import java.util.List;

//* @Source Josh Hug's Walkthrough video on Youtube */

public class NaivePointSet implements PointSet {
    private List<Point> myPoints;


    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<>(); /*create a list of points*/
        for (Point p : points) {
            myPoints.add(p);
        }
    }


    @Override
    public Point nearest(double x, double y) {
        Point best = myPoints.get(0);   /* get the 0th point */
        Point want = new Point(x, y); /* create a virtual point that corresponds to my goal */
        for (Point p : myPoints) {       /* calculate the distance from p to goal */
            double currentdistance = Point.distance(p, want);
            if (currentdistance < Point.distance(best, want)) {
                best = p;
            }

        }
        return best;

    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(1.1, 2.2);

        NaivePointSet np = new NaivePointSet(List.of(p1, p2, p3));
        Point returnn = np.nearest(3.0, 4.0);
        System.out.println(returnn.getX());
        System.out.println(returnn.getY());

    }
}
