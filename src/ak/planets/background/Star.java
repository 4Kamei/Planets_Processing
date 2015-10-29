package ak.planets.background;

import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import processing.core.PApplet;

/**
 * Created by Aleksander on 26/10/2015.
 */
public class Star extends Renderable{

    private Point2i position;
    private Point2i cameraPosition;
    private int[] model;
    private double scale;
    private BackgroundOld background;
    private PApplet main;
    private int z;

    public Star(Point2i position, BackgroundOld background, PApplet main, int z){
        this.position = position;
        this.background = background;
        this.main = main;
        this.renderPriority = 40;
        this.scale = 1;
        this.z = z;
    }

    @Override
    public void setup() {
        Logger.log(Logger.LogLevel.DEBUG, this + " is setup");
        model = new int[]{
                -10 , -10, 0, 0,
                 10 , -10, 1, 0,
                 10 ,  10, 1, 1,
                -10 ,  10, 0, 1,
        };

    }

    @Override
    public void render() {
        //Logger.log(Logger.LogLevel.DEBUG, this + " is being rendered");
        cameraPosition = cameraPosition.multiply(-1).add(cameraPosition.divide(z));
        main.beginShape();
        main.texture(background.getStarTexture());
        for (int index = 0; index < model.length; index+=4) {
            main.vertex(
                    (float) (model[index]/z + position.getX() +cameraPosition.getX()),
                    (float) (model[index+1]/z + position.getY() +cameraPosition.getY()),
                    model[index+2],
                    model[index+3]);
        }
        main.endShape();
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return "Star{" +
                "position=" + position +
                ", size=" + scale +
                ", z=" + z +
                '}';
    }

    public void setCameraPosition(Point2i cameraPosition){
        this.cameraPosition = cameraPosition;
    }
}
