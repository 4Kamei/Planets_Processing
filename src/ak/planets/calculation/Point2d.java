package ak.planets.calculation;

import processing.core.PApplet;

/**
 * Created by Aleksander on 20/10/2015.
 */
public class Point2d {

    private double x;
    private double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2d(Point2i point2i){
        this.x = point2i.getX();
        this.y = point2i.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2d point2d = (Point2d) o;

        if (Double.compare(point2d.x, x) != 0) return false;
        if (Double.compare(point2d.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double computeDistanceSquared(double x, double y) {
        double dX = this.x - x;
        double dY = this.y - y;
        return (dX * dX) + (dY * dY);
    }

    public double computeDistanceSquared(Point2d p){
        return p.computeDistanceSquared(this.x, this.y);
    }

    public double getX(){
        return x;
    }

    public double getY() { return y; }

    public Point2d add(Point2d p){
        return new Point2d(x + p.getX(), y + p.getY());
    }

    public Point2d sub(Point2d p) {
        return new Point2d(x - p.getX(), y - p.getY());
    }

    public Point2d multiply(double scalar){
        return new Point2d(scalar * x, scalar * y);
    }

    public Point2d divide(double scalar){
        return new Point2d(x / scalar, y / scalar);
    }

    public Point2d getPerpendicular() {
        return new Point2d(y ,-x);
    }

    public Point2d normalise(){
        double length = Math.sqrt(x * x + y * y);
        return new Point2d(x / length, y / length);
    }

    public void vertex(PApplet display) {
        display.vertex((float) x,(float) y);
    }
}

