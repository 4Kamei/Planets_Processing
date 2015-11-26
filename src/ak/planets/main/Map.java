package ak.planets.main;

import ak.planets.building.Connection;
import ak.planets.building.Node;
import ak.planets.calculation.Point2i;
import ak.planets.logger.Logger;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aleksander on 20/10/2015.
 */

public class Map {

    private HashMap<Point2i, Node> nodeMap;
    private ArrayList<Point2i> points;

    public Map() {
        this.nodeMap = new HashMap<>();
        this.points = new ArrayList<>();
    }

    public boolean add(Node n) {
        Point2i p = n.getPoint();
        if (!points.contains(p)) {
            nodeMap.put(p, n);
            points.add(p);
            Logger.log(Logger.LogLevel.DEBUG, "Added " + p + " to nodeMap, with hash " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
            return true;
        }
        return false;
    }

    public void remove(Node n) {
        Point2i p = n.getPoint();
        nodeMap.remove(p);
        while (points.contains(p))
            points.remove(p);
    }

    public Node get(Point2i p) {
        Logger.log(Logger.LogLevel.DEBUG, "Getting " + p + " from nodeMap with hashcode " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
        return nodeMap.get(p);
    }

    //TODO: FIX TO USE {@CODE final Point}
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

    public ArrayList<Node> sortByDistance(final Point2i p){
        if (nodeMap.size() == 0)
            return null;
        ArrayList<Point2i> sorted = new ArrayList<>(points);
        sorted.sort((p1, p2) -> Double.compare(p1.computeDistanceSquared(p), p2.computeDistanceSquared(p)));
        ArrayList<Node> nodeList = new ArrayList<>(sorted.size());
        sorted.stream().forEachOrdered(point -> nodeList.add(nodeMap.get(point)));
        return nodeList;
    }
    public boolean isConnectionIntersecting(Line2D line){
        return nodeMap.values().stream().anyMatch(n -> n.isIntersecting(line));
    }

    //TODO: Make this not O(n^2)
    public boolean isInNode(int x, int y){
        return sortByDistance(new Point2i(x, y)).stream().anyMatch(n -> n.getPoint().computeDistanceSquared(x, y) < n.getSize() * n.getSize());
    }
}
