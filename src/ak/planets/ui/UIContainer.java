package ak.planets.ui;

import ak.planets.render.Renderable;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Aleksander on 08/11/2015.
 */
public class UIContainer extends Renderable{

    private int x, y;
    private int renderX, renderY;
    private int index;
    private PApplet main;

    private ArrayList<UIComponent> components;

    public UIContainer(PApplet main, int x, int y){
        this.main = main;
        components = new ArrayList<>();
        index = 0;
        this.x = x;
        this.y = y;
        renderX = 0;
        renderY = 0;
    }

    public void addComponent(UIComponent component){
        components.add(component);
    }

    private boolean renderUpdate(){
        UIComponent component = components.get(index++);
        component.render(main);
        renderX += component.width();
        renderY += component.height();
        return index <= components.size();
    }

    private void reset(){
        index = 0;
    }

    @Override
    public void setup() {

    }

    @Override
    public void render() {
        while (renderUpdate());
    }

    @Override
    public void update() {

    }

    public void checkClick(int mouseX, int mouseY){
        int checkX = 0;
        int checkY = 0;
        for (UIComponent component : components){
            if(x + checkX>)
        }
    }
}