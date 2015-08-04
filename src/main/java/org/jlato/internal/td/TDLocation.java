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

import org.jlato.internal.bu.*;
import org.jlato.util.Mutation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class TDLocation<S extends STreeState> {

	private final TDContext<?> context;
	public final BUTree<S> tree;
	public final boolean changed;
	public final Tree facade;

	public TDLocation(BUTree<S> tree) {
		this(null, tree, false);
	}

	public TDLocation(TDContext<?> context, BUTree<S> tree) {
		this(context, tree, false);
	}

	public TDLocation(TDContext<?> context, BUTree<S> tree, boolean changed) {
		this.context = context;
		this.tree = tree;
		this.changed = changed;
		this.facade = tree.state.instantiate(this);
	}

	public TDLocation<S> withTree(BUTree<S> newTree) {
		return newTree == tree ? this : new TDLocation<S>(context, newTree, true);
	}

	public TDContext<?> context() {
		if (context == null) return null;
		return changed ? context.rebuilt(tree) : context;
	}

	public TDLocation<?> parent() {
		TDContext<?> context = context();
		return context == null ? null : context.parent;
	}

	public TDLocation<?> root() {
		final TDLocation parent = parent();
		return parent == null ? this : parent.root();
	}

	public TDLocation<?> firstChild() {
		STraversal traversal = tree.state.firstChild();
		return traversal == null ? null : traverse(traversal);
	}

	public TDLocation<?> lastChild() {
		STraversal traversal = tree.state.lastChild();
		return traversal == null ? null : traverse(traversal);
	}

	public TDLocation<?> traverse(STraversal traversal) {
		return new TDContext<S>(this, traversal).newLocation();
	}

	public TDLocation<?> leftSibling() {
		TDContext<?> leftSibling = context().leftSibling();
		return leftSibling == null ? null : leftSibling.newLocation();
	}

	public TDLocation<?> rightSibling() {
		TDContext<?> rightSibling = context().rightSibling();
		return rightSibling == null ? null : rightSibling.newLocation();
	}

	public <T extends Tree> T replace(T replacement) {
		return replaceTree(TDTree.<S>treeOf(replacement));
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T replaceTree(BUTree<S> newTree) {
		return (T) withTree(newTree).facade;
	}

	/* Node methods */

	public <A> A safeProperty(STypeSafeProperty<S, A> property) {
		return property.doRetrieve(tree.state);
	}

	public <T extends Tree, A> T safePropertyReplace(STypeSafeProperty<S, A> property, A attribute) {
		return replaceTree(tree.withState(property.doRebuildParentState(tree.state, attribute)));
	}

	public <T extends Tree, A> T safePropertyMutate(STypeSafeProperty<S, A> property, Mutation<A> mutation) {
		return safePropertyReplace(property, mutation.mutate(safeProperty(property)));
	}

	@SuppressWarnings("unchecked")
	public <C extends Tree> C safeTraversal(STypeSafeTraversal<S, ?, C> traversal) {
		final TDLocation<?> location = traverse(traversal);
		return location == null ? null : (C) location.facade;
	}

	public <T extends Tree, C extends Tree> T safeTraversalReplace(STypeSafeTraversal<S, ?, C> traversal, C child) {
		return replaceTree(tree.traverseReplace(traversal, TDTree.treeOf(child)));
	}

	public <T extends Tree, C extends Tree> T safeTraversalMutate(STypeSafeTraversal<S, ?, C> traversal, Mutation<C> mutation) {
		return safeTraversalReplace(traversal, mutation.mutate(safeTraversal(traversal)));
	}
}
