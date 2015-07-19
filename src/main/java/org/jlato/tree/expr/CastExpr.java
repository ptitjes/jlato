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
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class CastExpr extends TreeBase<CastExpr.State, Expr, CastExpr> implements Expr {

	public Kind kind() {
		return Kind.CastExpr;
	}

	private CastExpr(SLocation<CastExpr.State> location) {
		super(location);
	}

	public static STree<CastExpr.State> make(STree<? extends Type.State> type, STree<? extends Expr.State> expr) {
		return new STree<CastExpr.State>(new CastExpr.State(type, expr));
	}

	public CastExpr(Type type, Expr expr) {
		super(new SLocation<CastExpr.State>(make(TreeBase.<Type.State>nodeOf(type), TreeBase.<Expr.State>nodeOf(expr))));
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

	public static class State extends SNodeState<State>implements Expr.State {

		public final STree<? extends Type.State> type;

		public final STree<? extends Expr.State> expr;

		State(STree<? extends Type.State> type, STree<? extends Expr.State> expr) {
			this.type = type;
			this.expr = expr;
		}

		public CastExpr.State withType(STree<? extends Type.State> type) {
			return new CastExpr.State(type, expr);
		}

		public CastExpr.State withExpr(STree<? extends Expr.State> expr) {
			return new CastExpr.State(type, expr);
		}

		@Override
		public Kind kind() {
			return Kind.CastExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<CastExpr.State> location) {
			return new CastExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPE;
		}

		@Override
		public STraversal lastChild() {
			return EXPR;
		}
	}

	private static STypeSafeTraversal<CastExpr.State, Type.State, Type> TYPE = new STypeSafeTraversal<CastExpr.State, Type.State, Type>() {

		@Override
		protected STree<?> doTraverse(CastExpr.State state) {
			return state.type;
		}

		@Override
		protected CastExpr.State doRebuildParentState(CastExpr.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	private static STypeSafeTraversal<CastExpr.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<CastExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(CastExpr.State state) {
			return state.expr;
		}

		@Override
		protected CastExpr.State doRebuildParentState(CastExpr.State state, STree<Expr.State> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);
}
