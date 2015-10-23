package ak.planets.calculation;

/**
 * Created by Aleksander on 23/10/2015.
 */
public class Vector {
    private double x;
    private double y;

    public Vector(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void makePerpendicular(){
        double oldX = x;
        this.x = y;
        this.y = -oldX;
    }

    public void normalise(){
        double length = Math.sqrt(x * x + y * y);
        this.x /= length;
        this.y /= length;
    }

    public void multiply(double val){
        this.x *= val;
        this.y *= val;
    }

    public Vector add(Vector v){
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector add(Point v){
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector sub(Vector v){
        return new Vector(x - v.getX(), y - v.getY());
    }

    public Vector sub(Point v){
        return new Vector(x - v.getX(), y - v.getY());
    }


    public Point getPoint(){
        return new Point((int) x, (int) y);
    }
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    @Override
    public int hashCode() {
        //x << 5 is 32 * x,
        //x << 5 - x is 32x - x = 31x
        return Double.hashCode(x) * Double.hashCode(y);
    }

    @Override
    public String toString(){
        return this.x + ", " + this.y + " vector";
    }

}
