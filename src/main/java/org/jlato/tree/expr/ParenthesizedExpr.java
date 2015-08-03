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
import org.jlato.tree.Tree;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ParenthesizedExpr extends TreeBase<ParenthesizedExpr.State, Expr, ParenthesizedExpr> implements Expr {

	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	private ParenthesizedExpr(SLocation<ParenthesizedExpr.State> location) {
		super(location);
	}

	public static STree<ParenthesizedExpr.State> make(STree<? extends Expr.State> inner) {
		return new STree<ParenthesizedExpr.State>(new ParenthesizedExpr.State(inner));
	}

	public ParenthesizedExpr(Expr inner) {
		super(new SLocation<ParenthesizedExpr.State>(make(TreeBase.<Expr.State>treeOf(inner))));
	}

	public Expr inner() {
		return location.safeTraversal(INNER);
	}

	public ParenthesizedExpr withInner(Expr inner) {
		return location.safeTraversalReplace(INNER, inner);
	}

	public ParenthesizedExpr withInner(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(INNER, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> inner;

		State(STree<? extends Expr.State> inner) {
			this.inner = inner;
		}

		public ParenthesizedExpr.State withInner(STree<? extends Expr.State> inner) {
			return new ParenthesizedExpr.State(inner);
		}

		@Override
		public Kind kind() {
			return Kind.ParenthesizedExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ParenthesizedExpr.State> location) {
			return new ParenthesizedExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return INNER;
		}

		@Override
		public STraversal lastChild() {
			return INNER;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (inner == null ? state.inner != null : !inner.equals(state.inner))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (inner != null) result = 37 * result + inner.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ParenthesizedExpr.State, Expr.State, Expr> INNER = new STypeSafeTraversal<ParenthesizedExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(ParenthesizedExpr.State state) {
			return state.inner;
		}

		@Override
		public ParenthesizedExpr.State doRebuildParentState(ParenthesizedExpr.State state, STree<Expr.State> child) {
			return state.withInner(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(INNER), token(LToken.ParenthesisRight)
	);
}
