import java.util.ArrayList;

public class MyPQ<E extends Comparable<E>> implements MyPriorityQueue<E> {
    private ArrayList<E> array;

    public MyPQ() {
        this.array = new ArrayList<E>();
    }

    public void add(E obj) {
        var index = 0;
        while (index < this.array.size() && this.array.get(index).compareTo(obj) > 0)
            index++;
        this.array.add(index, obj);
    }

    public E removeMin() {
        return this.array.size() > 0 ? this.array.remove(this.array.size() - 1) : null;
    }

    public E peek() {
        return this.array.size() > 0 ? this.array.get(this.array.size() - 1) : null;
    }

    public boolean isEmpty() {
        return this.array.isEmpty();
    }

}
