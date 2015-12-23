package ak.planets.util;

import ak.planets.logger.Logger;
import processing.core.PConstants;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * Created by 10akaminski on 27/11/2015.
 */
//TODO: PER-NODE COORDS
public class TextureUtil {
    private static HashMap<String, PImage> images = new HashMap<>();

    public static PImage getImage(String path) {
        if (images.containsKey(path)) {
            Logger.log(Logger.LogLevel.ALL, "Using cached texture of image %s", path);
            return images.get(path);
        }
        PImage image = null;
        try {
            BufferedImage i = ImageIO.read(new File(path));
            Logger.log(Logger.LogLevel.ALL, String.valueOf(i.getTransparency()));
            image = new PImage(i.getWidth(), i.getHeight(), PConstants.ARGB);
            for (int x = 0; x < i.getWidth(); x++) {
                for (int y = 0; y < i.getHeight(); y++) {
                    image.set(x, y, i.getRGB(x, y));
                }
            }
        } catch (IOException e) {
            Logger.log(Logger.LogLevel.ERROR, "PATH NOT FOUND %s", path);
        }
        images.put(path, image);
        return image;
    }
}
