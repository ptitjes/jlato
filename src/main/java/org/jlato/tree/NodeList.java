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
import org.jlato.internal.bu.WDressing;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.util.Function1;
import org.jlato.util.Function2;
import org.jlato.util.Mutation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeList<T extends Tree> extends TDTree<SNodeListState, NodeList<T>, NodeList<T>> implements Tree, Iterable<T> {

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

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22, T t23) {
		return new NodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23);
	}

	public static <T extends Tree> NodeList<T> of(Iterable<T> ts) {
		NodeList<T> list = empty();
		for (T t : ts) {
			list = list.append(t);
		}
		return list;
	}

	public NodeList(TDLocation<SNodeListState> location) {
		super(location);
	}

	public NodeList(T... elements) {
		super(new TDLocation<SNodeListState>(new STree<SNodeListState>(new SNodeListState(treeListOf(elements)))));
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

	public T first() {
		return (T) location.safeTraversal(SNodeListState.firstTraversal());
	}

	public T last() {
		return (T) location.safeTraversal(SNodeListState.lastTraversal());
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

		newTree = insertInDressing(newTree, 0, false);

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public NodeList<T> prependAll(NodeList<? extends T> elements) {
		// TODO Do that better
		return insertAll(0, elements);
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> append(T element) {
		final STree<SNodeListState> tree = location.tree;

		final SNodeListState state = tree.state;
		final Vector<STree<?>> trees = state.children;

		final SNodeListState newState = state.withChildren(trees.append(treeOf(element)));
		STree newTree = tree.withState(newState);

		newTree = insertInDressing(newTree, trees.size(), true);

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public NodeList<T> appendAll(NodeList<? extends T> elements) {
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

		newTree = insertInDressing(newTree, index, index == trees.size());

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public NodeList<T> insertAll(int index, NodeList<? extends T> elements) {
		// TODO Do that better
		NodeList<T> newNodeList = this;
		for (T element : elements) {
			newNodeList = newNodeList.insert(index, element);
			index++;
		}
		return newNodeList;
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

	private STree insertInDressing(STree tree, int index, boolean last) {
		WDressing dressing = tree.dressing == null ? null : tree.dressing;
		if (dressing != null && dressing.run != null) {

			final WRunRun run = dressing.run;
			// If not last, insert before the element shape
			// If last, insert after the last element shape
			final WRunRun newRun = run.insert((index + (last ? -1 : 0)) * 2 + 1, 2, !last);

			tree = tree.withDressing(dressing.withRun(newRun));
		}
		return tree;
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
			TDLocation location = TDTree.locationOf(rewrote);

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

			Tree next = tree == null ? null : tree.asTree();
			builder.append(next);
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
