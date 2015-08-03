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

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeOption<T extends Tree> extends TreeBase<SNodeOptionState, NodeOption<T>, NodeOption<T>> implements Tree, Iterable<T> {

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

	public NodeOption(SLocation<SNodeOptionState> location) {
		super(location);
	}

	public NodeOption() {
		super(new SLocation<SNodeOptionState>(new STree<SNodeOptionState>(new SNodeOptionState(treeOf(null)))));
	}

	public NodeOption(T element) {
		super(new SLocation<SNodeOptionState>(new STree<SNodeOptionState>(new SNodeOptionState(treeOf(element)))));
	}

	public boolean isDefined() {
		return isSome();
	}

	public boolean isNone() {
		return location.tree.state.element == null;
	}

	public boolean isSome() {
		return location.tree.state.element != null;
	}

	public boolean contains(T element) {
		final STree<?> actual = location.tree.state.element;
		return actual != null ? actual.equals(treeOf(element)) : element == null;
	}

	@SuppressWarnings("unchecked")
	public T get() {
		return (T) location.safeTraversal(SNodeOptionState.elementTraversal());
	}

	public NodeOption<T> set(T element) {
		return location.safeTraversalReplace(SNodeOptionState.elementTraversal(), element);
	}

	@SuppressWarnings("unchecked")
	public NodeOption<T> set(Mutation<T> mutation) {
		return location.safeTraversalMutate(SNodeOptionState.elementTraversal(), (Mutation<Tree>) mutation);
	}

	public NodeOption<T> setNone() {
		return set((T) null);
	}

	public Iterator<T> iterator() {
		return new ElementIterator();
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Tree tree = location.safeTraversal(SNodeOptionState.elementTraversal());
		builder.append(tree);

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
