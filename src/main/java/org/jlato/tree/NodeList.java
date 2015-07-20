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

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.util.Function1;
import org.jlato.util.Function2;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeList<T extends Tree> extends TreeBase<SNodeListState, NodeList<T>, NodeList<T>> implements Tree, Iterable<T> {

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> empty() {
		return new NodeList<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1) {
		return new NodeList<T>(t1);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2) {
		return new NodeList<T>(t1, t2);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3) {
		return new NodeList<T>(t1, t2, t3);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4) {
		return new NodeList<T>(t1, t2, t3, t4);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5) {
		return new NodeList<T>(t1, t2, t3, t4, t5);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6);
	}

	public static <T extends Tree> NodeList<T> of(Iterable<T> ts) {
		NodeList<T> list = empty();
		for (T t : ts) {
			list = list.append(t);
		}
		return list;
	}

	public NodeList(SLocation<SNodeListState> location) {
		super(location);
	}

	public NodeList(T... elements) {
		super(new SLocation<SNodeListState>(new STree<SNodeListState>(new SNodeListState(treeListOf(elements)))));
	}

	public boolean isEmpty() {
		return location.tree.state.children.isEmpty();
	}

	public int size() {
		return location.tree.state.children.size();
	}

	public boolean contains(T element) {
		return location.tree.state.children.indexOf(treeOf(element)) != -1;
	}

	public T get(final int index) {
		return (T) location.safeTraversal(SNodeListState.elementTraversal(index));
	}

	public NodeList<T> set(int index, T element) {
		return location.safeTraversalReplace(SNodeListState.elementTraversal(index), element);
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> prepend(T element) {
		final STree<SNodeListState> tree = location.tree;

		final SNodeListState state = tree.state;
		final Vector<STree<?>> trees = state.children;

		final SNodeListState newState = state.withChildren(trees.prepend(treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, 0, false));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> append(T element) {
		final STree<SNodeListState> tree = location.tree;

		final SNodeListState state = tree.state;
		final Vector<STree<?>> trees = state.children;

		final SNodeListState newState = state.withChildren(trees.append(treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, trees.size(), true));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public NodeList<T> appendAll(NodeList<T> elements) {
		// TODO Do that better
		NodeList<T> newNodeList = this;
		for (T element : elements) {
			newNodeList = newNodeList.append(element);
		}
		return newNodeList;
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> insert(int index, T element) {
		final STree<SNodeListState> tree = location.tree;

		final SNodeListState state = tree.state;
		final Vector<STree<?>> trees = state.children;

		if (index < 0 || index > trees.size())
			throw new IllegalArgumentException();

		final SNodeListState newState = state.withChildren(insertAt(trees, index, treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, index, index == trees.size()));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	private Vector<STree<?>> insertAt(Vector<STree<?>> trees, int index, STree<?> element) {
		Vector<STree<?>> leftTrees = trees.take(index);
		Vector<STree<?>> rightTrees = trees.drop(index);
		Vector<STree<?>> newTrees = leftTrees.append(element);
		for (STree<?> rightTree : rightTrees) {
			newTrees = newTrees.append(rightTree);
		}
		return newTrees;
	}

	private WRunRun insertAt(WRunRun run, int index, boolean last) {
		// If not last, count in the before shape
		// If last, count in the missing separator shape
		return run.insertBefore(index * 2 + (last ? 0 : 1), 2);
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> rewriteAll(Mutation<T> mutator) {
		final STree<SNodeListState> tree = location.tree;

		final SNodeListState state = tree.state;
		final Vector<STree<?>> trees = state.children;
		if (trees.isEmpty()) return this;

		Builder<STree<?>, Vector<STree<?>>> newTrees = Vector.<STree<?>>factory().newBuilder();
		for (int i = 0; i < trees.size(); i++) {
			T rewrote = mutator.mutate(get(i));
			SLocation location = TreeBase.locationOf(rewrote);

			// TODO Handle change in the node-list run (leading comments and trailing comment)

			newTrees.add(location.tree);
		}

		STree<SNodeListState> newTree = tree.withState(state.withChildren(newTrees.build()));
		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public <U extends Tree> NodeList<U> map(Function1<T, U> f) {
		// TODO Optimize ?
		NodeList<U> us = NodeList.empty();
		for (T t : this) {
			us = us.append(f.apply(t));
		}
		return us;
	}

	public <U extends Tree> U foldLeft(U z, Function2<U, T, U> f) {
		// TODO Optimize ?
		U result = z;
		for (T t : this) {
			result = f.apply(result, t);
		}
		return result;
	}

	public Iterator<T> iterator() {
		return new ChildIterator();
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		boolean first = true;
		for (STree<?> tree : location.tree.state.children) {
			if (!first) builder.append(sep);
			else first = false;

			Tree next = tree.asTree();
			builder.append(next.toString());
		}

		builder.append(end);
		return builder.toString();
	}

	private class ChildIterator implements Iterator<T> {

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
