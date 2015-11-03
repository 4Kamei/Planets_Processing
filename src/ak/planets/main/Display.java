package ak.planets.main;

import ak.planets.background.Background;
import ak.planets.background.BackgroundOld;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.RenderQueue;
import ak.planets.render.Renderable;
import ak.planets.building.Connection;
import ak.planets.building.Connector;
import ak.planets.building.Node;
import ak.planets.camera.Camera;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;

import static ak.planets.main.Display.GameState.*;
import static ak.planets.logger.Logger.LogLevel.*;
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
    private Point2i connectionPoint1;
    private Camera camera;
    private BackgroundOld background;
    public void settings() {
        size(800, 600, P2D);
        smooth();
    }

    public void setup() {
        surface.setTitle(Reference.gameName);

        //This needs to work, anytime now would be good
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/texture/icon/planet.ico"));

        gameState = PLAYING;

        camera = new Camera(this);
        map = new Map();
        queue = new RenderQueue();

        background = new BackgroundOld(this, camera);


        noStroke();
        Logger.log(ALL, "Game Started");

        add(background);
    }

    public void draw() {

        if(gameState == MAIN_MENU){
            mainMenu();
        }

        if(gameState == PLAYING || gameState == PAUSED) {
            surface.setTitle(Reference.gameName + " : " + frameRate);
            background(0);
            camera.update();
            while (queue.hasNext())
                queue.next().render();
            queue.reset();
            popMatrix();    //restore coord system
        }
    }

    public void mainMenu(){
        background(0);
    }

    public void add(Renderable n) {
        n.setup();
        if (n instanceof Node)
            map.add((Node) n);
        queue.addAndSort(n);
    }

    public void delete(Node n){
        map.remove(n);
        queue.remove(n);
    }


    public void keyPressed(KeyEvent event) {
        Logger.log(DEBUG, "keyPressed " + event.getKeyCode());
        if(gameState == PLAYING){

            switch (event.getKeyCode()) {
                case 65:
                    Node node = new Node(this, camera.getRelativePosition(mouseX, mouseY), 0.1);
                    add(node);
                    for (int i = 0;i < 40;i++){
                        node.add();
                    }
                    break;
                case 88:
                    background.toggleHidden();
                    break;
                case 147:
                    Node del_node = map.search(camera.getRelativePosition(mouseX, mouseY), -1);
                    if (del_node != null)
                        delete(del_node);
                    else
                        Logger.log(ERROR, "delNode == null");
                    break;
            }
        } else if(gameState == MAIN_MENU){
            switch (event.getKeyCode()){
                case 10:
                    gameState = PLAYING;
            }
        }
    }

    public void mouseWheel(MouseEvent event) {
        /*
        Node addSize = map.search(camera.getRelativePosition(mouseX, mouseY), 100);
        if(addSize != null)
            addSize.add();
        */
        if(mouseButton == 37){
            camera.updateZoom(event.getCount() * 0.05);
        }
    }

    public void mousePressed(MouseEvent event) {
        if (gameState == PLAYING) {
            camera.mousePressed(event);
            Logger.log(DEBUG, "mousePressed" + event);
            Point2i mouse = camera.getRelativePosition(mouseX, mouseY);
            Node closestNode = map.search(mouse, -1);
            Logger.log(DEBUG, closestNode + " = closestNode");

            if (closestNode != null) {
                Point2i point = closestNode.getClosestConnection(mouse);
                if (connectionPoint1 == null) {
                    this.connectionPoint1 = point;
                } else if (connectionPoint1 != point) {
                    Connection connection = new Connection(this, connector, c);
                    add(connection);
                    connector = null;
                }
            }
        } else if (gameState == MAIN_MENU) {

        }
    }

    public void mouseReleased(MouseEvent event) {
        if(gameState == PLAYING){
            camera.mouseReleased(event);
        }
    }


    public void mouseMoved(){
        //Needs to be called on every update to pass mouse coords into camera
        if(gameState == PLAYING){
            camera.mouseMoved(mouseX, mouseY);
        }
    }

    public void mouseDragged(){
        this.mouseMoved();
    }
}
