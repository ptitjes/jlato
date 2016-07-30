/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.td.coll;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.*;
import org.jlato.util.Mutation;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Didier Villevalois
 */
public class TDNodeOption<T extends Tree> extends TDTree<SNodeOption, TDNodeOption<T>, TDNodeOption<T>> implements NodeOption<T> {

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> of(T tree) {
		return tree == null ? TDNodeOption.<T>none() : some(tree);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> none() {
		return new TDNodeOption<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeOption<T> some(T tree) {
		return new TDNodeOption<T>(tree);
	}

	public TDNodeOption(TDLocation<SNodeOption> location) {
		super(location);
	}

	public TDNodeOption() {
		super(new TDLocation<SNodeOption>(new BUTree<SNodeOption>(new SNodeOption(treeOf(null)))));
	}

	public TDNodeOption(T element) {
		super(new TDLocation<SNodeOption>(new BUTree<SNodeOption>(new SNodeOption(treeOf(element)))));
	}

	@Override
	public boolean isDefined() {
		return isSome();
	}

	@Override
	public boolean isNone() {
		return location.tree.state.element == null;
	}

	@Override
	public boolean isSome() {
		return location.tree.state.element != null;
	}

	@Override
	public boolean contains(T element) {
		final BUTree<?> actual = location.tree.state.element;
		return actual != null ? actual.equals(treeOf(element)) : element == null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get() {
		return (T) location.safeTraversal(SNodeOption.elementTraversal());
	}

	@Override
	public NodeOption<T> set(T element) {
		return location.safeTraversalReplace(SNodeOption.elementTraversal(), element);
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeOption<T> set(Mutation<T> mutation) {
		return location.safeTraversalMutate(SNodeOption.elementTraversal(), (Mutation<Tree>) mutation);
	}

	@Override
	public NodeOption<T> setNone() {
		return set((T) null);
	}

	@Override
	public Iterator<T> iterator() {
		return new ElementIterator();
	}

	@Override
	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Tree tree = location.safeTraversal(SNodeOption.elementTraversal());
		if (tree != null) builder.append(tree);

		builder.append(end);
		return builder.toString();
	}

	private class ElementIterator implements Iterator<T> {

		private boolean done = false;

		public boolean hasNext() {
			return !done && location.tree.state.element != null;
		}

		public T next() {
			if (done || location.tree.state.element == null)
				throw new NoSuchElementException();
			done = true;
			return get();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
