package ak.planets.main;

import ak.planets.building.Node;
import ak.planets.RenderQueue;
import ak.planets.PersonEntity;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Display extends PApplet {

    public static void main(String[] args){
        PApplet.main(new String[]{"ak.planets.main.Display"});
    }

    public void settings() {
        size(800, 600, P2D);
    }

    RenderQueue queue;
    PersonEntity e1, e2;
    Node b;
    public void setup(){
        //TODO: BUILDINGS -> CONNECTIONS
        noStroke();
        queue = new RenderQueue();
        e1 = new PersonEntity(this);
        e1.setup();
        e2 = new PersonEntity(this);
        e2.setup();
        b  = new Node(this, 400, 300);
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

    public void mouseWheel(MouseEvent event) {
        b.add();
    }
}
