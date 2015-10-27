package ak.planets.camera;

import ak.planets.calculation.Point2i;
import processing.core.PApplet;
import processing.event.MouseEvent;


/**
 * Created by Aleksander on 18/10/2015.
 */
public class Camera {

    private double zoom;

    private int x, y;
    private int lastX, lastY;

    private int mouseX, mouseY;

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
        //main.translate(mouseX, mouseY);
        main.translate((float) (x), (float) (y));
        main.scale((float) zoom);
    }

    public void mousePressed(MouseEvent event){
        if(event.getButton() == 3){
            middlePressed = true;
            lastX = event.getX();
            lastY = event.getY();
        }
    }

    public void mouseReleased(MouseEvent event){
        if(event.getButton() == 3){
            middlePressed = false;
        }
    }
    public void mouseMoved(int mouseX, int mouseY){
        if(middlePressed){
            x += mouseX - lastX;
            y += mouseY - lastY;
            lastX = mouseX;
            lastY = mouseY;
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public Point2i getRelativePosition(int x, int y){
        return new Point2i((int) ((x - this.x)/zoom), (int) ((y - this.y)/zoom));
    }

    public void updateZoom(double v) {
        //this.zoom += v;
    }
}