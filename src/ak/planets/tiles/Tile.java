package ak.planets.tiles;

import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import ak.planets.render.RenderableEntity;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by Aleksander on 18/12/2015.
 */
public class Tile extends Renderable{

    private int tileX;
    private int tileY;
    private final int size = 8;
    private PApplet main;

    public Tile(PApplet main, int tileX, int tileY) {
        this.main = main;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX(){
        return tileX;
    }

    public int getTileY(){
        return tileY;
    }

    public int getActualX(){
        return tileX * size;
    }

    public int getActualY(){
        return tileY * size;
    }

    public boolean positionAt(int x, int y){
        if (tileX != x) return false;
        if (tileY != y) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (tileX != tile.tileX) return false;
        if (tileY != tile.tileY) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tileX;
        result = 31 * result + tileY;
        return result;
    }

    @Override
    public void setup() {

    }

    @Override
    public void render() {
        int actX = getActualX();
        int actY = getActualY();
        int actSize = size;
        main.shapeMode(PConstants.CENTER);
        main.fill(0 , 255, 0, 50);
        main.stroke(0, 127, 0, 127);
        main.rect(actX + actSize/2, actY+actSize/2, actSize, actSize);
        main.noFill();
        main.noStroke();
    }

    @Override
    public void update() {}

}
