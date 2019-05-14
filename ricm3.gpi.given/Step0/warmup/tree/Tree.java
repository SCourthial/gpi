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
		String[] path_sep = path.split(pathSeparatorString);
		int i;
		Node current = this;
		for (i = 1 ; i < path_sep.length ; i++) {
			current = this.child(path_sep[i]);
			if(current == null) {
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
		throw new Error("NYI");
	}

	public String toString() {
		TreePrinter p = new TreePrinter(this);
		return p.toString();
	}

}
