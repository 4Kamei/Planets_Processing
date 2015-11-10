package ak.planets.ui;

import ak.planets.render.Renderable;
import javafx.geometry.BoundingBox;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by Aleksander on 10/11/2015.
 */
public class UIComponent {

    private int width, height;
    private int[] bounds;
    private int midX, midY;
    private String text;
    private ClickAction action;
    private UIContainer parent;

    public UIComponent(UIContainer parent, String text, ClickAction action, int[] bounds, int width, int height){
        this.text = text;
        this.action = action;
        this.bounds = bounds;
        for (int i = 0; i < bounds.length; ) {
            midX += bounds[i++];
            midY += bounds[i++];
        }
        midY /= 4;
        midX /= 4;
        this.parent = parent;
        this.width = width;
        this.height = height;
    }

    public void click(){
        action.exectute();
    }

    public void render(PApplet main){
        main.fill(0x300ff);
        main.beginShape();
        for (int i = 0; i < bounds.length;) {
            main.vertex(bounds[i++], bounds[i++]);
        }
        main.endShape();
        main.fill(0x3ff00);
        main.textAlign(PConstants.CENTER);
        main.text(text, midX, midY);
        main.noFill();
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
}
