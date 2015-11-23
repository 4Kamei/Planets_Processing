package ak.planets.ui.placement;

import ak.planets.main.Display;
import ak.planets.render.Renderable;
import processing.core.PApplet;

/**
 * Created by Aleksander on 21/11/2015.
 */
public class PlaceUtil {

    protected PApplet main;
    protected int x, y;
    private boolean visible;
    protected Renderable r;
    protected boolean destroy;

    public PlaceUtil(PApplet main){
        this.main = main;
        this.visible = true;
    }

    public void render() throws NullPointerException{
        if (visible)
            this.draw();
    }

    protected void draw() {

    }

    //Should be called on 'mouse moved'
    public void updatePosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean shouldUpdate(){
        return visible;
    }

    public void scroll(int direction){
    }

    public void onClick(){
    }

    public boolean ready(){
        return r != null;
    }

    public Renderable fetchObject(){
        return r;
    }

    public void destroy(){
        destroy = true;
    }
}
