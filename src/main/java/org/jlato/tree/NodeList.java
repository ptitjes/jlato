/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree;

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;

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

		public LexicalShape shape() {
			throw new UnsupportedOperationException();
		}
	}

	private NodeList(SLocation location) {
		super(location);
	}

	public NodeList(T... elements) {
		super(new SLocation(new SNodeList(kind, new SNodeListState(treeListOf(elements)))));
	}

	public boolean isEmpty() {
		return location.nodeListChildren().isEmpty();
	}

	public int size() {
		return location.nodeListChildren().size();
	}

	public boolean contains(T element) {
		return location.nodeListChildren().indexOf(treeOf(element)) != -1;
	}

	public T get(final int index) {
		return location.nodeListChild(index);
	}

	public NodeList<T> set(int index, T element) {
		return location.nodeListWithChild(index, element);
	}

	public NodeList<T> prepend(T element) {
		final Vector<STree> newChildren = location.nodeListChildren().prepend(treeOf(element));
		return location.nodeListWithChildren(newChildren);
	}

	public NodeList<T> append(T element) {
		final Vector<STree> newChildren = location.nodeListChildren().append(treeOf(element));
		return location.nodeListWithChildren(newChildren);
	}

	public Iterator<T> iterator() {
		return new NodeIterator();
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		boolean first = true;
		for (STree tree : location.nodeListChildren()) {
			if (!first) builder.append(sep);
			else first = false;

			Tree next = new SLocation(tree).facade;
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
