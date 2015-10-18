package ak.planets;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Building extends Renderable {

    private PApplet main;
    private PImage texture;
    private int[] model;
    private int x, y;
    private double scale;

    public Building(int y, int x, PApplet main) {
        this.y = y;
        this.x = x;
        this.main = main;
        scale = 1;
        this.renderPriority = 2;
    }

    public void add(){
        scale++;
    }
    @Override
    public void setup() {
        this.texture = main.loadImage("res/texture/building/outline.png");
        model = new int[]{
                -10,  10, 0, 0,
                10,  10, 1, 0,
                10, -10, 1, 1,
                -10, -10, 0, 1
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
    }

    @Override
    public void update() {

    }
}
