package ak.planets.background;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Aleksander on 26/10/2015.
 */
public class Background {
    private PImage starTexture;
    private PApplet main;

    public Background(PApplet main) {
        this.main = main;
        this.starTexture = main.loadImage("res/texture/background/star.png");
    }

    public PImage getStarTexture(){
        return starTexture;
    }
    //TODO: GENERATE MAP + USE main.cameraX + main.cameraY for the parallax (implement those too, maybe?!)

}
