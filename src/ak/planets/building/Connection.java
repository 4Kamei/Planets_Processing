package ak.planets.building;

import ak.planets.Renderable;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Aleksander on 19/10/2015.
 */
public class Connection extends Renderable{

    //Constants


    public static int CONNECTION_NORMAL = 1;


    private Connector connector1, connector2;
    private int type;
    private double length;
    private PImage texture;
    private PApplet main;
    private int[] model;

    public Connection(PApplet main, Connector connector2, Connector connector1) {
        this.connector2 = connector2;
        this.connector1 = connector1;
        this.length = connector1.getPoint().computeDistanceSquared(connector1.getPoint());
        this.length = Math.sqrt(length);
        this.type = CONNECTION_NORMAL;
        this.main = main;
    }

    /**
     * @return {@code Array} containing 2 {@code Connector}
     */
    public Connector[] getConnectors() {
        return new Connector[]{connector1, connector2};
    }

    @Override
    public void setup() {
        this.texture = main.loadImage("res/texture/building/outline.png");
        System.out.println(texture.width + " : " + texture.height);
        model = new int[]{
                -texture.width/2, texture.height/2, 0, 1,
                texture.width/2, texture.height/2, 1, 1,
                texture.width/2, -texture.height/2, 1, 0,
                -texture.width/2, -texture.height/2, 0, 0
        };
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }
}
