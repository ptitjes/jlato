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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class CastExpr extends TreeBase<CastExpr.State, Expr, CastExpr> implements Expr {

	public final static SKind<CastExpr.State> kind = new SKind<CastExpr.State>() {
		public CastExpr instantiate(SLocation<CastExpr.State> location) {
			return new CastExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CastExpr(SLocation<CastExpr.State> location) {
		super(location);
	}

	public static STree<CastExpr.State> make(Type type, Expr expr) {
		return new STree<CastExpr.State>(kind, new CastExpr.State(TreeBase.<Type.State>nodeOf(type), TreeBase.<Expr.State>nodeOf(expr)));
	}

	public CastExpr(Type type, Expr expr) {
		super(new SLocation<CastExpr.State>(make(type, expr)));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public CastExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public CastExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public CastExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public CastExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	private static final STraversal<CastExpr.State> TYPE = new STraversal<CastExpr.State>() {

		public STree<?> traverse(CastExpr.State state) {
			return state.type;
		}

		public CastExpr.State rebuildParentState(CastExpr.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<CastExpr.State> leftSibling(CastExpr.State state) {
			return null;
		}

		public STraversal<CastExpr.State> rightSibling(CastExpr.State state) {
			return EXPR;
		}
	};
	private static final STraversal<CastExpr.State> EXPR = new STraversal<CastExpr.State>() {

		public STree<?> traverse(CastExpr.State state) {
			return state.expr;
		}

		public CastExpr.State rebuildParentState(CastExpr.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<CastExpr.State> leftSibling(CastExpr.State state) {
			return TYPE;
		}

		public STraversal<CastExpr.State> rightSibling(CastExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);

	public static class State extends SNodeState<State> {

		public final STree<Type.State> type;

		public final STree<Expr.State> expr;

		State(STree<Type.State> type, STree<Expr.State> expr) {
			this.type = type;
			this.expr = expr;
		}

		public CastExpr.State withType(STree<Type.State> type) {
			return new CastExpr.State(type, expr);
		}

		public CastExpr.State withExpr(STree<Expr.State> expr) {
			return new CastExpr.State(type, expr);
		}

		public STraversal<CastExpr.State> firstChild() {
			return null;
		}

		public STraversal<CastExpr.State> lastChild() {
			return null;
		}
	}
}
