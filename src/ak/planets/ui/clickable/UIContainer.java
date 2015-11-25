package ak.planets.ui.clickable;

import ak.planets.render.Renderable;
import processing.core.PApplet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Aleksander on 08/11/2015.
 */
public class UIContainer {

    public static final int VERTICAL = 2;
    public static final int HORIZONTAL = 1;
    private int x, y;
    private int renderX, renderY;
    private int index;
    private PApplet main;
    private int spacing;
    private boolean vertical;
    private ArrayList<UIButton> components;
    private String imagePath;

    public UIContainer(PApplet main, int x, int y, String s){
        this.imagePath = s;
        this.main = main;
        this.components = new ArrayList<>();
        this.index = 0;
        this.x = x;
        this.y = y;
        this.renderX = 0;
        this.renderY = 0;
        this.spacing = 2;
    }

    public void addComponent(UIButton b) throws IOException {
        b.loadTexture(imagePath);
        components.add(b);
    }

    private boolean renderUpdate(){
        UIButton component = components.get(index++);
        component.render(main, renderX + x, renderY + y);
        if (!vertical) {
            renderX += component.width();
        }else{
            renderY += component.height() + spacing;
        }
        return index < components.size();
    }

    private void reset(){
        renderX = 0;
        renderY = 0;
        index = 0;
    }

    public void setup() {

    }

    public void render() {
        reset();
        while (renderUpdate());

    }

    public boolean checkClick(int mouseX, int mouseY){
        int checkX = 0;
        int checkY = 0;
        for (UIButton component : components) {
            if (mouseX >= checkX && mouseX <= checkX + component.width()){
                if (mouseY >= checkY && mouseY < checkY + component.height()) {
                    component.click();
                    return true;
                }
            }
            if (!vertical)
                checkX += component.width() + spacing;
            else
                checkY += component.height() + spacing;
        }
        return false;
    }

    public void setLayout(int layout) {
        switch (layout){
            case 1 : vertical = false;
                break;
            case 2 : vertical = true;
        }
    }
}