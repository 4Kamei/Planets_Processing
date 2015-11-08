package ak.planets.building;

import ak.planets.calculation.Point2d;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.geom.Line2D;
import java.util.ArrayList;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class Node extends Renderable {

    private PApplet main;
    private PImage texture;
    private int[] model;

    private ArrayList<Connection> connections;
    private ArrayList<Node> attachedBuildings;

    private int maxConnections;

    private Point2i position;

    private double scale;
    private final int radius = 100;


    public Node(PApplet main, Point2i p, double scale) {
        this.renderPriority = 20;
        this.position = p;
        this.main = main;
        this.scale = scale;
        attachedBuildings = new ArrayList<>();
        connections = new ArrayList<>();

        maxConnections = getPossibleConnections((int) (radius * scale), 100);

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

    public void add() {
        scale += 0.01;

        maxConnections = getPossibleConnections((int) (radius * scale), 100);
    }

    @Override
    public void setup() {
        this.texture = main.loadImage("res/texture/building/outline.png");
        Logger.log(Logger.LogLevel.DEBUG, "Texture size for connection is " + texture.width + " : " + texture.height);
                model = new int[]{
                -radius, radius, 0, 1,
                radius, radius, 1, 1,
                radius, -radius, 1, 0,
                -radius, -radius, 0, 0
        };
    }

    @Override
    public void render() {
        main.textureMode(PConstants.NORMAL);

        main.beginShape();
        main.texture(texture);
        for (int index = 0; index < model.length; )
            main.vertex((int) (position.getX() + model[index++] * scale), (int) (position.getY() + model[index++] * scale), model[index++], model[index++]);
        main.endShape();

        main.noFill();

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
        vector = vector.multiply(radius*scale);
        return new Connector(vector.getX(), vector.getY(), this);

    }

    public boolean isIntersecting(Line2D line){
        return connections.stream().anyMatch(c -> c.asLine().intersectsLine(line));
    }
}
