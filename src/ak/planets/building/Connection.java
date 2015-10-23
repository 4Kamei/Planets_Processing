package ak.planets.building;

import ak.planets.Renderable;
import ak.planets.calculation.Point;
import ak.planets.calculation.Vector;
import processing.core.PApplet;
import processing.core.PConstants;
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
    private double[] model;
    private int x, y;
    private int width;
    private Vector r1, r2, r3, r4;
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
        this.width = 10;
    }

    /**
     * @return {@code Array} containing 2 {@code Connector}
     */
    public Connector[] getConnectors() {
        return new Connector[]{connector1, connector2};
    }

    @Override
    public void setup() {
        //Replace with call to the not yet built util class.
        //This should be generated procedurally, based on the textures of the parents nodes.
        this.texture = main.loadImage("res/texture/connection/connection.png");
        System.out.println(texture.width + " : " + texture.height);

        Point con1 = connector1.getPoint();
        Point con2 = connector2.getPoint();

        Vector perpen = new Vector(con2.sub(con1));

        System.out.println(perpen);

        System.out.println(con1);
        System.out.println(con2);


        perpen.makePerpendicular();
        System.out.println(perpen + " Perpendicular ");
        perpen.normalise();
        System.out.println(perpen + " Normalised ");
        perpen.multiply(10);
        System.out.println(perpen + " After Multiplication ");

        r1 = con1.add(perpen);
        r2 = con2.add(perpen);
        r3 = con2.sub(perpen);
        r4 = con1.sub(perpen);

        System.out.println(r1 + " = r1");
        System.out.println(r2 + " = r2");
        System.out.println(r3 + " = r3");
        System.out.println(r4 + " = r4");

        double xLength = Math.sqrt(con1.computeDistanceSquared(con2))/texture.width;
        model = new double[]{
                r1.getX(), r1.getY(), 0, 1,
                r2.getX(), r2.getY(), xLength, 1,
                r3.getX(), r3.getY(), xLength, 0,
                r4.getX(), r4.getY(), 0, 0,
        };
    }

    @Override
    public void render() {


        main.beginShape();
        main.textureWrap(PConstants.REPEAT);
        main.texture(texture);
        for (int index = 0; index < model.length; index+=4) {
            main.vertex((float) model[index],(float)  model[index+1],(float)  model[index+2],(float)  model[index+3]);
        }
        main.endShape(PConstants.CLOSE);
        main.textureWrap(PConstants.NORMAL);

        /*main.fill(main.color(0, 0, 255));
        main.beginShape();
            r1.getPoint().vertex(main);
            r2.getPoint().vertex(main);
            r3.getPoint().vertex(main);
            r4.getPoint().vertex(main);
        main.endShape();
        main.noFill();
        */
    }


    @Override
    public void update() {

    }
}
