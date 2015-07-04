package org.jlato.tree;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;

/**
 * @author Didier Villevalois
 */
public class NodeOption<T extends Tree> extends Tree {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T extends Tree> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T extends Tree> implements Tree.Kind {
		public Tree instantiate(SLocation location) {
			return new NodeOption<T>(location);
		}
	}

	private NodeOption(SLocation location) {
		super(location);
	}

	public NodeOption() {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(new Tree[]{null})))));
	}

	public NodeOption(T element) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(element)))));
	}

	public boolean isDefined() {
		return !isNone();
	}

	public boolean isNone() {
		return location.nodeListRun().tree(0) == null;
	}

	public T get() {
		return location.nodeChild(0);
	}

	public NodeOption<T> set(T element) {
		return location.nodeWithChild(0, element);
	}

	public NodeOption<T> setNone() {
		return location.nodeWithChild(0, null);
	}
}
