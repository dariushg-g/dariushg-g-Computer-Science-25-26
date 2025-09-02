/*
 * See ArrayList documentation here:
 * http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
 */

/*
 * Your indexed functions should throw IndexOutOfBoundsException if index is invalid!
 */


public class MyArrayList<E> {

	/* Internal Object counter */
	protected int objectCount;

	/* Internal Object array */
	protected E[] internalArray;

	/* Constructor: Create it with whatever capacity you want? */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		this.internalArray = (E[]) new Object[100];
	}

	/* Constructor with initial capacity */
	@SuppressWarnings("unchecked")
	public MyArrayList(int initialCapacity) {
		this.internalArray = (E[]) new Object[initialCapacity];
	}

	/* Return the number of active slots in the array list */
	public int size() {
		return this.objectCount;
	}

	/* Are there zero objects in the array list? */
	public boolean isEmpty() {
		return this.objectCount == 0;
	}

	/* Get the index-th object in the list. */
	public E get(int index) {
		if (index >= this.objectCount)
			throw new IndexOutOfBoundsException();
		return this.internalArray[index];
	}

	/* Replace the object at index with obj. returns object that was replaced. */
	public E set(int index, E obj) {
		if (index >= this.objectCount)
			throw new IndexOutOfBoundsException();
		E temp = this.internalArray[index];
		this.internalArray[index] = obj;
		return temp;
	}

	/*
	 * Returns true if this list contains an element equal to obj; otherwise returns false.
	 */
	public boolean contains(E obj) {
		for (E val : this.internalArray) {
			if (val.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	/* Insert an object at index */
	@SuppressWarnings("unchecked")
	public void add(int index, E obj) {
		if (index > this.internalArray.length)
			throw new IndexOutOfBoundsException();

		if (this.internalArray.length > this.objectCount) {
			for (int i = this.objectCount - 1; i >= index; i--) {
				this.internalArray[i + 1] = this.internalArray[i];
			}
			this.internalArray[index] = obj;
			this.objectCount++;
		} else {
			E[] arr = (E[]) new Object[this.internalArray.length + 1];
			for (int i = 0; i < this.internalArray.length; i++) {
				arr[i] = this.internalArray[i];
			}
			this.internalArray = arr;
			this.add(index, obj);
		}
	}

	/* Add an object to the end of the list; returns true */
	// @SuppressWarnings("unchecked")
	public boolean add(E obj) {
		if (this.internalArray.length > this.objectCount) {
			this.internalArray[this.objectCount++] = obj;
			return true;
		} else {
			add(this.objectCount, obj);
			return true;
		}
	}

	/* Remove the object at index and shift. Returns removed object. */
	public E remove(int index) {
		if (index >= this.objectCount)
			throw new IndexOutOfBoundsException();

		E ret = this.internalArray[index];

		for (int i = index; i < objectCount - 1; i++) {
			System.out.println(this.internalArray[i]);
			this.internalArray[i] = this.internalArray[i + 1];
		}

		this.internalArray[objectCount - 1] = null;
		this.objectCount--;

		return ret;
	}

	/*
	 * Removes the first occurrence of the specified element from this list, if it is present. If
	 * the list does not contain the element, it is unchanged. More formally, removes the element
	 * with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))) (if such an
	 * element exists). Returns true if this list contained the specified element (or equivalently,
	 * if this list changed as a result of the call).
	 */
	public boolean remove(E obj) {
		int index = 0;
		for (int i = 0; i < objectCount; i++)
			if (obj.equals(this.internalArray[i])) {
				index = i;
				break;
			}

		if (remove(index) == null)
			return false;
		return true;
	}


	/*
	 * For testing; your string should output as "[X, X, X, X, ...]" where X, X, X, X, ... are the
	 * elements in the ArrayList. If the array is empty, it should return "[]". If there is one
	 * element, "[X]", etc. Elements are separated by a comma and a space.
	 */
	public String toString() {
		String str = "[";
		for (int i = 0; i < this.objectCount; i++) {
			str += this.internalArray[i] == null ? "null" : this.internalArray[i].toString();
			if (i + 1 != this.objectCount) {
				str += ", ";
			}
		}
		return str + "]";
	}

}
