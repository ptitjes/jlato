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
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SLocation {

	public final SContext context;
	public final STree tree;
	public final boolean changed;
	public final Tree facade;

	public SLocation(STree tree) {
		this(new SContext.Root(), tree, false);
	}

	public SLocation(SContext context, STree tree) {
		this(context, tree, false);
	}

	public SLocation(SContext context, STree tree, boolean changed) {
		this.context = context;
		this.tree = tree;
		this.changed = changed;
		this.facade = tree.kind.instantiate(this);
	}

	public SLocation withTree(STree newTree) {
		return newTree == tree ? this : new SLocation(context, newTree, true);
	}

	public Tree parent() {
		final SLocation parentLocation = changed ? context.rebuiltWith(tree) : context.original();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		final Tree parent = parent();
		return parent == null ? facade : parent.root();
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

		final SContext childContext = new SContext.NodeChild(this, index);
		final SLocation childLocation = new SLocation(childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeWithChild(int index, C child) {
		final SNodeState state = (SNodeState) tree.state;
		final STree newNode = tree.withState(state.withChild(index, Tree.treeOf(child)));
		return (T) withTree(newNode).facade;
	}

	@SuppressWarnings("unchecked")
	public <A> A nodeData(final int index) {
		return (A) tree.state.data(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T nodeWithData(int index, A attribute) {
		final SNodeState state = (SNodeState) tree.state;
		final STree newNode = tree.withState(state.withData(index, attribute));
		return (T) withTree(newNode).facade;
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

	/* NodeList methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeListChild(final int index) {
		final SNodeListState state = (SNodeListState) tree.state;
		final STree childTree = state.child(index);
		if (childTree == null) return null;

		final SContext childContext = new SContext.NodeListChild(this, index);
		final SLocation childLocation = new SLocation(childContext, childTree);
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

		final SContext childContext = new SContext.TreeSetTree(this, path);
		final SLocation childLocation = new SLocation(childContext, childTree);
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
