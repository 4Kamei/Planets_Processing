package ak.planets.building;

import ak.planets.calculation.Point2d;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import ak.planets.render.RenderableEntity;
import ak.planets.util.TextureUtil;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class Node extends RenderableEntity {

    private ArrayList<Connection> connections;
    private ArrayList<Node> attachedBuildings;

    private int maxConnections;


    private double scale;

    private ArrayList<Point2i> tiles;

    public Node(PApplet main, Point2i p, double scale) {
        super(main);
        this.renderPriority = 20;
        this.position = p;
        this.main = main;
        this.scale = scale;
        attachedBuildings = new ArrayList<>();
        connections = new ArrayList<>();
        maxConnections = getPossibleConnections((int) (scale), 100);


    }

    public Point2i getPoint() {
        return position;
    }

    public void removeRec(Node n) {
        if (attachedBuildings.contains(n))
            attachedBuildings.remove(n);
        n.removeRec(this);
    }

    public void remove(Node n){
        attachedBuildings.remove(n);
    }

    public void connect(Node n, Connection connection) {
        if (!connections.contains(connection)){
            connections.add(connection);
            n.connect(this, connection);
        }
    }

    public double getSize(){
        return scale;
    }

    @Override
    public void setup() {
        this.texture = TextureUtil.getImage("res/texture/building/outline.png");
        Logger.log(Logger.LogLevel.DEBUG, "Texture size for connection is " + texture.width + " : " + texture.height);
        model = new float[]{
                -1, 1, 0, 1,
                1, 1, 1, 1,
                1, -1, 1, 0,
                -1, -1, 0, 0
        };
    }

    @Override
    public void render() {
        main.textureMode(PConstants.NORMAL);

        main.fill(0, 255, 0);
        //On mouse hover show outline
        /*
        is the mouse currently on the node
        */

        //main.text(String.format(Locale.CANADA_FRENCH, "%s", position), position.getX(), position.getY());
        main.noFill();

        main.beginShape();
            main.texture(texture);
            for (int index = 0; index < model.length; )
                main.vertex((int) (position.getX() + model[index++] * scale), (int) (position.getY() + model[index++] * scale), model[index++], model[index++]);
        main.endShape();
    }

    @Override
    public void update() {
    }

    private int getPossibleConnections(int radius, int spacing) {
        return (int) Math.PI * radius * 2 / spacing;
    }

    public Connector getClosestConnection(Point2i p){
        Point2d vector = new Point2d(p.sub(position));
        vector = vector.normalise();
        vector = vector.multiply(scale);
        return new Connector(vector.getX(), vector.getY(), this);
    }

    public boolean isIntersecting(Line2D line){
        return connections.stream().anyMatch(c -> c.asLine().intersectsLine(line));
    }
}
