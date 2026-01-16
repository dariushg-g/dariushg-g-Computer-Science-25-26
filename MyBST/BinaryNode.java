
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

	private int get_height_rec() {
		int left = this.getLeft() == null ? 0 : this.getLeft().get_height_rec();
		int right = this.getRight() == null ? 0 : this.getRight().get_height_rec();
		return Math.max(left, right) + 1;
	}

	public int getHeight() {
		return get_height_rec() - 1;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public void setLeft(BinaryNode<E> left) {
		this.left = left;
	}

	public void setRight(BinaryNode<E> right) {
		this.right = right;
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
