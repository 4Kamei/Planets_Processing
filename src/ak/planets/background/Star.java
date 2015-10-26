package ak.planets.background;

import ak.planets.calculation.Point2d;
import ak.planets.render.Renderable;
import processing.core.PApplet;

/**
 * Created by Aleksander on 26/10/2015.
 */
public class Star extends Renderable{

    private Point2d position;
    private float[] model;
    private double scale;
    private Background background;
    private PApplet main;
    public Star(Point2d position, Background background, PApplet main){
        this.position = position;
        this.background = background;
        this.main = main;
        this.renderPriority = 40;
        this.scale = 1;
    }

    @Override
    public void setup() {
        float size = 10;
        model = new float[]{
                (float) (position.getX() - size), (float) (position.getY() - size), 0, 0,
                (float) (position.getX() + size), (float) (position.getY() - size), 1, 0,
                (float) (position.getX() + size), (float) (position.getY() + size), 1, 1,
                (float) (position.getX() - size), (float) (position.getY() + size), 0, 1,
        };

    }

    @Override
    public void render() {
        main.beginShape();
        main.texture(background.getStarTexture());
        for (int index = 0; index < model.length; )
            main.vertex(model[index++], model[index++], model[index++], model[index++]);
        main.endShape();
    }

    @Override
    public void update() {

    }
}
