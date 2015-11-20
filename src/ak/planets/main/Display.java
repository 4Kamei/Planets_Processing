package ak.planets.main;

import ak.planets.background.BackgroundOld;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.RenderQueue;
import ak.planets.render.Renderable;
import ak.planets.building.Connection;
import ak.planets.building.Connector;
import ak.planets.building.Node;
import ak.planets.camera.Camera;
import ak.planets.ui.clickable.ButtonAction;
import ak.planets.ui.clickable.UIButton;
import ak.planets.ui.clickable.UIContainer;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.io.IOException;

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

    private Connector connector_C;
    private Node node_C;

    private Camera camera;
    private BackgroundOld background;

    private UIContainer container;

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

        //TODO: Make new layered background work
        background = new BackgroundOld(this, camera);


        noStroke();
        Logger.log(ALL, "Game Started");

        add(background);

        container = new UIContainer(this, 1, 1, "res/texture/ui/sideUI/button.png");

        try {
            container.addComponent(new UIButton("Button1", new ButtonAction() {
                @Override
                public void exectute() {
                    Logger.log(DEBUG, "Clicked? Button1");
                }

                @Override
                public void onHover() {

                }
            },60, 30));
            container.addComponent(new UIButton("Button2", new ButtonAction() {
                @Override
                public void exectute() {
                    Logger.log(DEBUG, "Clicked? Button2");
                }

                @Override
                public void onHover() {

                }
            },60, 30));
            container.addComponent(new UIButton("Button3", new ButtonAction() {
                @Override
                public void exectute() {
                    Logger.log(DEBUG, "Clicked? Button3");
                }

                @Override
                public void onHover() {

                }
            },60, 30));
        }catch (IOException e){
            e.printStackTrace();
        }
        container.setLayout(UIContainer.VERTICAL);


        textureMode(NORMAL);
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

            //Render Static Components.
            container.render();
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
        Logger.log(DEBUG, "mousePressed" + event);
        if (gameState == PLAYING) {
            if (event.getButton() == 37){
                //Left Click
                if (container.checkClick(mouseX, mouseY))
                    return;

                Point2i mouse = camera.getRelativePosition(mouseX, mouseY);
                Node closestNode = map.search(mouse, -1);
                Logger.log(DEBUG, closestNode + " = closestNode");

                if (closestNode != null) {
                    Connector c = closestNode.getClosestConnection(mouse);
                    Logger.log(ALL, "Getting connection at %s from %s. It is %s", mouse, closestNode, c);

                    if (c == null){
                        System.out.println(mouse);
                        return;
                    }

                    if ( connector_C == null) {
                        connector_C = c;
                        node_C = closestNode;

                    } else if ( connector_C != c) {
                        Connection connection = new Connection(this, connector_C, c);
                        if (!map.isIntersecting(connection.asLine())) {
                            node_C.connect(closestNode, connection);
                            add(connection);
                        }
                        connector_C = null;

                    }
                }

            }else if (event.getButton() == 39){
                //Right Click

            }else if (event.getButton() == 3){
                //Middle Mouse
                camera.mousePressed(event);
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
