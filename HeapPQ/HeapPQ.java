
public class HeapPQ<E extends Comparable<E>> implements MyPriorityQueue<E> {

	private E[] heap;
	private int objectCount;

	@SuppressWarnings("unchecked")
	public HeapPQ() {
		this.heap = (E[]) new Comparable[3];
		this.objectCount = 0;
	}

	// Returns the number of elements in the priority queue
	public int size() {
		return objectCount;
	}

	// DO NOT CHANGE MY JANKY TOSTRING!!!!!
	public String toString() {
		StringBuffer stringbuf = new StringBuffer("[");
		for (int i = 0; i < objectCount; i++) {
			stringbuf.append(heap[i]);
			if (i < objectCount - 1)
				stringbuf.append(", ");
		}
		stringbuf.append("]\nor alternatively,\n");

		for (int rowLength = 1, j = 0; j < objectCount; rowLength *= 2) {
			for (int i = 0; i < rowLength && j < objectCount; i++, j++) {
				stringbuf.append(heap[j] + " ");
			}
			stringbuf.append("\n");
			if (j < objectCount) {
				for (int i = 0; i < Math.min(objectCount - j, rowLength * 2); i++) {
					if (i % 2 == 0)
						stringbuf.append("/");
					else
						stringbuf.append("\\ ");
				}
				stringbuf.append("\n");
			}
		}
		return stringbuf.toString();
	}

	// Doubles the size of the heap array
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		var heap_length = this.size();
		var new_length = heap_length * 2;
		var new_heap = (E[]) new Comparable[new_length];
		for (int i = 0; i < heap_length; i++) {
			new_heap[i] = this.heap[i];
		}
		this.heap = new_heap;
	}

	// Returns the index of the "parent" of index i
	private int parent(int i) {
		return (i - 1) / 2;
	}

	// Returns the index of the *smaller child* of index i
	private int smallerChild(int i) {
		var child1 = i * 2 + 1;
		var child2 = i * 2 + 2;
		return this.heap[child1].compareTo(this.heap[child2]) > 0 ? child2 : child1;
	}

	// Swaps the contents of indices i and j
	private void swap(int i, int j) {
		var temp = this.heap[j];
		this.heap[j] = this.heap[i];
		this.heap[i] = temp;
	}

	// Bubbles the element at index i upwards until the heap properties hold again.
	private void bubbleUp(int i) {
		var current_index = i;
		while (this.heap[parent(current_index)].compareTo(this.heap[current_index]) > 0) {
			swap(parent(current_index), current_index);
			current_index = parent(current_index);
		}
	}

	// Bubbles the element at index i downwards until the heap properties hold again.
	private void bubbleDown(int i) {
		var current_index = i;
		while (true) {
			var smaller_index = smallerChild(current_index);
			if (this.heap[current_index].compareTo(this.heap[smaller_index]) > 0)
				swap(current_index, smaller_index);
			else
				return;
		}
	}

	@Override
	public void add(E obj) {
		if (this.size() == this.heap.length)
			this.increaseCapacity();
		this.heap[size()] = obj;
		bubbleUp(size());
		this.objectCount += 1;
	}

	@Override
	public E removeMin() {
		var min = this.heap[0];
		swap(0, size() - 1);
		this.heap[size()] = null;
		this.objectCount -= 1;
		bubbleDown(0);
		return min;
	}

	@Override
	public E peek() {
		return this.heap[0];
	}

	@Override
	public boolean isEmpty() {
		return this.objectCount == 0;
	}

}
