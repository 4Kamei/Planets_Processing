package ak.planets;

import java.util.ArrayList;

/**
 * Created by Aleksander on 18/10/2015.
 */
//Terrible implementation of a FIFO queue, but needs sorting after element added
    //TODO: IMPROVE THIS
public class RenderQueue {
    private ArrayList<Renderable> queue;
    private int index;

    public void add(Renderable renderable){
        queue.add(renderable);
    }

    public void addAndSort(Renderable renderable){
        queue.add(renderable);
        queue.sort(
                (r1, r2) -> Integer.compare(r2.getRenderPriority(), r1.getRenderPriority()));
    }

    public RenderQueue(){
        queue = new ArrayList<>();
        index = 0;
    }

    public Renderable next(){
        return queue.get(index++);
    }

    public boolean hasNext(){
        return index < queue.size();
    }

    public void reset(){
        index = 0;
    }
}
