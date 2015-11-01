package ak.planets.background;

import ak.planets.calculation.Point2i;
import ak.planets.camera.Camera;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by Aleksander on 26/10/2015.
 */
public class Background extends Renderable{
    private PApplet main;
    private ArrayList<BackgroundLayer> layers;
    private boolean hidden = false;
    private Camera camera;

    public Background(PApplet main, Camera camera) {
        this.renderPriority = 30;
        layers = new ArrayList<>();
        this.main = main;
        this.camera = camera;
    }

    @Override
    public void setup() {
        BackgroundLayer layer = new BackgroundLayer(main.width/2, main.height/2, 1, new Point2i(0, 0), main, this);
        layer.setup();
        layers.add(layer);

        //Generate background 2
        //layer = new BackgroundLayer(main.width, main.height, 2, new Point2i(0, 0), main, this);
        //layer.setup();
        //layers.add(layer);
    }

    @Override
    public void render(){
        if (!hidden)
            layers.forEach(BackgroundLayer::render);
    }

    public Point2i getCameraPosition(){
        return camera.getPosition();
    }

    @Override
    public void update() {

    }

    public void toggleHidden() {
        hidden = !hidden;
    }
}
