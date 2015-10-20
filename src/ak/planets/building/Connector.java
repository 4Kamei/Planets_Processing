package ak.planets.building;

import processing.core.PApplet;

/**
 * Created by Aleksander on 19/10/2015.
 */
public class Connector {

    private double x;
    private double y;
    private Node parent;
    private boolean isConnected;
    private Connection connection;

    public Connector(double x, double y, Node parent, Connection connection) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.isConnected = true;
        this.connection = connection;

    }

    public Connector(double x, double y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.isConnected = false;
    }

    public boolean canConnect(Connector c){
        return false;
    }

    @Deprecated
    public void render(PApplet main, double x, double y, double scale){
        main.ellipse((float) (this.x * scale + x), (float) (this.y * scale + y), 10, 10);
    }
}
