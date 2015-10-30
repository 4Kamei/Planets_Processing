package ak.planets.background;

import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Aleksander on 29/10/2015.
 */
public class BackgroundLayerFactory {

    private PImage texture;

    public BackgroundLayerFactory(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setPaint (new Color(255, 0, 0, 0));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        texture = new PImage(image);
    }

    public boolean addStar(int x, int y, double size) throws IOException {
        //TODO: replace with texture generator call
        PImage starTexture = new PImage(ImageIO.read(new File("res/texture/background/star.png")));
        if(x < 0 || y < 0)
            return false;
        if(size <= 0)
            return false;
        if (starTexture.width + x > texture.width)
            return false;
        if (starTexture.height + y > texture.height)
            return false;

        texture.blend(starTexture, 0, 0, starTexture.width, starTexture.height, x, y, (int) (starTexture.width*size), (int) (starTexture.width*size), PImage.ADD);
        return true;
    }

    public boolean addGalaxy(int x, int y, double size) throws IOException {
        //TODO: Replace with texture generator call
        PImage galaxyTexture = new PImage(ImageIO.read(new File("res/texture/background/galaxy.png")));
        if(x < 0 || y < 0)
            return false;
        if(size <= 0)
            return false;
        if (galaxyTexture.width + x > texture.width)
            return false;
        if (galaxyTexture.height + y > texture.height)
            return false;

        texture.blend(galaxyTexture, 0, 0, galaxyTexture.width, galaxyTexture.height, x, y, (int) (galaxyTexture.width*size), (int) (galaxyTexture.width*size), PImage.ADD);
        return true;
    }
    public PImage getTexture(){
        return texture;
    }
}
