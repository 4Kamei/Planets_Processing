package ak.planets.ui.placement;

import ak.planets.building.Node;
import ak.planets.calculation.Point2i;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Created by Aleksander on 21/11/2015.
 */
public class NodeShadow extends PlaceUtil {

    private int[] model;
    private PImage texture;
    private double scale;

    public NodeShadow(PApplet main) {
        super(main);
        int radius = 100;
        model = new int[]{
                -radius, radius, 0, 1,
                radius, radius, 1, 1,
                radius, -radius, 1, 0,
                -radius, -radius, 0, 0
        };
        this.texture = main.loadImage("res/texture/building/outline.png");

        scale = 0.2;
    }


    @Override
    protected void draw(){
        main.textureMode(PConstants.NORMAL);

        main.beginShape();
            main.texture(texture);
            main.tint(255, 140);
            for (int index = 0; index < model.length; )
                main.vertex((int) (x + model[index++] * scale), (int) (y + model[index++] * scale), model[index++], model[index++]);
            main.endShape();
        main.noFill();
        main.tint(255, 255);
    }

    @Override
    public void scroll(int direction){
        scale += 0.01 * direction;
    }

    @Override
    public void onClick(){
        r = new Node(main, new Point2i(x, y), scale);
    }

}
