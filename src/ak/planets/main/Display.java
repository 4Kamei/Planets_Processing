package ak.planets.main;

import ak.planets.Building;
import ak.planets.RenderQueue;
import ak.planets.Renderable;
import ak.planets.RenderableEntity;
import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Display extends PApplet {

    public static void main(String[] args){
        PApplet.main(new String[]{"ak.planets.main.Display"});
    }

    public void settings() {
        size(800, 600, P3D);
    }

    RenderQueue queue;
    RenderableEntity e1, e2;
    Building b;
    public void setup(){

        //TODO: BUILDINGS -> CONNECTIONS
        noStroke();
        queue = new RenderQueue();
        e1 = new RenderableEntity(this);
        e1.setup();
        e2 = new RenderableEntity(this);
        e2.setup();
        b  = new Building(400, 300, this);
        b.setup();
        queue.add(e2);
        queue.add(b);
        queue.addAndSort(e1);

    }

    public void draw() {
        background(0);
        while (queue.hasNext())
            queue.next().render();
        queue.reset();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case 37:
                b.add();
        }
    }
}
