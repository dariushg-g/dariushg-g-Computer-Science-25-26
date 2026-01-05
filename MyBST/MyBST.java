// Implements a BST with BinaryNode nodes

public class MyBST<E extends Comparable<E>> {

	private BinaryNode<E> root; // holds the root of this BST

	// Constructor: creates an empty BST.
	public MyBST() {
		root = null;
	}

	public BinaryNode<E> getRoot() {
		return root;
	}

	public int getHeight() {
		return root.getHeight();
	}

	// Returns true if this BST contains value; otherwise returns false.
	public boolean contains(E value) {
		var curr = this.root;

		while (curr != null) {
			if (curr.getValue().equals(value))
				return true;

			if (value.compareTo(curr.getValue()) > 0) {
				curr = curr.getRight();
			} else {
				curr = curr.getLeft();
			}
		}

		return false;
	}

	// Adds value to this BST, unless this tree already holds value.
	// Returns true if value has been added; otherwise returns false.
	public boolean add(E value) {
		var curr = this.root;

		while (!value.equals(curr.getValue())) {
			if (value.compareTo(curr.getValue()) > 0) {
				if (!curr.hasRight()) {
					curr.setRight(new BinaryNode<>(value));
					return true;
				}
				curr = curr.getRight();
			} else {
				if (!curr.hasLeft()) {
					curr.setLeft(new BinaryNode<>(value));
					return true;
				}
				curr = curr.getLeft();
			}
		}

		return false;
	}

	// Removes value from this BST. Returns true if value has been
	// found and removed; otherwise returns false.
	// If removing a node with two children: replace it with the
	// largest node in the right subtree
	public boolean remove(E value) {
		var curr = this.root;

		while (curr != null) {
			if (curr.getValue().equals(value)) {
				var right_node_val = curr.getRight().getValue();
				curr.setValue(right_node_val);
				curr.setLeft(null);
				curr.setRight(null);
				return true;
			}

			if (value.compareTo(curr.getValue()) > 0) {
				curr = curr.getRight();
			} else {
				curr = curr.getLeft();
			}
		}

		return false;
	}

	// Returns the minimum in the tree
	public E min() {
		var curr = this.root;
		while (curr.getLeft() != null) {
			curr = curr.getLeft();
		}
		return curr.getValue();
	}

	// Returns the maximum in the tree.
	public E max() {
		var curr = this.root;
		while (curr.getRight() != null) {
			curr = curr.getRight();
		}
		return curr.getValue();
	}

	// Returns a bracket-surrounded, comma separated list of the contents of the nodes, in order
	// e.g. [Apple, Cranberry, Durian, Mango]
	public String toString() {
		return "";
	}

	private String to_string_helper() {
		
		return "";
	}

}
