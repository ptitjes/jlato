package org.jlato.tree;

import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeList<T extends Tree> extends Tree {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T extends Tree> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T extends Tree> implements Tree.Kind {
		public Tree instantiate(SLocation location) {
			return new NodeList<T>(location);
		}
	}

	private NodeList(SLocation location) {
		super(location);
	}

	public NodeList(T... elements) {
		super(new SLocation(new SNode(kind, runOf(elements))));
	}

	public int size() {
		return location.nodeRun().treeCount();
	}

	public T get(final int index) {
		return location.nodeChild(index);
	}

	public NodeList<T> set(int index, T element) {
		return location.nodeWithChild(index, element);
	}

	public NodeList<T> append(T element) {
		LRun newRun = location.nodeRun().append(treeOf(element));
		return location.nodeWithRun(newRun);
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Iterator<STree> iterator = location.nodeRun().treeIterator();

		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) builder.append(sep);
			else first = false;

			Tree next = new SLocation(iterator.next()).facade;
			builder.append(next.toString());
		}

		builder.append(end);
		return builder.toString();
	}
}
