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

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.WDressing;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.*;
import org.jlato.util.Function1;
import org.jlato.util.Function2;
import org.jlato.util.Mutation;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class TDNodeList<T extends Tree> extends TDTree<SNodeList, NodeList<T>, NodeList<T>> implements NodeList<T> {

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> empty() {
		return new TDNodeList<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1) {
		return new TDNodeList<T>(t1);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2) {
		return new TDNodeList<T>(t1, t2);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3) {
		return new TDNodeList<T>(t1, t2, t3);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4) {
		return new TDNodeList<T>(t1, t2, t3, t4);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tree> NodeList<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10, T t11, T t12, T t13, T t14, T t15, T t16, T t17, T t18, T t19, T t20, T t21, T t22, T t23) {
		return new TDNodeList<T>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23);
	}

	public static <T extends Tree> NodeList<T> of(Iterable<T> ts) {
		NodeList<T> list = empty();
		for (T t : ts) {
			list = list.append(t);
		}
		return list;
	}

	public TDNodeList(TDLocation<SNodeList> location) {
		super(location);
	}

	public TDNodeList(T... elements) {
		super(new TDLocation<SNodeList>(new BUTree<SNodeList>(new SNodeList(treeListOf(elements)))));
	}

	@Override
	public boolean isEmpty() {
		return location.tree.state.children.isEmpty();
	}

	@Override
	public int size() {
		return location.tree.state.children.size();
	}

	@Override
	public boolean contains(T element) {
		return location.tree.state.children.indexOf(treeOf(element)) != -1;
	}

	@Override
	public int indexOf(T element) {
		return location.tree.state.children.indexOf(treeOf(element));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(final int index) {
		return (T) location.safeTraversal(SNodeList.elementTraversal(index));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T first() {
		return (T) location.safeTraversal(SNodeList.firstTraversal());
	}

	@Override
	@SuppressWarnings("unchecked")
	public T last() {
		return (T) location.safeTraversal(SNodeList.lastTraversal());
	}

	@Override
	public NodeList<T> set(int index, T element) {
		return location.safeTraversalReplace(SNodeList.elementTraversal(index), element);
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeList<T> prepend(T element) {
		final BUTree<SNodeList> tree = location.tree;

		final SNodeList state = tree.state;
		final Vector<BUTree<?>> trees = state.children;

		final SNodeList newState = state.withChildren(trees.prepend(treeOf(element)));
		BUTree newTree = tree.withState(newState);

		newTree = insertInDressing(newTree, 0, false);

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@Override
	public NodeList<T> prependAll(NodeList<? extends T> elements) {
		// TODO Do that better
		return insertAll(0, elements);
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeList<T> append(T element) {
		final BUTree<SNodeList> tree = location.tree;

		final SNodeList state = tree.state;
		final Vector<BUTree<?>> trees = state.children;

		final SNodeList newState = state.withChildren(trees.append(treeOf(element)));
		BUTree newTree = tree.withState(newState);

		newTree = insertInDressing(newTree, trees.size(), true);

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@Override
	public NodeList<T> appendAll(NodeList<? extends T> elements) {
		// TODO Do that better
		NodeList<T> newNodeList = this;
		for (T element : elements) {
			newNodeList = newNodeList.append(element);
		}
		return newNodeList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeList<T> insert(int index, T element) {
		final BUTree<SNodeList> tree = location.tree;

		final SNodeList state = tree.state;
		final Vector<BUTree<?>> trees = state.children;

		if (index < 0 || index > trees.size())
			throw new IllegalArgumentException();

		final SNodeList newState = state.withChildren(insertAt(trees, index, treeOf(element)));
		BUTree newTree = tree.withState(newState);

		newTree = insertInDressing(newTree, index, index == trees.size());

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@Override
	public NodeList<T> insertAll(int index, NodeList<? extends T> elements) {
		// TODO Do that better
		NodeList<T> newNodeList = this;
		for (T element : elements) {
			newNodeList = newNodeList.insert(index, element);
			index++;
		}
		return newNodeList;
	}

	private Vector<BUTree<?>> insertAt(Vector<BUTree<?>> trees, int index, BUTree<?> element) {
		Vector<BUTree<?>> leftTrees = trees.take(index);
		Vector<BUTree<?>> rightTrees = trees.drop(index);
		Vector<BUTree<?>> newTrees = leftTrees.append(element);
		for (BUTree<?> rightTree : rightTrees) {
			newTrees = newTrees.append(rightTree);
		}
		return newTrees;
	}

	private BUTree insertInDressing(BUTree tree, int index, boolean last) {
		WDressing dressing = tree.dressing == null ? null : tree.dressing;
		if (dressing != null && dressing.run != null) {

			final WRunRun run = dressing.run;
			// If not last, insert before the element shape
			// If last, insert after the last element shape
			final WRunRun newRun = run.insert((index + (last ? -1 : 0)) * 2 + 1, 2);

			tree = tree.withDressing(dressing.withRun(newRun));
		}
		return tree;
	}

	@Override
	public NodeList<T> insert(T before, T element) {
		int index = indexOf(before);
		return index == -1 ? append(element) : insert(index, element);
	}

	@Override
	public NodeList<T> insertAll(T before, NodeList<? extends T> elements) {
		int index = indexOf(before);
		return index == -1 ? appendAll(elements) : insertAll(index, elements);
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeList<T> delete(int index) {
		final BUTree<SNodeList> tree = location.tree;

		final SNodeList state = tree.state;
		final Vector<BUTree<?>> trees = state.children;

		if (index < 0 || index > trees.size())
			throw new IllegalArgumentException();

		final SNodeList newState = state.withChildren(deleteAt(trees, index));
		BUTree newTree = tree.withState(newState);

		newTree = deleteInDressing(newTree, index, index == trees.size());

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	private Vector<BUTree<?>> deleteAt(Vector<BUTree<?>> trees, int index) {
		Vector<BUTree<?>> leftTrees = trees.take(index);
		Vector<BUTree<?>> rightTrees = trees.drop(index + 1);
		Vector<BUTree<?>> newTrees = leftTrees;
		for (BUTree<?> rightTree : rightTrees) {
			newTrees = newTrees.append(rightTree);
		}
		return newTrees;
	}

	private BUTree deleteInDressing(BUTree tree, int index, boolean last) {
		WDressing dressing = tree.dressing == null ? null : tree.dressing;
		if (dressing != null && dressing.run != null) {

			final WRunRun run = dressing.run;
			// If not last, delete before the element shape
			// If last, delete after the last element shape
			final WRunRun newRun = run.delete((index + (last ? -1 : 0)) * 2 + 1, 2);

			tree = tree.withDressing(dressing.withRun(newRun));
		}
		return tree;
	}

	@Override
	public NodeList<T> delete(T element) {
		int index = indexOf(element);
		return index == -1 ? this : delete(index);
	}

	@Override
	@SuppressWarnings("unchecked")
	public NodeList<T> rewriteAll(Mutation<T> mutator) {
		final BUTree<SNodeList> tree = location.tree;

		final SNodeList state = tree.state;
		final Vector<BUTree<?>> trees = state.children;
		if (trees.isEmpty()) return this;

		Builder<BUTree<?>, Vector<BUTree<?>>> newTrees = Vector.<BUTree<?>>factory().newBuilder();
		for (int i = 0; i < trees.size(); i++) {
			T rewrote = mutator.mutate(get(i));
			TDLocation location = TDTree.locationOf(rewrote);

			// TODO Handle change in the node-list run (leading comments and trailing comment)

			newTrees.add(location.tree);
		}

		BUTree<SNodeList> newTree = tree.withState(state.withChildren(newTrees.build()));
		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@Override
	public <U extends Tree> NodeList<U> map(Function1<T, U> f) {
		// TODO Optimize ?
		NodeList<U> us = TDNodeList.empty();
		for (T t : this) {
			us = us.append(f.apply(t));
		}
		return us;
	}

	@Override
	public <U extends Tree> U foldLeft(U z, Function2<U, T, U> f) {
		// TODO Optimize ?
		U result = z;
		for (T t : this) {
			result = f.apply(result, t);
		}
		return result;
	}

	@Override
	public <U extends Tree> U foldRight(U z, Function2<T, U, U> f) {
		// TODO Optimize ?
		U result = z;
		for (int i = size() - 1; i >= 0; i--) {
			T t = get(i);
			result = f.apply(t, result);
		}
		return result;
	}

	@Override
	public boolean exists(Function1<T, Boolean> p) {
		for (T e : this) {
			if (p.apply(e)) return true;
		}
		return false;
	}

	@Override
	public NodeList<T> filter(Function1<T, Boolean> p) {
		NodeList<T> nl = TDNodeList.empty();
		for (T e : this) {
			if (p.apply(e)) nl = nl.append(e);
		}
		return nl;
	}

	@Override
	public Iterator<T> iterator() {
		return new ChildIterator();
	}

	@Override
	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		boolean first = true;
		for (BUTree<?> tree : location.tree.state.children) {
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
