package ak.planets.camera;

import processing.core.PApplet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Camera {

    private double zoom;
    private int x, y;
    private PApplet main;
    private boolean middlePressed = false;

    public Camera(PApplet main) {
        this.x = 0;
        this.y = 0;
        this.zoom = 1;
        this.main = main;
    }

    public void update() {
        main.pushMatrix();
        main.translate(x, y);
        main.scale((float) zoom);
    }


}