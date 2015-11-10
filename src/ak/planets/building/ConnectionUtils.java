package ak.planets.building;

import ak.planets.calculation.Point2d;
import ak.planets.calculation.Point2i;

import java.awt.*;

/**
 * Created by Aleksander on 08/11/2015.
 */
public class ConnectionUtils {

    /**
     * Checks if a node can connect to another one from point on it's circumference.
     *
     * @param closeNode      the first node
     * @param closeConnector connection point from first node
     * @param farConnector   the connection point from far node
     * @param farNode        the far node
     * @return true if can connect, false if can't
     */
    public static boolean canConnect(Node closeNode, Point2i closeConnector, Point2i farConnector, Node farNode) {
        Point2i closePoint = closeNode.getPoint();
        Point2i farPoint = farNode.getPoint();
        double c_cSquared = closePoint.computeDistanceSquared(closeConnector);
        double f_cSquared = closeConnector.computeDistanceSquared(farConnector);
        double f_fSquared = farConnector.computeDistanceSquared(farPoint);
        return true;
    }
}
