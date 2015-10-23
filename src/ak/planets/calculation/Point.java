package ak.planets.calculation;

import processing.core.PApplet;

/**
 * Created by Aleksander on 20/10/2015.
 */
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int hashCode() {
        //x << 5 is 32 * x,
        //x << 5 - x is 32x - x = 31x
        return (x << 5 -  x) + y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Point)
            return ((Point) o).hashCode() == this.hashCode();
        return false;
    }

    public double computeDistanceSquared(int x, int y) {
        int dX = this.x - x;
        int dY = this.y - y;
        int diff = (dX * dX) + (dY * dY);
        return diff;
    }

    public double computeDistanceSquared(Point p){
        return p.computeDistanceSquared(this.x, this.y);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @Override
    public String toString(){
        return this.x + ", " + this.y;
    }

    public Point add(Point p){
        return new Point(x + p.getX(), y + p.getY());
    }

    public void vertex(PApplet display) {
        display.vertex(x, y);
    }
}
