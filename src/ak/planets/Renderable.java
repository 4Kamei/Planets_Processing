package ak.planets;

/**
 * Created by Aleksander on 18/10/2015.
 */
public abstract class Renderable {
    public abstract void setup();

    public abstract void render();

    public abstract void update();

    protected int renderPriority;

    public int getRenderPriority(){
        return renderPriority;
    }
}
