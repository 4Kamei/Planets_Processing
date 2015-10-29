package ak.planets.background;

import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aleksander on 29/10/2015.
 */
public class BackgroundLayer extends Renderable{

    private int elementWidth;
    private int elementHeight;
    private Point2i position;
    private Point2i cameraPosition;
    private int[] model;
    private Background background;
    private PApplet main;
    private int z;
    private HashMap<Point2i, PImage> tiles;
    private int[] positions = new int[]{
            -1, -1, 0, -1, 1, -1,
            -1,  0, 0,  0, 1,  0,
            -1,  1, 0,  1, 1,  1
    };
    private boolean render;

    public BackgroundLayer(int width, int height, int z, Point2i position, PApplet main, Background background) {
        this.elementWidth = width;
        this.elementHeight = height;
        this.z = z+1;
        this.position = position;
        this.main = main;
        this.background = background;
        this.tiles = new HashMap<>();
        cameraPosition = new Point2i(0, 0);
        Logger.log(Logger.LogLevel.DEBUG, this.toString());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ak.planets.background.BackgroundLayer{");
        sb.append("elementHeight=").append(elementHeight);
        sb.append(", elementWidth=").append(elementWidth);
        sb.append(", position=").append(position);
        sb.append(", render=").append(render);
        sb.append(", tiles=").append(tiles);
        sb.append(", z=").append(z);
        sb.append('}');
        return sb.toString();
    }

    public void updateCameraPosition(Point2i p){
        cameraPosition = p;
    }
    private void addImage(int tileX, int tileY){
        Logger.log(Logger.LogLevel.DEBUG, "Adding tile %d, %d, to render", tileX, tileY);

        Thread t = new Thread(() -> {
            BackgroundLayerFactory factory = new BackgroundLayerFactory(elementWidth, elementHeight);
            for (int x = 0; x < elementWidth; x++) {
                for (int y = 0; y < elementHeight; y++) {
                    if (Math.random() > 0.9999) {
                        try {
                            if (!factory.addStar(x, y, (Math.random()/2)))
                                //Logger.log(Logger.LogLevel.DEBUG, this + " : Star added at %d, %d", x, y);
                                Logger.log(Logger.LogLevel.ERROR, this + " : Star can't be added at %d, %d ", x, y);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            this.addImage(tileX, tileY, factory.getTexture());
        });
        t.start();
    }

    private void addImage(int tileX, int tileY, PImage image){
        stopRender();
        tiles.put(new Point2i(tileX, tileY), image);
        startRender();
    }

    private boolean isVisible(Point2i point){
        int tilePosX = cameraPosition.getX()/elementWidth;
        int tilePosY = cameraPosition.getY()/elementHeight;
        for (int i = 0; i < positions.length;) {
            if(point == new Point2i(tilePosX + positions[i++], tilePosY + positions[i++]))
                return true;
        }
        //Logger.log(Logger.LogLevel.DEBUG, point + " removed from visible list");
        return false;
    }

    private void stopRender(){
        render = false;
    }
    private void startRender(){
        render = true;
    }
    @Override
    public void setup() {
        model = new int[]{
                0           ,             0, 0, 0,
                elementWidth,             0, 1, 0,
                elementWidth, elementHeight, 1, 1,
                0           , elementHeight, 0, 1
        };

        addImage(-1, 0);
    }

    @Override
    public void render() {
        if(render) {
            ArrayList<Point2i> visibleTiles = new ArrayList<>(tiles.keySet());
            visibleTiles.removeIf(this::isVisible);
            for (Point2i visibleTile : visibleTiles) {
                cameraPosition = cameraPosition.multiply(-1).add(cameraPosition.divide(z));
                visibleTile = new Point2i(visibleTile.getX() * main.width, visibleTile.getY() * main.height);
                Logger.log(Logger.LogLevel.DEBUG, visibleTile.toString());
                main.beginShape();

                main.texture(tiles.get(visibleTile));

                for (int index = 0; index < model.length; ) {
                    main.vertex(
                            (float) (model[index++] / z + position.getX() + visibleTile.getX() + cameraPosition.getX()),
                            (float) (model[index++] / z + position.getY() + visibleTile.getY() + cameraPosition.getY()),
                            model[index++],
                            model[index++]);
                }
                main.endShape();
            }
        }
    }

    @Override
    public void update() {

    }
}
