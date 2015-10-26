package ak.planets;

import java.util.ArrayList;

/**
 * Created by Aleksander on 18/10/2015.
 */
//Terrible implementation of a FIFO queue, but needs sorting after element added
//TODO: THIS NEEDS TO BE A LOT FASTER THAN IT IS - PREFERABLY O(1) FOR next() and hasNext().
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
                (r2, r1) -> Integer.compare(r2.getRenderPriority(), r1.getRenderPriority()));
    }

    public void remove(Renderable r){
        queue.remove(queue.indexOf(r));
        queue.sort((r1, r2) -> -1);
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
