package warmup.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import warmup.tree.Node;

/**
 * This is a container within a tree of containers and components. A container
 * is a component that has children components. The children are managed as an
 * ordered set. Children components are painted on top of their parent
 * container.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Container extends Component {

	protected List<Component> m_children;
	
	Container() {
		super();
		this.m_children = new ArrayList<Component>();
	}

	public Container(Container parent) {
		super(parent);
		this.m_children = new ArrayList<Component>();
	}

	/**
	 * @return the number of components that are children to this container
	 */
	public int childrenCount() {
		int count = 0;
		Iterator<Component> itr = this.m_children.iterator();
		while(itr.hasNext()) {
			count++;
			itr.next();
		}
		return count;
	}

	/**
	 * @return the component indexed by the given index.
	 */
	public Component childrenAt(int i) {
		return this.m_children.get(i);
	}

	/**
	 * Select the component, on top, at the given location. The location is given in
	 * local coordinates. Reminder: children are above their parent.
	 * 
	 * @param x
	 * @param y
	 * @return this selected component
	 */
	public Component select(int x, int y) {
		if (this.inside(x,  y)) {
			Iterator<Component> itr = this.m_children.iterator();
			while(itr.hasNext()) {
				Component current = itr.next();
				if (current.inside(x, y)) {
					return current.select(x, y);
				}
			}
			return this;
		}
		return null;
	}

}
