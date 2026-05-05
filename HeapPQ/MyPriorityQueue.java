
public interface MyPriorityQueue<E extends Comparable<E>> {

	// Adds obj to the Priority Queue
	public void add(E obj);

	// Removes and returns the MINIMUM element from the Priority Queue
	public E removeMin();

	// Returns the MINIMUM element from the Priority Queue without removing it
	public E peek();

	// Returns true if the priority queue is empty
	public boolean isEmpty();


}
