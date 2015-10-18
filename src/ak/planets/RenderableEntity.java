package ak.planets;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.EnumMap;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class RenderableEntity extends Renderable {

    protected PApplet main;
    protected PImage texture;
    protected int[] model;
    protected int x, y;


    public RenderableEntity(PApplet main){
        this.main = main;
    }


    public void setup(){

        model = new int[]{
                20, 20, 0, 0,
                60, 20, 1, 0,
                60, 60, 1, 1,
                20, 60, 0, 1
        };

        texture = main.loadImage("res/texture/icon/icon32.png");
    }

    public void render(){
        main.textureMode(PConstants.NORMAL);
        main.beginShape();
            main.texture(texture);
            for(int index = 0; index < model.length;)
                main.vertex(x + model[index++], y + model[index++], model[index++], model[index++]);
        main.endShape();
    }

    @Override
    public void update() {

    }

}
