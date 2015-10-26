package ak.planets.calculation;

import processing.core.PApplet;

/**
 * Created by Aleksander on 20/10/2015.
 */
public class Point2i {

    private int x;
    private int y;

    public Point2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2i(Point2d point2d){
        this.x = (int) point2d.getX();
        this.y = (int) point2d.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2i point2i = (Point2i) o;

        if (x != point2i.x) return false;
        if (y != point2i.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    public double computeDistanceSquared(int x, int y) {
        int dX = this.x - x;
        int dY = this.y - y;
        int diff = (dX * dX) + (dY * dY);
        return diff;
    }

    public double computeDistanceSquared(Point2i p){
        return p.computeDistanceSquared(this.x, this.y);
    }

    public int getX(){
        return x;
    }

    public int getY() { return y; }

    public Point2i add(Point2i p){
        return new Point2i(x + p.getX(), y + p.getY());
    }

    public Point2i sub(Point2i p) { return new Point2i(x - p.getX(), y - p.getY()); }

    public Point2i multiply(double scalar){
        return new Point2i((int) (scalar * x), (int) (scalar * y));
    }

    public Point2i divide(double scalar){
        return new Point2i((int) (x / scalar), (int) (y / scalar));
    }

    public Point2i getPerpendicular() {
        return new Point2i(y ,-x);
    }

    public Point2i normalise(){
        double length = Math.sqrt(x * x + y * y);
        return new Point2i((int) (x / length), (int) (y / length));
    }

    public void vertex(PApplet display) {
        display.vertex(x, y);
    }
}

