package org.jlato.tree;

import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.STree;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.stmt.ExplicitConstructorInvocationStmt;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeList<T extends Tree> extends Tree implements Iterable<T> {

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
		super(new SLocation(new SNodeList(kind, runOf(elements))));
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return location.nodeListRun().treeCount();
	}

	public boolean contains(T element) {
		return location.nodeListRun().contains(treeOf(element));
	}

	public T get(final int index) {
		return location.nodeListChild(index);
	}

	public NodeList<T> set(int index, T element) {
		return location.nodeListWithChild(index, element);
	}

	public NodeList<T> prepend(T element) {
		LRun newRun = location.nodeListRun().prepend(treeOf(element));
		return location.nodeListWithRun(newRun);
	}

	public NodeList<T> append(T element) {
		LRun newRun = location.nodeListRun().append(treeOf(element));
		return location.nodeListWithRun(newRun);
	}

	public Iterator<T> iterator() {
		return new NodeIterator();
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Iterator<STree> iterator = location.nodeListRun().treeIterator();

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

	private class NodeIterator implements Iterator<T> {

		private int index = 0;

		public boolean hasNext() {
			return index < size();
		}

		public T next() {
			final T next = get(index);
			index++;
			return next;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
