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
    private int x, y;

    public Connection(PApplet main, Connector connector2, Connector connector1) {
        if (connector1.getPoint().getX() > connector2.getPoint().getX()){
            this.x = connector1.getPoint().getX();
            this.y = connector1.getPoint().getY();
        }else {
            this.x = connector2.getPoint().getX();
            this.y = connector2.getPoint().getY();
        }
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
        this.texture = main.loadImage("res/texture/connection/connection.png");
        System.out.println(texture.width + " : " + texture.height);
    }

    @Override
    public void render() {

        main.pushMatrix(); //Save coord system

        main.strokeWeight(10);
        main.stroke(255);
        main.beginShape();

        connector1.getPoint().vertex(main);
        connector2.getPoint().vertex(main);
        connector1.getPoint().vertex(main);

        main.endShape();
        main.strokeWeight(1);
        main.noStroke();

        main.popMatrix(); //Restore coord system
    }


    @Override
    public void update() {

    }
}
