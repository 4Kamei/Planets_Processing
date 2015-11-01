package ak.planets.background;

import ak.planets.calculation.Point2d;
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
    private ArrayList<Point2i> keySet;
    private int[] positions = new int[]{
        0,0
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
        this.keySet = new ArrayList<>();
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

    private void addImage(int tileX, int tileY){
        Logger.log(Logger.LogLevel.DEBUG, "Adding tile %d, %d, to %s", tileX, tileY, this);

        Thread t = new Thread(() -> {
            BackgroundLayerFactory factory = new BackgroundLayerFactory(elementWidth, elementHeight);
            for (int x = 0; x < elementWidth; x++) {
                for (int y = 0; y < elementHeight; y++) {
                    if (Math.random() > 0.9999) {
                        try {
                            if (!factory.addStar(x, y, (Math.random()/2)))
                                //Logger.log(Logger.LogLevel.DEBUG, this + " : Star added at %d, %d", x, y);
                                Logger.log(Logger.LogLevel.ERROR,"%s : Star can't be added at %d, %d ", this, x, y);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            this.addImage(tileX, tileY, factory.getTexture());
        });
        t.setName(String.format("BL textureGen for BL{%d, %d, z = %d,}", tileX, tileY, z));
        t.start();
    }

    private void addImage(int tileX, int tileY, PImage image){
        stopRender();
        tiles.put(new Point2i(tileX, tileY), image);
        keySet.add(new Point2i(tileX, tileY));
        startRender();
    }

    private boolean isVisible(Point2i calc){
        Point2i point = new Point2i(calc.getX()/elementWidth, calc.getY()/elementHeight);
        int tilePosX = cameraPosition.getX()/elementWidth;
        int tilePosY = cameraPosition.getY()/elementHeight;
        for (int i = 0; i < positions.length;) {
            Point2i check = new Point2i(tilePosX + positions[i++], tilePosY + positions[i++]);
            check = check.multiply(-1);
            if(point.equals(check))
                return false;
        }
        Logger.log(Logger.LogLevel.DEBUG, "Removed %s as it is not between -1 and +1 for x and y of Point2i{%d, %d}", point, tilePosX, tilePosY);
        return true;
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

        addImage( 0, 0);
        addImage( 0, 1);
        addImage( 0, 2);
        addImage( 0, 3);
        addImage( 0, 4);
        addImage( 0, -1);
        addImage( 0, -2);
        addImage( 0, -3);
        addImage( 0, -4);
        addImage( 1,  0);
        addImage( 2,  0);
        addImage( 3,  0);
        addImage( 4,  0);
        addImage(-1,  0);
        addImage(-2,  0);
        addImage(-3,  0);
        addImage(-4,  0);

    }

    @Override
    public void render() {
        if(render) {
            cameraPosition = background.getCameraPosition();
            ArrayList<Point2i> visibleTiles = new ArrayList<>(keySet);
            Point2i camera = cameraPosition.multiply(-1).add(cameraPosition.divide(z));

            //FIXME: Visibility of background later tile depends on finalPosition, after the camera has been added.
            //FIXME: To stop addition to every tile, divide camera first by elementWidth and Height then check.
            //FIXME:  Make sure to center the visible tile using +-1.5 elementSize
            for (Point2i visibleTile : visibleTiles) {


                visibleTile = new Point2i(visibleTile.getX() * elementWidth, visibleTile.getY() * elementHeight);
                //Logger.log(Logger.LogLevel.DEBUG, visibleTile.toString());

                Point2i finalPos = position.add(visibleTile).add(camera);

                if(isVisible(finalPos))
                    main.fill(128);
                else
                    main.fill(255);

                main.beginShape();

                main.texture(tiles.get(visibleTile));

                for (int index = 0; index < model.length; ) {
                    main.vertex(
                            (float) (model[index++] + finalPos.getX()),
                            (float) (model[index++] + finalPos.getY()),
                            model[index++],
                            model[index++]);
                }
                main.endShape();
                main.noFill();
            }
        }
    }

    @Override
    public void update() {

    }
}
