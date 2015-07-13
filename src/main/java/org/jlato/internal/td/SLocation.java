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

package org.jlato.internal.td;

import com.github.andrewoma.dexx.collection.TreeMap;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SLocation {

	private final SLocation parent;
	private final SContext context;
	public final STree tree;
	public final boolean changed;
	public final Tree facade;

	public SLocation(STree tree) {
		this(null, null, tree, false);
	}

	public SLocation(SLocation parent, SContext context, STree tree) {
		this(parent, context, tree, false);
	}

	public SLocation(SLocation parent, SContext context, STree tree, boolean changed) {
		this.parent = parent;
		this.context = context;
		this.tree = tree;
		this.changed = changed;
		this.facade = tree.kind.instantiate(this);
	}

	public SLocation withTree(STree newTree) {
		return newTree == tree ? this : new SLocation(parent, context, newTree, true);
	}

	public SLocation parent() {
		if (parent == null) return null;
		return changed ? parent.withTree(context.rebuildParent(parent.tree, tree)) : parent;
	}

	public SLocation root() {
		final SLocation parent = parent();
		return parent == null ? this : parent.root();
	}

	public SLocation firstChild() {
		if (tree.state instanceof SLeafState) return null;
		else if (tree.state instanceof SNodeState) {
			final SNodeState state = (SNodeState) tree.state;
			if (state.children.isEmpty()) return null;
			SContext first = new SContext.NodeChild(-1).rightSibling(tree);
			return first == null ? null : new SLocation(this, first, first.peruse(tree));
		} else if (tree.state instanceof SNodeListState) {
			final SNodeListState state = (SNodeListState) tree.state;
			if (state.children.isEmpty()) return null;
			SContext first = new SContext.NodeListChild(-1).rightSibling(tree);
			return first == null ? null : new SLocation(this, first, first.peruse(tree));
		} else if (tree.state instanceof STreeSetState) {
			return null; // For now
		} else throw new IllegalStateException();
	}

	public SLocation lastChild() {
		if (tree.state instanceof SLeafState) return null;
		else if (tree.state instanceof SNodeState) {
			final SNodeState state = (SNodeState) tree.state;
			if (state.children.isEmpty()) return null;
			SContext last = new SContext.NodeChild(state.children.size()).leftSibling(tree);
			return last == null ? null : new SLocation(this, last, last.peruse(tree));
		} else if (tree.state instanceof SNodeListState) {
			final SNodeListState state = (SNodeListState) tree.state;
			if (state.children.isEmpty()) return null;
			SContext last = new SContext.NodeListChild(state.children.size()).leftSibling(tree);
			return last == null ? null : new SLocation(this, last, last.peruse(tree));
		} else if (tree.state instanceof STreeSetState) {
			return null; // For now
		} else throw new IllegalStateException();
	}

	public SLocation leftSibling() {
		SLocation parent = parent();
		SContext sibling = context.leftSibling(parent.tree);
		return sibling == null ? null : new SLocation(parent, sibling, sibling.peruse(parent.tree), false);
	}

	public SLocation rightSibling() {
		SLocation parent = parent();
		SContext sibling = context.rightSibling(parent.tree);
		return sibling == null ? null : new SLocation(parent, sibling, sibling.peruse(parent.tree), false);
	}

	@SuppressWarnings("unchecked")
	public <S extends Tree, T extends S> T replace(T replacement) {
		final STree newTree = Tree.treeOf(replacement);
		return (T) withTree(newTree).facade;
	}

	/* Node methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeChild(final int index) {
		final SNodeState state = (SNodeState) tree.state;
		final STree childTree = state.child(index);
		if (childTree == null) return null;

		final SContext childContext = new SContext.NodeChild(index);
		final SLocation childLocation = new SLocation(this, childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeWithChild(int index, C child) {
		final SNodeState state = (SNodeState) tree.state;
		final STree newNode = tree.withState(state.withChild(index, Tree.treeOf(child)));
		return (T) withTree(newNode).facade;
	}

	public <T extends Tree, C extends Tree> T nodeMutateChild(int index, Mutation<C> mutation) {
		return nodeWithChild(index, mutation.mutate(this.<C>nodeChild(index)));
	}

	/* Tree methods */

	@SuppressWarnings("unchecked")
	public <A> A data(final int index) {
		final STreeState state = tree.state;
		return (A) state.data(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T withData(int index, A attribute) {
		final STreeState state = tree.state;
		final STree newTree = tree.withState(state.withData(index, attribute));
		return (T) withTree(newTree).facade;
	}

	public <T extends Tree, A> T mutateData(int index, Mutation<A> mutation) {
		return withData(index, mutation.mutate(this.<A>data(index)));
	}

	/* NodeList methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeListChild(final int index) {
		final SNodeListState state = (SNodeListState) tree.state;
		final STree childTree = state.child(index);
		if (childTree == null) return null;

		final SContext childContext = new SContext.NodeListChild(index);
		final SLocation childLocation = new SLocation(this, childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithChild(int index, C newChild) {
		final SNodeListState state = (SNodeListState) tree.state;
		final STree newTree = tree.withState(state.withChild(index, Tree.treeOf(newChild)));
		return (T) withTree(newTree).facade;
	}

	public Vector<STree> nodeListChildren() {
		final SNodeListState state = (SNodeListState) tree.state;
		return state.children;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithChildren(Vector<STree> children) {
		final SNodeListState state = (SNodeListState) tree.state;
		final STree newTree = tree.withState(state.withChildren(children));
		return (T) withTree(newTree).facade;
	}

	/* TreeSet methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C treeSetTree(final String path) {
		final STreeSetState state = (STreeSetState) tree.state;
		final STree childTree = state.tree(path);
		if (childTree == null) return null;

		final SContext childContext = new SContext.TreeSetTree(path);
		final SLocation childLocation = new SLocation(this, childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T treeSetWithTree(String path, C newChild) {
		final STreeSetState state = (STreeSetState) tree.state;
		final STree newTree = tree.withState(state.withTree(path, Tree.treeOf(newChild)));
		return (T) withTree(newTree).facade;
	}

	public TreeMap<String, STree> treeSetTrees() {
		final STreeSetState state = (STreeSetState) tree.state;
		return state.trees;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T treeSetWithTrees(TreeMap<String, STree> trees) {
		final STreeSetState state = (STreeSetState) tree.state;
		final STree newTree = tree.withState(state.withTrees(trees));
		return (T) withTree(newTree).facade;
	}
}
