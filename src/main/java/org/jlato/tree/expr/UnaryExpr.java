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
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;

public class UnaryExpr extends TreeBase<UnaryExpr.State, Expr, UnaryExpr> implements Expr {

	public Kind kind() {
		return Kind.UnaryExpr;
	}

	private UnaryExpr(SLocation<UnaryExpr.State> location) {
		super(location);
	}

	public static STree<UnaryExpr.State> make(UnaryOp op, STree<? extends Expr.State> expr) {
		return new STree<UnaryExpr.State>(new UnaryExpr.State(op, expr));
	}

	public UnaryExpr(UnaryOp op, Expr expr) {
		super(new SLocation<UnaryExpr.State>(make(op, TreeBase.<Expr.State>treeOf(expr))));
	}

	public UnaryOp op() {
		return location.safeProperty(OP);
	}

	public UnaryExpr withOp(UnaryOp op) {
		return location.safePropertyReplace(OP, op);
	}

	public UnaryExpr withOp(Mutation<UnaryOp> mutation) {
		return location.safePropertyMutate(OP, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public UnaryExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public UnaryExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final UnaryOp op;

		public final STree<? extends Expr.State> expr;

		State(UnaryOp op, STree<? extends Expr.State> expr) {
			this.op = op;
			this.expr = expr;
		}

		public UnaryExpr.State withOp(UnaryOp op) {
			return new UnaryExpr.State(op, expr);
		}

		public UnaryExpr.State withExpr(STree<? extends Expr.State> expr) {
			return new UnaryExpr.State(op, expr);
		}

		@Override
		public Kind kind() {
			return Kind.UnaryExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<UnaryExpr.State> location) {
			return new UnaryExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public Iterable<SProperty> allProperties() {
			return Collections.<SProperty>singleton(OP);
		}

		@Override
		public STraversal firstChild() {
			return EXPR;
		}

		@Override
		public STraversal lastChild() {
			return EXPR;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (op == null ? state.op != null : !op.equals(state.op))
				return false;
			if (expr == null ? state.expr != null : !expr.equals(state.expr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (op != null) result = 37 * result + op.hashCode();
			if (expr != null) result = 37 * result + expr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<UnaryExpr.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<UnaryExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(UnaryExpr.State state) {
			return state.expr;
		}

		@Override
		public UnaryExpr.State doRebuildParentState(UnaryExpr.State state, STree<Expr.State> child) {
			return state.withExpr(child);
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

	private static STypeSafeProperty<UnaryExpr.State, UnaryOp> OP = new STypeSafeProperty<UnaryExpr.State, UnaryOp>() {

		@Override
		public UnaryOp doRetrieve(UnaryExpr.State state) {
			return state.op;
		}

		@Override
		public UnaryExpr.State doRebuildParentState(UnaryExpr.State state, UnaryOp value) {
			return state.withOp(value);
		}
	};

	public final static LexicalShape opShape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			final UnaryOp op = ((State) tree.state).op;
			switch (op) {
				case Positive:
					return LToken.Plus;
				case Negative:
					return LToken.Minus;
				case PreIncrement:
					return LToken.Increment;
				case PreDecrement:
					return LToken.Decrement;
				case Not:
					return LToken.Not;
				case Inverse:
					return LToken.Inverse;
				case PostIncrement:
					return LToken.Increment;
				case PostDecrement:
					return LToken.Decrement;
				default:
					// Can't happen by definition of enum
					throw new IllegalStateException();
			}
		}
	});

	public static final LexicalShape shape = alternative(new LSCondition() {
		public boolean test(STree tree) {
			final UnaryOp op = ((State) tree.state).op;
			return op.isPrefix();
		}
	}, composite(opShape, child(EXPR)), composite(child(EXPR), opShape));
}
