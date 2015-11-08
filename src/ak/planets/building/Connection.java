package ak.planets.building;

import ak.planets.calculation.Point2d;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import ak.planets.calculation.Point2i;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.geom.Line2D;
import java.util.Arrays;

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
    private Point2d r1, r2, r3, r4;

    public Connection(PApplet main, Connector connector2, Connector connector1) {
        this.renderPriority = 10;
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

    public Line2D asLine(){
        return new Line2D.Float(connector1.getPoint().getX(), connector1.getPoint().getY(), connector2.getPoint().getX(), connector2.getPoint().getY());
    }

    @Override
    public void setup() {
        //Replace with call to the not yet built util class.
        //This should be generated procedurally, based on the textures of the parents nodes.
        this.texture = main.loadImage("res/texture/connection/connection.png");
        Logger.log(Logger.LogLevel.DEBUG, "Texture size for connection is " + texture.width + " : " + texture.height);

        Point2i con1 = connector1.getPoint();
        Point2i con2 = connector2.getPoint();


        Point2d con1_V = new Point2d(con1);
        Point2d con2_V = new Point2d(con2);


        Point2d calcVector = con2_V.sub(con1_V);
        calcVector = calcVector.getPerpendicular();
        calcVector = calcVector.normalise();

        Point2d linearVector = calcVector.getPerpendicular();
        con1_V = con1_V.sub(linearVector.multiply(1));
        con2_V = con2_V.add(linearVector.multiply(1));

        calcVector = calcVector.multiply(width);

        Logger.log(Logger.LogLevel.DEBUG, "NormalisedVector to P1 and P2 is " + calcVector.toString());

        r1 = con1_V.add(calcVector);
        r2 = con2_V.add(calcVector);
        r3 = con2_V.sub(calcVector);
        r4 = con1_V.sub(calcVector);

        double xLength = Math.sqrt(con1.computeDistanceSquared(con2))/texture.width;

        model = new double[]{
                r1.getX(), r1.getY(), 0, 0,
                r2.getX(), r2.getY(), xLength, 0,
                r3.getX(), r3.getY(), xLength, 1,
                r4.getX(), r4.getY(), 0, 1,
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ak.planets.building.Connection{");
        sb.append("connector1=").append(connector1);
        sb.append(", connector2=").append(connector2);
        sb.append(", length=").append(length);
        sb.append(", main=").append(main);
        sb.append(", model=").append(Arrays.toString(model));
        sb.append(", r1=").append(r1);
        sb.append(", r2=").append(r2);
        sb.append(", r3=").append(r3);
        sb.append(", r4=").append(r4);
        sb.append(", texture=").append(texture);
        sb.append(", type=").append(type);
        sb.append(", width=").append(width);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
