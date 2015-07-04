package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SNodeData {

	public final ArrayList<STree> children;
	public final ArrayList<Object> attributes;

	public SNodeData(ArrayList<STree> children) {
		this(children, ArrayList.empty());
	}

	public SNodeData(ArrayList<STree> children, ArrayList<Object> attributes) {
		this.children = children;
		this.attributes = attributes;
	}

	public STree child(int index) {
		return children.get(index);
	}

	public SNodeData withChild(int index, STree value) {
		return new SNodeData(children.set(index, value), attributes);
	}

	public Object attribute(int index) {
		return attributes.get(index);
	}

	public SNodeData withAttribute(int index, Object value) {
		return new SNodeData(children, attributes.set(index, value));
	}
}
