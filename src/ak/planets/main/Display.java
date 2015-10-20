package ak.planets.main;

import ak.planets.Map;
import ak.planets.Point;
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
        noSmooth();
    }


    Map map;
    RenderQueue queue;
    public void setup(){

        //TODO: BUILDINGS -> CONNECTIONS

        map = new Map();
        queue = new RenderQueue();
        addNode(new Node(this, 400, 300, 0.5));
        addNode(new Node(this, 400, 400, 0.5));
        addNode(new Node(this, 400, 200, 0.5));

        noStroke();

    }

    public void addNode(Node n){
        n.setup();
        map.add(n);
        queue.add(n);
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
                Node n = map.get(new Point(400, 300));
                n.add();
        }
    }

    public void mouseWheel(MouseEvent event) {
        Node n = map.search(mouseX, mouseY, 100);
        if(n != null)
            n.add();
    }
}
