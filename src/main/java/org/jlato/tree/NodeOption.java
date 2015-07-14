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

import com.github.andrewoma.dexx.collection.IndexedLists;
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeOption<T extends Tree> extends Tree implements Iterable<T> {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T extends Tree> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T extends Tree> implements Tree.Kind {
		public Tree instantiate(SLocation location) {
			return new NodeOption<T>(location);
		}

		public LexicalShape shape() {
			throw new UnsupportedOperationException();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> of(T tree) {
		return tree == null ? NodeOption.<T>none() : some(tree);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> none() {
		return new NodeOption<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> some(T tree) {
		return new NodeOption<T>(tree);
	}

	private NodeOption(SLocation location) {
		super(location);
	}

	public NodeOption() {
		super(new SLocation(new STree(kind, new SNodeOptionState(treeOf(null)))));
	}

	public NodeOption(T element) {
		super(new SLocation(new STree(kind, new SNodeOptionState(treeOf(element)))));
	}

	public boolean isDefined() {
		return isSome();
	}

	public boolean isNone() {
		return location.nodeOptionElement() == null;
	}

	public boolean isSome() {
		return location.nodeOptionElement() != null;
	}

	public boolean contains(T element) {
		return treeOf(location.nodeOptionElement()) == treeOf(element);
	}

	public T get() {
		return location.nodeOptionElement();
	}

	public NodeOption<T> set(T element) {
		return location.nodeOptionWithElement(element);
	}

	public NodeOption<T> set(Mutation<T> mutation) {
		return location.nodeOptionMutateElement(mutation);
	}

	public Iterator<T> iterator() {
		return new ElementIterator();
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Tree tree = location.nodeOptionElement();
		if (tree != null) builder.append(tree.toString());

		builder.append(end);
		return builder.toString();
	}

	private class ElementIterator implements Iterator<T> {

		private boolean done = false;

		public boolean hasNext() {
			return !done;
		}

		public T next() {
			done = true;
			return get();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}