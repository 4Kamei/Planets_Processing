package ak.planets.building;

import ak.planets.calculation.Point;
import processing.core.PApplet;

/**
 * Created by Aleksander on 19/10/2015.
 */
public class Connector {

    private double x;
    private double y;
    private Node parent;
    private boolean connected;
    private Connection connection;

    public Connector(double x, double y, Node parent, Connection connection) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.connected = true;
        this.connection = connection;

    }

    public Connector(double x, double y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.connected = false;
    }

    public void connect(Connection connection){
        if(!this.connected) {
            this.connection = connection;
            this.connected = true;
            for (Connector connector : connection.getConnectors()) {
                connector.connect(connection);
            }
        }
    }

    /**
     * Handling of intersection of buildings.
     * @param c
     * @return
     */
    public boolean canConnect(Connector c) {
        return false;
    }

    public Point getPoint(){
        return new Point((int) x, (int) y).add(parent.getPoint());
    }


    @Deprecated
    public void render(PApplet main, double x, double y, double scale) {
        main.ellipse((float) (this.x + x), (float) (this.y + y), 10, 10);
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public String toString(){
        return "[" + x + ", " + y + ", " + connected + "]";
    }

    @Deprecated
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
