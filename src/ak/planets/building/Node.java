package ak.planets.building;

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
    private ArrayList<Node> connections;
    private ArrayList<Point2i> usedConnections;
    private int maxConnections;

    private Point2i position;

    private double scale;
    private final int radius = 100;

    private ArrayList<Node> attachedBuildings;

    public Node(PApplet main, Point2i position, double scale) {
        this.renderPriority = 20;
        this.position = position;
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
        if(connection)
        attachedBuildings.remove(n);
    }

    public void connect(Node n) {
        if (attachedBuildings.size() < maxConnections){
            return;
        }
        if (connections.contains(n)){
            Logger.log(Logger.LogLevel.DEBUG, "%s is already connected to %s", this, n);
            return;
        }
        connections.add(n);
        n.connect(this);

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
        main.stroke(255);
        main.beginShape();

        main.endShape(PConstants.CLOSE);
        main.noStroke();
    }

    @Override
    public void update() {
    }

    private int getPossibleConnections(int radius, int spacing) {
        return (int) Math.PI * radius * 2 / spacing;
    }

    public Point2i connectionTo(final Point2i p) {
        if (usedConnections.size() == maxConnections)
            return null;
        return position.sub(p).normalise().multiply(radius).add(p);

    }
}
