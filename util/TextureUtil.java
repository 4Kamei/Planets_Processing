package ak.planets.util;

import ak.planets.logger.Logger;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * Created by 10akaminski on 27/11/2015.
 */
public class TextureUtil {
    private static HashMap<String, PImage> images = new HashMap<>();

    public static PImage getImage(String path) throws IOException {
        if (images.containsKey(path)) {
            Logger.log(Logger.LogLevel.ALL, "Using cached texture of image %s", path);
            return images.get(path);
        }
        PImage image = new PImage(ImageIO.read(new File(path)));
        images.put(path, image);
        return image;
    }
}
