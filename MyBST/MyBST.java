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
		if (this.root == null) {
			this.root = new BinaryNode<>(value);
			return true;
		}
		var curr = this.root;

		while (true) {
			if (curr.getValue().compareTo(value) < 0) {
				if (!curr.hasRight()) {
					var new_node = new BinaryNode<>(value);
					curr.setRight(new_node);
					new_node.setParent(curr);
					new_node.setHeight(0);
					return true;
				}
				curr = curr.getRight();
				continue;
			}
			if (curr.getValue().compareTo(value) > 0) {
				if (!curr.hasLeft()) {
					var new_node = new BinaryNode<>(value);
					curr.setLeft(new_node);
					new_node.setParent(curr);
					new_node.setHeight(0);
					return true;
				}
				curr = curr.getLeft();
				continue;
			}
			return false;
		}
	}

	// Removes value from this BST. Returns true if value has been
	// found and removed; otherwise returns false.
	// If removing a node with two children: replace it with the
	// largest node in the right subtree
	public boolean remove(E value) {
		var curr = this.root;
		while (curr != null) {
			int cmp = value.compareTo(curr.getValue());
			if (cmp < 0)
				curr = curr.getLeft();
			else if (cmp > 0)
				curr = curr.getRight();
			else
				break;
		}
		
		if (curr == null)
			return false;

		if (curr.getLeft() == null || curr.getRight() == null) {
			BinaryNode<E> child = (curr.getLeft() != null) ? curr.getLeft() : curr.getRight();
			replace_node(curr, child);
			return true;
		}

		BinaryNode<E> to_replace = curr.getLeft();
		while (to_replace.getRight() != null)
			to_replace = to_replace.getRight();

		curr.setValue(to_replace.getValue());

		replace_node(to_replace, to_replace.getLeft());
		return true;
	}

	private void replace_node(BinaryNode<E> replaced, BinaryNode<E> node) {
		if (replaced.equals(this.root)) {
			this.root = node;
			if (node != null)
				node.setParent(null);
			return;
		}
		var cmp = replaced.getValue().compareTo(replaced.getParent().getValue());
		if (cmp > 0) {
			replaced.getParent().setRight(node);
		} else {
			replaced.getParent().setLeft(node);
		}
		if (node != null)
			node.setParent(replaced.getParent());
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
		var builder = new StringBuilder();
		builder.append("[");
		to_string_helper(root, builder);
		return builder.delete(builder.length() - 2, builder.length()).toString() + "]";
	}

	private void to_string_helper(BinaryNode<E> node, StringBuilder builder) {
		if (node == null)
			return;

		to_string_helper(node.getLeft(), builder);
		builder.append(node.getValue().toString() + ", ");
		to_string_helper(node.getRight(), builder);
	}
}
