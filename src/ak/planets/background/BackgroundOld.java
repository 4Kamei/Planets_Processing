package ak.planets.background;

import ak.planets.calculation.Point2d;
import ak.planets.calculation.Point2i;
import ak.planets.camera.Camera;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by Aleksander on 26/10/2015.
 */
public class BackgroundOld extends Renderable{
    private PImage starTexture;
    private PApplet main;
    private ArrayList<Star> stars;
    private boolean hidden = false;
    private Camera camera;

    public BackgroundOld(PApplet main, Camera camera) {
        this.renderPriority = 30;
        stars = new ArrayList<>();
        this.main = main;
        this.starTexture = main.loadImage("res/texture/background/star.png");
        this.camera = camera;
    }

    public void addStar(Point2i point2i, int z){
        Star s = new Star(point2i , this, main, z);
        s.setup();
        stars.add(s);
    }
    public PImage getStarTexture(){
        return starTexture;
    }
    //TODO: GENERATE MAP + USE main.cameraX + main.cameraY for the parallax

    @Override
    public void setup() {
        for (int x = -main.width; x < main.width*2; x++) {
            for (int y = -main.height; y < main.height*2; y++) {
                if (Math.random() > 0.9999)
                    addStar(new Point2i(x, y), (int) (Math.random() * 4)+3);
            }
        }

    }

    @Override
    public void render(){
        stars.forEach(star -> star.setCameraPosition(camera.getPosition()));
        if (!hidden)
            stars.forEach(Star::render);
    }

    @Override
    public void update() {

    }

    public void toggleHidden() {
        hidden = !hidden;
    }
}
