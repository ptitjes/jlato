package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SNodeState {

	public final ArrayList<STree> children;
	public final ArrayList<Object> data;

	public SNodeState(ArrayList<STree> children) {
		this(children, ArrayList.empty());
	}

	public SNodeState(ArrayList<STree> children, ArrayList<Object> data) {
		this.children = children;
		this.data = data;
	}

	public STree child(int index) {
		return children.get(index);
	}

	public SNodeState withChild(int index, STree value) {
		return new SNodeState(children.set(index, value), data);
	}

	public Object data(int index) {
		return data.get(index);
	}

	public SNodeState withData(int index, Object value) {
		return new SNodeState(children, data.set(index, value));
	}
}
