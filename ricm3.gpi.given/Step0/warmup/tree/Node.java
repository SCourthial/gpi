package warmup.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a node of a tree. Each node has a name. Therefore, each node is
 * reachable through a path. Each node may be attached an object.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Node {
	Node m_parent;
	String m_name;
	List<Node> m_children;
	Object m_attachment;

	/**
	 * @param name
	 * @throws IllegalArgumentException if the name contains the character
	 *                                  Tree.pathSeparator
	 */
	protected Node(String name) {
		if (name.contains(Tree.pathSeparatorString)) {
			throw new IllegalArgumentException("Contains" + Tree.pathSeparator);
		}
		this.m_parent = null;
		this.m_name = name;
		this.m_children = new ArrayList<Node>();
		this.m_attachment = null;
	}

	/**
	 * @param name
	 * @throws IllegalArgumentException if the name contains the character
	 *                                  Tree.pathSeparator
	 */
	public Node(Node parent, String name) {
		if (name.contains(Tree.pathSeparatorString)) {
			throw new IllegalArgumentException("Contains" + Tree.pathSeparator);
		}
		this.m_parent = parent;
		this.m_name = name;
		this.m_children = new ArrayList<Node>();
		this.m_attachment = null;
	}

	public String toString() {
		if (m_name == null)
			return "";
		return m_name;
	}

	public Node parent() {
		return m_parent;
	}

	public void attach(Object e) {
		m_attachment = e;
	}

	public Object attachment() {
		return m_attachment;
	}

	public void name(String name) {
		m_name = name;
	}

	public String name() {
		return m_name;
	}

	public String path() {
		Node current = this.m_parent;
		String path = this.m_name;
		while (current != null) {
			path = current.m_name + "/" + path;
			current = current.m_parent;
		}
		return path;
	}

	public void remove() {
		this.m_parent.m_children.remove(this);
	}

	public Iterator<Node> children() {
		Iterator<Node> itr = this.m_children.iterator();
		return itr;
	}

	public Node child(String name) {
		Iterator<Node> itr = this.children();
		while (itr.hasNext()) {
			Node current = itr.next();
			if (current.m_name.equals(name)) {
				return current;
			}
		}
		return null;
	}

}
