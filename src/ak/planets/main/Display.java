package ak.planets.main;

import ak.planets.Map;
import ak.planets.Reference;
import ak.planets.calculation.Point;
import ak.planets.RenderQueue;
import ak.planets.Renderable;
import ak.planets.building.Connection;
import ak.planets.building.Connector;
import ak.planets.building.Node;
import ak.planets.camera.Camera;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import static ak.planets.main.Display.GameState.*;

/**
 * Created by Aleksander on 18/10/2015.
 */
public class Display extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{"ak.planets.main.Display"});
    }

    enum GameState {
        MAIN_MENU,
        PLAYING,
        PAUSED,
    }

    private GameState gameState;

    private Map map;
    private RenderQueue queue;
    private Connector connector;
    private Camera camera;

    private boolean goingUp, goingDown, goingLeft, goingRight;


    public void settings() {
        size(800, 600, P2D);
        smooth();
    }

    public void setup() {
        surface.setTitle(Reference.gameName);
        gameState = PLAYING;

        camera = new Camera(this);

        map = new Map();
        queue = new RenderQueue();

        noStroke();
    }

    public void draw() {
        update();

        if(gameState == MAIN_MENU){
            mainMenu();
        }

        if(gameState == PLAYING || gameState == PAUSED) {
            background(0);
            while (queue.hasNext())
                queue.next().render();
            queue.reset();
        }
    }

    public void mainMenu(){
        background(0);

    }

    public void update(){
        if(gameState == PLAYING){

        }
    }

    
    public void add(Renderable n) {

        n.setup();
        if (n instanceof Node)
            map.add((Node) n);
        if (n instanceof Connection){
            Connection add_C = (Connection) n;
            connector.connect(add_C);
        }
        queue.addAndSort(n);
        System.out.println("added " + n + " to renderQueue");
    }

    public void delete(Node n){
        map.remove(n);
        queue.remove(n);
    }


    public void keyPressed(KeyEvent event) {
        System.out.println(event.getKey() + " : " + event.getKeyCode());
        if(gameState == PLAYING){

            switch (event.getKeyCode()) {
                case 65:
                    Node node = new Node(this, new Point(mouseX, mouseY), 0.1);
                    add(node);
                    break;
                case 147:
                    Node del_node = map.search(new Point(mouseX, mouseY), -1);
                    if (del_node != null)
                        delete(del_node);
                    else
                        System.out.println("Delnode is null");
                    break;
                case 37:
                    goingLeft = true;
                    break;
                case 38:
                    goingUp = true;
                    break;
                case 39:
                    goingRight = true;
                    break;
                case 40:
                    goingDown = true;
                    break;
            }
        } else if(gameState == MAIN_MENU){
            switch (event.getKeyCode()){
                case 10:
                    gameState = PLAYING;
            }
        }
    }
    public void keyReleased(KeyEvent event){
        if(gameState == PLAYING){
            switch (event.getKeyCode()){
                case 37:
                    goingLeft = false;
                    break;
                case 38:
                    goingUp = false;
                    break;
                case 39:
                    goingRight = false;
                    break;
                case 40:
                    goingDown = false;
                    break;
            }
        }
    }


    public void mouseWheel(MouseEvent event) {
        Node addSize = map.search(new Point(mouseX, mouseY), 100);
        if(addSize != null)
            addSize.add();
    }

    public void mousePressed(MouseEvent event) {
        if (gameState == PLAYING) {
            System.out.println(event);
            Point mouse = new Point(mouseX, mouseY);
            Node closestNode = map.search(mouse, -1);
            System.out.println(closestNode + " = closestNode");
            if (closestNode != null) {
                Connector c = closestNode.getClosestConnection(mouse);
                if (c == null)
                    return;
                if (c.getPoint().computeDistanceSquared(mouse) > 200)
                    return;

                if (connector == null) {
                    connector = c;
                } else if (connector != c) {
                    Connection connection = new Connection(this, connector, c);
                    add(connection);
                    connector = null;
                }
            }
        } else if (gameState == MAIN_MENU) {

        }
    }
}
