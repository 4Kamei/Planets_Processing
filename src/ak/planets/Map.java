package ak.planets;

import ak.planets.building.Node;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aleksander on 20/10/2015.
 */

public class Map {

    HashMap<Point, Node> nodeMap;
    ArrayList<Point> points;

    public Map(){
        this.nodeMap = new HashMap<>();
        this.points = new ArrayList<>();
    }

    public void add(Node n){
        Point p = n.getPoint();
        nodeMap.put(p, n);
        points.add(p);
        //System.out.println("Added " +  p + " to nodeMap, with hash " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
    }

    public void remove(Node n){
        Point p = n.getPoint();
        nodeMap.remove(p);
        points.remove(p);
    }

    public Node get(Point p){
        //System.out.println("Getting " +  p + " from nodeMap with hashcode " + p.hashCode() + " \t nodeMap contains " + nodeMap.size() + " values");
        return nodeMap.get(p);
    }

    //TODO: FIX TO USE {@CODE final Point}
    public Node search(final int x, final int y, int max){
        ArrayList<Point> sorted = new ArrayList<>(points);
        sorted.sort((p1, p2) -> Double.compare(p1.computeDistanceSquared(x, y), p2.computeDistanceSquared(x, y)));
        if(sorted.get(0).computeDistanceSquared(x, y) > max*max)
            return null;
        return nodeMap.get(sorted.get(0));
    }

}
