package ak.planets.building;

import ak.planets.calculation.Point2d;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.ArrayList;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class Node extends Renderable {

    private PApplet main;
    private PImage texture;
    private int[] model;
    private ArrayList<Connector> connections;
    private int maxConnections;

    private Point2i p;

    private double scale;
    private final int radius = 100;

    private ArrayList<Node> attachedBuildings;

    public Node(PApplet main, Point2i p, double scale) {
        this.renderPriority = 20;
        this.p = p;
        this.main = main;
        this.scale = scale;
        attachedBuildings = new ArrayList<>();
        connections = new ArrayList<>();

        maxConnections = getPossibleConnections((int) (radius * scale), 100);

    }

    public Point2i getPoint() {
        return p;
    }

    public void removeRec(Node n) {
        if (attachedBuildings.contains(n))
            attachedBuildings.remove(n);
        n.removeRec(this);
    }

    public void remove(Node n){
        attachedBuildings.remove(n);
    }

    public void connect(Node n) {
        if (attachedBuildings.size() < maxConnections) ;
    }

    private void connect(Node n, Connection connection){

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
            main.vertex((int) (p.getX() + model[index++] * scale), (int) (p.getY() + model[index++] * scale), model[index++], model[index++]);
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

    public Point2i getClosestConnection(Point2i point){
        Point2d vector = new Point2d(this.p.sub(point));
        vector = vector.normalise();
        vector = vector.multiply(radius);
        return new Point2i(vector);
    }
}
