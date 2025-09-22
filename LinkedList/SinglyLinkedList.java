// Implements a singly-linked list.


public class SinglyLinkedList<E> {
	private ListNode<E> head;
	private ListNode<E> tail;
	private int nodeCount;

	// Constructor: creates an empty list
	public SinglyLinkedList() {
		this.head = null;
		this.tail = null;
		this.nodeCount = 0;
	}

	// Constructor: creates a list that contains
	// all elements from the array values, in the same order
	public SinglyLinkedList(E[] values) {
		for (int i = 1; i < values.length; i++) {
			this.add(values[i]);
		}
	}

	public ListNode<E> getHead() {
		return head;
	}

	public ListNode<E> getTail() {
		return tail;
	}

	// Returns true if this list is empty; otherwise returns false.
	public boolean isEmpty() {
		return this.nodeCount == 0;
	}

	// Returns the number of elements in this list.
	public int size() {
		return this.nodeCount;
	}

	// Returns true if this list contains an element equal to obj;
	// otherwise returns false.
	public boolean contains(E obj) {
		ListNode<E> node = this.head;
		while (node.getNext() != null)
			if (node.getValue().equals(obj))
				return true;
			else
				node = node.getNext();

		return false;
	}

	// Returns the index of the first element in equal to obj;
	// if not found, returns -1.
	public int indexOf(E obj) {
		int i = 0;
		ListNode<E> node = this.head;
		while (node.getNext() != null) {
			if (node.getValue().equals(obj))
				return i;
			i++;
			node = node.getNext();
		}
		return -1;
	}

	// Adds obj to this collection. Returns true if successful;
	// otherwise returns false.
	public boolean add(E obj) {
		ListNode<E> node = new ListNode<E>(obj);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			this.tail.setNext(node);
			this.tail = node;
		}
		this.nodeCount++;
		return true;
	}

	// Removes the first element that is equal to obj, if any.
	// Returns true if successful; otherwise returns false.
	public boolean remove(E obj) {
		ListNode<E> node = this.head;
		ListNode<E> prev = null;
		while (node != null) {
			if (node.getValue().equals(obj)) {
				if (prev == null)
					this.head = node.getNext();
				else
					prev.setNext(node.getNext());
				if (node.equals(tail))
					tail = prev;
				nodeCount--;
				return true;
			}
			prev = node;
			node = node.getNext();
		}

		return false;
	}

	// Returns the i-th element.
	public E get(int i) {
		if (i > this.nodeCount || i < 0)
			throw new IndexOutOfBoundsException();

		int count = 0;
		for (ListNode<E> j = null; j != null; j = j.getNext()) {
			if (count == i) {
				return j.getValue();
			}
			count++;
		}
		return null;
	}

	// Replaces the i-th element with obj and returns the old value.
	public E set(int i, E obj) {

		if (i >= this.nodeCount || i < 0)
			throw new IndexOutOfBoundsException();

		int count = 0;
		ListNode<E> node = null;
		for (ListNode<E> j = this.head; j != null && count < i - 1; j = this.head.getNext()) {
			node = j;
			count++;
		}


		return (E) new Object();
	}

	// Inserts obj to become the i-th element. Increments the size
	// of the list by one.
	public void add(int i, E obj) {

	}

	// Removes the i-th element and returns its value.
	// Decrements the size of the list by one.
	public E remove(int i) {
		return (E) new Object();
	}

	// Returns a string representation of this list exactly like that for MyArrayList.
	public String toString() {
		return null;
	}


}
