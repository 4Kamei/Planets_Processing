package ak.planets.building;

import ak.planets.calculation.Point2i;
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
        this.renderPriority = 2;
        attachedBuildings = new ArrayList<>();
        connections = new ArrayList<>();

        maxConnections = getPossibleConnections((int) (radius * scale), 100);
        updateConnectionArray(radius, maxConnections, 0);

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

    public void add() {
        scale += 0.01;

        maxConnections = getPossibleConnections((int) (radius * scale), 100);
        updateConnectionArray(radius, maxConnections, 0);
    }

    @Override
    public void setup() {
        this.texture = main.loadImage("res/texture/building/outline.png");
        System.out.println(texture.width + " : " + texture.height);
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
        for(Connector con : connections){
            if(con.isConnected())
                main.stroke(main.color(255, 0, 0));
            else
                main.stroke(main.color(0, 255, 0));
            con.render(main, p.getX(), p.getY(), scale);
        }
        main.endShape(PConstants.CLOSE);
        main.noStroke();
    }

    @Override
    public void update() {
    }

    /**
     * Calculates the position of points {@code connections} on the circle of radius {@code radius}
     *
     * @param radius     The radius of the circle
     * @param connectNum the number of connections to make
     * @param offset     the offset (in radians) from the first point
     * @return
     */
    private void updateConnectionArray(int radius, int connectNum, double offset) {
        //Distance between each connection, as angle
        connections = new ArrayList<>(connectNum);
        double readRad = radius * scale;
        double dist = Math.PI * 2 / connectNum;
        for (int n = 1; n <= connectNum; n++)
            connections.add(new Connector(Math.sin(offset + n * dist) * readRad, Math.cos(offset + n * dist) * readRad, this));
    }

    private int getPossibleConnections(int radius, int spacing) {
        return (int) Math.PI * radius * 2 / spacing;
    }

    public Connector getClosestConnection(final Point2i p){


        ArrayList<Connector> con = new ArrayList<>(connections);
        con.removeIf(Connector::isConnected);
        if (con.size() == 0)
            return null;

        con.sort((c1, c2) -> Double.compare(c1.getPoint().computeDistanceSquared(p), c2.getPoint().computeDistanceSquared(p)));

        return con.get(0);
    }
}
