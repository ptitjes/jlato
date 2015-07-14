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
import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;

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

	private NodeList(SLocation location) {
		super(location);
	}

	public NodeList(T... elements) {
		super(new SLocation(new STree(kind, new SNodeListState(treeListOf(elements)))));
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

	@SuppressWarnings("unchecked")
	public NodeList<T> prepend(T element) {
		final STree tree = location.tree;

		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree> trees = state.children;

		final SNodeListState newState = state.withChildren(trees.prepend(treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, 0, false));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> append(T element) {
		final STree tree = location.tree;

		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree> trees = state.children;

		final SNodeListState newState = state.withChildren(trees.append(treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, trees.size(), true));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	@SuppressWarnings("unchecked")
	public NodeList<T> insert(int index, T element) {
		final STree tree = location.tree;

		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree> trees = state.children;

		if (index < 0 || index > trees.size())
			throw new IllegalArgumentException();

		final SNodeListState newState = state.withChildren(insertAt(trees, index, treeOf(element)));
		STree newTree = tree.withState(newState);

		WRunRun run = tree.run;
		if (run != null) newTree = newTree.withRun(insertAt(run, index, index == trees.size()));

		return (NodeList<T>) location.withTree(newTree).facade;
	}

	private Vector<STree> insertAt(Vector<STree> trees, int index, STree element) {
		Vector<STree> leftTrees = trees.take(index);
		Vector<STree> rightTrees = trees.drop(index);
		Vector<STree> newTrees = leftTrees.append(element);
		for (STree rightTree : rightTrees) {
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
		final STree tree = location.tree;

		final SNodeListState state = (SNodeListState) tree.state;
		final Vector<STree> trees = state.children;
		if (trees.isEmpty()) return this;

		Builder<STree, Vector<STree>> newTrees = Vector.<STree>factory().newBuilder();
		for (int i = 0; i < trees.size(); i++) {
			T rewrote = mutator.mutate(get(i));
			SLocation location = Tree.locationOf(rewrote);

			// TODO Handle change in the node-list run (leading comments and trailing comment)

			newTrees.add(location.tree);
		}

		STree newTree = tree.withState(state.withChildren(newTrees.build()));
		return (NodeList<T>) location.withTree(newTree).facade;
	}

	public Iterator<T> iterator() {
		return new ChildIterator();
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
