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
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SLocation<S extends STreeState> {

	private final SContext<?> context;
	public final STree<S> tree;
	public final boolean changed;
	public final Tree facade;

	public SLocation(STree<S> tree) {
		this(null, tree, false);
	}

	public SLocation(SContext<?> context, STree<S> tree) {
		this(context, tree, false);
	}

	public SLocation(SContext<?> context, STree<S> tree, boolean changed) {
		this.context = context;
		this.tree = tree;
		this.changed = changed;
		this.facade = tree.state.instantiate(this);
	}

	public SLocation<S> withTree(STree<S> newTree) {
		return newTree == tree ? this : new SLocation<S>(context, newTree, true);
	}

	public SContext<?> context() {
		if (context == null) return null;
		return changed ? context.rebuilt(tree) : context;
	}

	public SLocation<?> parent() {
		SContext<?> context = context();
		return context == null ? null : context.parent;
	}

	public SLocation<?> root() {
		final SLocation parent = parent();
		return parent == null ? this : parent.root();
	}

	public SLocation<?> firstChild() {
		STraversal traversal = tree.state.firstChild();
		return traversal == null ? null : traverse(traversal);
	}

	public SLocation<?> lastChild() {
		STraversal traversal = tree.state.lastChild();
		return traversal == null ? null : traverse(traversal);
	}

	public SLocation<?> traverse(STraversal traversal) {
		return new SContext<S>(this, traversal).newLocation();
	}

	public SLocation<?> leftSibling() {
		SContext<? extends STreeState> leftSibling = context().leftSibling();
		return leftSibling == null ? null : leftSibling.newLocation();
	}

	public SLocation<?> rightSibling() {
		SContext<? extends STreeState> rightSibling = context().rightSibling();
		return rightSibling == null ? null : rightSibling.newLocation();
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T replace(T replacement) {
		final STree<S> newTree = TreeBase.treeOf(replacement);
		return (T) withTree(newTree).facade;
	}

	/* Node methods */

	@SuppressWarnings("unchecked")
	public <A> A safeProperty(STypeSafeProperty<S, A> property) {
		final S state = tree.state;
		return (A) property.retrieve(state);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T safePropertyReplace(STypeSafeProperty<S, A> property, A attribute) {
		final S state = tree.state;
		final STree newTree = tree.withState((S) property.rebuildParentState(state, attribute));
		return (T) withTree(newTree).facade;
	}

	public <T extends Tree, A> T safePropertyMutate(STypeSafeProperty<S, A> property, Mutation<A> mutation) {
		return safePropertyReplace(property, mutation.mutate(safeProperty(property)));
	}

	@SuppressWarnings("unchecked")
	public <C extends Tree> C safeTraversal(STypeSafeTraversal<S, ?, C> traversal) {
		return (C) traverse(traversal).facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T safeTraversalReplace(STypeSafeTraversal<S, ?, C> traversal, C child) {
		return (T) withTree(tree.traverseReplace(traversal, TreeBase.treeOf(child))).facade;
	}

	public <T extends Tree, C extends Tree> T safeTraversalMutate(STypeSafeTraversal<S, ?, C> traversal, Mutation<C> mutation) {
		return safeTraversalReplace(traversal, mutation.mutate(safeTraversal(traversal)));
	}
}
