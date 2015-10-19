package ak.planets.building;

/**
 * Created by Aleksander on 19/10/2015.
 */
public class Connection {

    //Constants
    public static int CONNECTION_NORMAL = 1;


    private Connector connector1,  connector2;
    private int type;

    public Connection(Connector connector2, Connector connector1) {
        this.connector2 = connector2;
        this.connector1 = connector1;
        this.type = CONNECTION_NORMAL;
    }

    /**
     * @return {@code Array} containing 2 {@code Connector}
     */
    public Connector[] getConnectors(){
        return new Connector[]{connector1, connector2};
    }

}
