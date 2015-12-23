package ak.planets.main;

import ak.planets.building.Connection;
import ak.planets.building.Node;
import ak.planets.calculation.Point2i;
import ak.planets.camera.Camera;
import ak.planets.logger.Logger;
import ak.planets.render.Renderable;
import ak.planets.render.RenderableEntity;
import ak.planets.tiles.Tile;
import ak.planets.ui.TileOverlay;
import com.sun.org.glassfish.gmbal.Description;
import processing.core.PApplet;
import sun.rmi.runtime.Log;

import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Aleksander on 20/10/2015.
 */

public class Map {

    private HashMap<Point2i, Node> nodeMap;
    private ArrayList<Point2i> points;
    private HashMap<Node, ArrayList<Tile>> assignedTiles;
    private PApplet main;

    public Map(PApplet main) {
        this.nodeMap = new HashMap<>();
        this.points = new ArrayList<>();
        this.assignedTiles = new HashMap<>();
        this.main = main;
    }

    /**
     * Adds a node to the map.
     * @param n Node to be added
     * @return returns true if successful, false if not
     */
    public boolean add(Node n) {
        Point2i p = n.getPoint();
        if (!addNode(n))
            return false;
        if (!points.contains(p)) {
            nodeMap.put(p, n);
            points.add(p);
            Logger.log(Logger.LogLevel.DEBUG, "Added " + p + " to nodeMap, with hash " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
            return true;
        }
        assignedTiles.remove(n);
        return false;
    }

    /**
     * Removes a node from the map
     * @param n the node to be removed.
     */
    public void remove(Node n) {
        Point2i p = n.getPoint();
        nodeMap.remove(p);
        while (points.contains(p))
            points.remove(p);
        assignedTiles.remove(n);
    }

    /**
     * @return Returns node at point p
     */
    public Node get(Point2i p) {
        Logger.log(Logger.LogLevel.DEBUG, "Getting " + p + " from nodeMap with hashcode " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
        return nodeMap.get(p);
    }

    /**
     * Searches around the point until a node is found.
     * @param p the point to search around
     * @param max the maximum search distance, in pixels. Note, all nodes will be searched through, regardless of max distance.
     * @return returns the closest node to the point p, or null if no node could be found
     */
    public Node search(final Point2i p, int max) {
        if (nodeMap.size() == 0)
            return null;
        ArrayList<Point2i> sorted = new ArrayList<>(points);
        sorted.sort((p1, p2) -> Double.compare(p1.computeDistanceSquared(p), p2.computeDistanceSquared(p)));
        if (max < 0)
            return nodeMap.get(sorted.get(0));
        if (sorted.get(0).computeDistanceSquared(p) > max * max)
            return null;
        return nodeMap.get(sorted.get(0));
    }

    /**
     * Returns a sorted list based on distance from point p
     * @param p the point to sort to
     * @return ArrayList of Node containing all of the nodes, closest to point p first.
     */
    public ArrayList<Node> sortByDistance(final Point2i p){
        if (nodeMap.size() == 0)
            return null;
        ArrayList<Point2i> sorted = new ArrayList<>(points);
        sorted.sort((p1, p2) -> Double.compare(p1.computeDistanceSquared(p), p2.computeDistanceSquared(p)));
        ArrayList<Node> nodeList = new ArrayList<>(sorted.size());
        sorted.stream().forEachOrdered(point -> nodeList.add(nodeMap.get(point)));
        return nodeList;
    }

    /**
     * Checks whether a connection is intersecting with any nodes.
     * @param line Line2D.
     * @return boolean, false if no intersection
     */
    public boolean isConnectionIntersecting(Line2D line){
        return nodeMap.values().stream().anyMatch(n -> n.isIntersecting(line));
    }


    /*
     *  METHODS FOR TILES
     */

    /**
     * Returns all of the tiles that are inside of the bounding box
     * @param lowerX lower X value for bounding box
     * @param lowerY lower Y value for bounding box
     * @param higherX maximum X value for bounding box
     * @param higherY maiximum Y value for bounding box
     * @return ArrayList of Node
     */
    public ArrayList<Tile> getAllUsedTiles(int lowerX, int lowerY, int higherX, int higherY){
        ArrayList<Node> nodes = new ArrayList<>();
        nodeMap.values().forEach(node -> {
            int xPos = node.getPoint().getX();
            int yPos = node.getPoint().getY();
            if (lowerX < xPos && higherX > xPos && lowerY < yPos && higherY > yPos)
                nodes.add(node);
        });
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Node node : nodes) {
            tiles.addAll(getUsedTiles(node));
        }
        return tiles;
    }

    /**
     * Returns all of the tiles used by a given node.
     * @param node the node to check.
     * @return ArrayList of Node.
     */
    public ArrayList<Tile> getUsedTiles(Node node){
        return assignedTiles.get(node);
    }

    /**
     * Adds all the tiles a node crosses to the assignedTiles list.
     * @param n the node who's tiles to add
     * @return true if succeeded.
     */
    public boolean addNode(Node n) {
        if (assignedTiles.containsKey(n))
            return false;
        ArrayList<Tile> tiles = new ArrayList<>();
        Point2i position = n.getPoint();
        for (int x = (int) (position.getX() - n.getSize()); x < position.getX() + n.getSize(); x += 8)
            for (int y = (int) (position.getY() - n.getSize()); y < position.getY() + n.getSize(); y += 8)
                tiles.add(new Tile(main, x / 8, y / 8));
        if (!tiles.stream().anyMatch(t -> assignedTiles.values().stream().anyMatch(list -> list.contains(t)))){
            assignedTiles.put(n, tiles);
            return true;
        }else{
            Logger.log(Logger.LogLevel.ALL, "Cannot add %s to map", n);
            return false;
        }
    }
    /*
     *  METHODS FOR TILES
     */

    /**
     * For all tiles do stuff, takes in a lambda.
     * @param predicate the thing to do to all nodes.
     */
    public void forAll(Consumer<? super Node> predicate){
        nodeMap.values().forEach(predicate);
    }
}
