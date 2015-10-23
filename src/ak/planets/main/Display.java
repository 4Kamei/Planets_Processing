package ak.planets.main;

import ak.planets.Map;
import ak.planets.Point;
import ak.planets.RenderQueue;
import ak.planets.building.Connection;
import ak.planets.building.Connector;
import ak.planets.building.Node;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Display extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{"ak.planets.main.Display"});
    }

    public void settings() {
        size(800, 600, P2D);
        noSmooth();
    }


    Map map;
    RenderQueue queue;

    public void setup() {

        //TODO: BUILDINGS -> CONNECTIONS

        map = new Map();
        queue = new RenderQueue();

        noStroke();

    }

    public void addNode(Node n) {
        n.setup();
        map.add(n);
        queue.add(n);
    }

    public void delete(Node n){
        map.remove(n);
        queue.remove(n);
    }

    public void draw() {
        background(0);
        while (queue.hasNext())
            queue.next().render();
        queue.reset();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println(event.getKey() + " : " + event.getKeyCode());
        switch (event.getKeyCode()) {
            case 65:
                Node node = new Node(this, new Point(mouseX, mouseY), 0.1);
                addNode(node);
                break;
            case 147:
                Node del_node = map.search(new Point(mouseX, mouseY), -1);
                if(del_node != null)
                    delete(del_node);
                else
                    System.out.println("Delnode is null");
                break;
            case 82 :
        }
    }

    public void mouseWheel(MouseEvent event) {
        Node addSize = map.search(new Point(mouseX, mouseY), 100);
        if(addSize != null)
            addSize.add();
    }

    public void mousePressed(MouseEvent event){

        System.out.println(event);
        Point mouse = new Point(mouseX, mouseY);
        Node closestNode = map.search(mouse, -1);
        System.out.println(closestNode + " = closestNode");
        if(closestNode != null){
            Connector c = closestNode.getClosestConnection(mouse);
            if(c == null)
                return;
            if(c.getPoint().computeDistanceSquared(mouse) > 200)
                return;
            c.getPoint().render(this);
            c.setConnected(true);
            System.out.println(c);
        }
    }
}
