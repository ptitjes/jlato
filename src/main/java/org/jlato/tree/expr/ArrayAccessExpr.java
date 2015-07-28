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

package org.jlato.tree.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ArrayAccessExpr extends TreeBase<ArrayAccessExpr.State, Expr, ArrayAccessExpr> implements Expr {

	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	private ArrayAccessExpr(SLocation<ArrayAccessExpr.State> location) {
		super(location);
	}

	public static STree<ArrayAccessExpr.State> make(STree<? extends Expr.State> name, STree<? extends Expr.State> index) {
		return new STree<ArrayAccessExpr.State>(new ArrayAccessExpr.State(name, index));
	}

	public ArrayAccessExpr(Expr name, Expr index) {
		super(new SLocation<ArrayAccessExpr.State>(make(TreeBase.<Expr.State>treeOf(name), TreeBase.<Expr.State>treeOf(index))));
	}

	public Expr name() {
		return location.safeTraversal(NAME);
	}

	public ArrayAccessExpr withName(Expr name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ArrayAccessExpr withName(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public Expr index() {
		return location.safeTraversal(INDEX);
	}

	public ArrayAccessExpr withIndex(Expr index) {
		return location.safeTraversalReplace(INDEX, index);
	}

	public ArrayAccessExpr withIndex(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(INDEX, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> name;

		public final STree<? extends Expr.State> index;

		State(STree<? extends Expr.State> name, STree<? extends Expr.State> index) {
			this.name = name;
			this.index = index;
		}

		public ArrayAccessExpr.State withName(STree<? extends Expr.State> name) {
			return new ArrayAccessExpr.State(name, index);
		}

		public ArrayAccessExpr.State withIndex(STree<? extends Expr.State> index) {
			return new ArrayAccessExpr.State(name, index);
		}

		@Override
		public Kind kind() {
			return Kind.ArrayAccessExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ArrayAccessExpr.State> location) {
			return new ArrayAccessExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return INDEX;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ArrayAccessExpr.State state = (ArrayAccessExpr.State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (index == null ? state.index != null : !index.equals(state.index))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			if (index != null) result = 37 * result + index.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ArrayAccessExpr.State, Expr.State, Expr> NAME = new STypeSafeTraversal<ArrayAccessExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public ArrayAccessExpr.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return INDEX;
		}
	};

	private static STypeSafeTraversal<ArrayAccessExpr.State, Expr.State, Expr> INDEX = new STypeSafeTraversal<ArrayAccessExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.index;
		}

		@Override
		public ArrayAccessExpr.State doRebuildParentState(State state, STree<Expr.State> child) {
			return state.withIndex(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(NAME),
			token(LToken.BracketLeft), child(INDEX), token(LToken.BracketRight)
	);
}
