
public class BinaryNode<E extends Comparable<E>> {

	private E value;
	private BinaryNode<E> left;
	private BinaryNode<E> right;
	private BinaryNode<E> parent;
	private int height;

	public BinaryNode(E value) {
		this.value = value;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.height = 0;
	}

	public E getValue() {
		return value;
	}

	public BinaryNode<E> getLeft() {
		return left;
	}

	public BinaryNode<E> getRight() {
		return right;
	}

	public BinaryNode<E> getParent() {
		return parent;
	}

	public int getHeight() {
		return height;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public void setLeft(BinaryNode<E> left) {
		this.left = left;
		this.height = left.getHeight();
	}

	public void setRight(BinaryNode<E> right) {
		this.right = right;
		this.height = right.getHeight();
	}

	public void setParent(BinaryNode<E> parent) {
		this.parent = parent;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	public boolean isLeaf() {
		return !hasLeft() && !hasRight();
	}

	public String toString() {
		return value.toString();

	}

}
