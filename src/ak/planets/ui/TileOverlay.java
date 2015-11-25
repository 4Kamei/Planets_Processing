package ak.planets.ui;

import ak.planets.camera.Camera;
import ak.planets.main.Map;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by Aleksander on 25/11/2015.
 */
public class TileOverlay extends Renderable {

    private Map map;
    private PApplet main;
    private Camera camera;
    private boolean visible;
    private int size = 1;

    public TileOverlay(Map map, PApplet main, Camera camera) {
        this.map = map;
        this.main = main;
        this.camera = camera;
    }

    @Override
    public void setup() {

    }

    @Override
    public void render() {
        int camX = camera.getPosition().getX();
        int camY = camera.getPosition().getY();
        main.stroke(0, 255, 0, 30);
        main.strokeWeight(1);
        for (int x = camX%size; x < (main.width); x += size){
            main.line(x,0,x,main.height);
        }

        for (int y = camY%size; y < (main.height); y += size){
            main.line(0,y,main.width,y);
        }
        main.noStroke();
        main.fill(255, 0, 0, 50);

        main.rectMode(PConstants.CENTER);

        for (int x = camX%size; x < (main.width); x += size){
            for (int y = camY%size; y < (main.height); y += size) {
                if (map.isInNode(x - camX + size/2, y - camY + size/2)){
                    main.rect(x + size / 2, y + size / 2, size, size);
                }
            }
        }
        main.noFill();

    }

    @Override
    public void update() {

    }

    public boolean isVisible(){
        return visible;
    }

    public void toggleVisible(){
        visible = !visible;
    }

    public void inc(){
        size++;
    }

    public void dec(){
        size--;
    }
}
