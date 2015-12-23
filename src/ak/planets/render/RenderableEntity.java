package ak.planets.render;

import ak.planets.calculation.Point2i;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Aleksander on 01/12/2015.
 */
public class RenderableEntity extends Renderable {


    protected Point2i position;
    protected PImage texture;
    protected PApplet main;
    protected float[] model;

    public RenderableEntity(PApplet main) {
        this.main = main;
    }

    @Override
    public void setup() {

    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }
}
