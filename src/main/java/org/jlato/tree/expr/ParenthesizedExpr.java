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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class ParenthesizedExpr extends TreeBase<ParenthesizedExpr.State, Expr, ParenthesizedExpr> implements Expr {

	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	private ParenthesizedExpr(SLocation<ParenthesizedExpr.State> location) {
		super(location);
	}

	public static STree<ParenthesizedExpr.State> make(Expr inner) {
		return new STree<ParenthesizedExpr.State>(new ParenthesizedExpr.State(TreeBase.<Expr.State>nodeOf(inner)));
	}

	public ParenthesizedExpr(Expr inner) {
		super(new SLocation<ParenthesizedExpr.State>(make(inner)));
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

	private static final STraversal<ParenthesizedExpr.State> INNER = new STraversal<ParenthesizedExpr.State>() {

		public STree<?> traverse(ParenthesizedExpr.State state) {
			return state.inner;
		}

		public ParenthesizedExpr.State rebuildParentState(ParenthesizedExpr.State state, STree<?> child) {
			return state.withInner((STree) child);
		}

		public STraversal<ParenthesizedExpr.State> leftSibling(ParenthesizedExpr.State state) {
			return null;
		}

		public STraversal<ParenthesizedExpr.State> rightSibling(ParenthesizedExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(INNER), token(LToken.ParenthesisRight)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> inner;

		State(STree<Expr.State> inner) {
			this.inner = inner;
		}

		public ParenthesizedExpr.State withInner(STree<Expr.State> inner) {
			return new ParenthesizedExpr.State(inner);
		}

		public STraversal<ParenthesizedExpr.State> firstChild() {
			return null;
		}

		public STraversal<ParenthesizedExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ParenthesizedExpr.State> location) {
			return new ParenthesizedExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ParenthesizedExpr;
		}
	}
}
