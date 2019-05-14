package warmup.tree;

/**
 * This is a tree of named node.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */
public class Tree extends Node {

	public static final char pathSeparator = '/';
	public static final String pathSeparatorString = "/";

	public Tree() {
		super("");
	}

	/**
	 * Finds a node corresponding to a path in the tree. If the path does not
	 * exists, throws NotFoundException
	 * 
	 * @param path of the searched node
	 * @return the node named by the given path
	 * @throws NotFoundException if the path does not exist
	 */
	public Node find(String path) throws NotFoundException {
		if (!path.contains(pathSeparatorString)) {
			throw new IllegalArgumentException("Not a tree");
		}

		String[] path_sep = path.split(pathSeparatorString);
		int i;
		Node current = this;

		for (i = 1; i < path_sep.length; i++) {
			current = current.child(path_sep[i]);
			if (current == null || !current.m_name.equals(path_sep[i])) {
				throw new NotFoundException("Incorrect path");
			}
		}

		return current;
	}

	/**
	 * Make a path in the tree, leading either to a leaf or to a node.
	 * 
	 * @throws IllegalStateException if the path should be to a leaf but it already
	 *                               exists to a node, of if the path should be to a
	 *                               node but it already exists to a leaf.
	 */
	public Node makePath(String path, boolean isLeaf) {
		if (!path.contains(pathSeparatorString)) {
			throw new IllegalArgumentException("Not a tree");
		}

		String[] path_sep = path.split(pathSeparatorString);
		int i;
		Node current = this;
		for (i = 1; i < path_sep.length; i++) {
			try {
				Node child = current.child(path_sep[i]);
				if (child != null) {
					current = child;
				} else {
					if (i == path_sep.length - 1 && isLeaf) {
						current = new Leaf(current, path_sep[i]);
					} else {
						current = new Node(current, path_sep[i]);
					}
				}
			} catch (IllegalStateException ex) {
				if (i == path_sep.length - 1) {
					if (isLeaf) {
						return current;
					} else {
						throw new IllegalStateException("Not a leaf");
					}
				} else {
					throw new IllegalStateException("Path with a leaf midway");
				}
			}
		}
		if ((isLeaf && current instanceof Leaf)
				|| (!isLeaf && current instanceof Node)) {
			return current;
		}
		throw new IllegalStateException("Wrong type");
	}

	public String toString() {
		TreePrinter p = new TreePrinter(this);
		return p.toString();
	}

}
