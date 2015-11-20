package ak.planets.ui.clickable;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aleksander on 10/11/2015.
 */
public class UIButton {

    private int width, height;
    private float[] bounds;
    private int midX, midY;
    private PImage texture;
    private ButtonAction action;
    private String label;

    public UIButton(String title, ButtonAction action, int width, int height) throws IOException {
        this.label = title;
        this.action = action;
        this.bounds = new float[]{
                0    , 0     , width/(float)height, 1,
                width, 0     , 0, 1,
                width, height, 0, 0,
                0    , height, width/(float)height, 0,
        };
        for (int i = 0; i < bounds.length; ) {
            midX += bounds[i++];
            midY += bounds[i++];
        }
        midY /= 4;
        midX /= 4;
        this.width = width;
        this.height = height;
    }

    public void loadTexture(String path) throws IOException {
        this.texture = new PImage(ImageIO.read(new File(path)));
    }

    public void onHover(){
        action.onHover();
    }
    public void click(){
        action.exectute();
    }

    public void render(PApplet main, int x, int y){
        main.textureWrap(PConstants.REPEAT);
        main.fill(0xfff300ff);
        main.beginShape();
        main.texture(texture);
            for (int i = 0; i < bounds.length;) {
                main.vertex(bounds[i++] + x, bounds[i++] + y, bounds[i++], bounds[i++]);
            }
        main.endShape();
        main.fill(0xff00ff00);
        main.textAlign(PConstants.CENTER, PConstants.CENTER);
        main.text(label, x + midX, y + midY);
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
}
