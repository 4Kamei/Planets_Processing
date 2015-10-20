package ak.planets.building;

import ak.planets.Point;
import ak.planets.Renderable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class Node extends Renderable {

    private PApplet main;
    private PImage texture;
    private int[] model;
    private ArrayList<Connector> connections;
    private int maxConnections;
    private Point p;

    private int x, y;
    private double scale;
    private final int radius = 100;

    private ArrayList<Node> attachedBuildings;

    public Node(PApplet main, int x, int y, double scale) {
        this.y = y;
        this.x = x;
        p = new Point(x, y);
        this.main = main;
        this.scale = scale;
        this.renderPriority = 2;
        attachedBuildings = new ArrayList<>();
        connections = new ArrayList<>();

        maxConnections = getPossibleConnections((int) (radius*scale), 100);
        updateConnectionArray(radius, maxConnections, 0);

    }

    public Point getPoint() {
        return p;
    }

    public void remove(Node n){
        if (attachedBuildings.contains(n))
            attachedBuildings.remove(n);
        n.remove(this);
    }

    public void connect(Node n){
        if (attachedBuildings.size() < maxConnections);

    }

    public void add(){
        scale += 0.01;
        maxConnections = getPossibleConnections((int) (radius*scale), 100);
        updateConnectionArray(radius, maxConnections, 0);
    }

    @Override
    public void setup() {
        this.texture = main.loadImage("res/texture/building/outline.png");
        System.out.println(texture.width + " : " + texture.height);
        model = new int[]{
                -radius,  radius, 0, 1,
                radius,  radius, 1, 1,
                radius, -radius, 1, 0,
                -radius, -radius, 0, 0
        };
    }

    @Override
    public void render() {
        main.textureMode(PConstants.NORMAL);

        main.beginShape();
        main.texture(texture);
        for(int index = 0; index < model.length;)
            main.vertex((int)(x + model[index++]*scale), (int) (y + model[index++]*scale), model[index++], model[index++]);
        main.endShape();

        main.noFill();
        main.stroke(255);
        main.beginShape();
            connections.forEach(con -> con.render(main, x, y, scale));
        main.endShape(PConstants.CLOSE);
        main.noStroke();
    }

    @Override
    public void update() {
    }

    /**
     * Calculates the position of points {@code connections} on the circle of radius {@code radius}
     * @param radius The radius of the circle
     * @param connectNum the number of connections to make
     * @param offset the offset (in radians) from the first point
     * @return
     */
    private void updateConnectionArray(int radius, int connectNum, double offset){
        //Distance between each connection, as angle
        connections = new ArrayList<>(connectNum);
        double dist = Math.PI * 2/connectNum;
        for (int n = 1; n <= connectNum; n++)
            connections.add(new Connector(Math.sin(offset+n*dist) * radius, Math.cos(offset+n*dist) * radius, this));
    }

    private int getPossibleConnections(int radius, int spacing){
        return (int) Math.PI * radius * 2/spacing;
    }


}
