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

import static org.jlato.internal.shapes.LexicalShape.*;

public class UnaryExpr extends TreeBase<UnaryExpr.State, Expr, UnaryExpr> implements Expr {

	public Kind kind() {
		return Kind.UnaryExpr;
	}

	private UnaryExpr(SLocation<UnaryExpr.State> location) {
		super(location);
	}

	public static STree<UnaryExpr.State> make(UnaryOp operator, STree<? extends Expr.State> expr) {
		return new STree<UnaryExpr.State>(new UnaryExpr.State(operator, expr));
	}

	public UnaryExpr(UnaryOp operator, Expr expr) {
		super(new SLocation<UnaryExpr.State>(make(operator, TreeBase.<Expr.State>nodeOf(expr))));
	}

	public UnaryOp op() {
		return location.safeProperty(OPERATOR);
	}

	public UnaryExpr withOp(UnaryOp operator) {
		return location.safePropertyReplace(OPERATOR, operator);
	}

	public UnaryExpr withOp(Mutation<UnaryOp> mutation) {
		return location.safePropertyMutate(OPERATOR, mutation);
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

	public static boolean isPrefix(UnaryOp op) {
		return !isPostfix(op);
	}

	public static boolean isPostfix(UnaryOp op) {
		return op == UnaryOp.PostIncrement || op == UnaryOp.PostDecrement;
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final UnaryOp operator;

		public final STree<? extends Expr.State> expr;

		State(UnaryOp operator, STree<? extends Expr.State> expr) {
			this.operator = operator;
			this.expr = expr;
		}

		public UnaryExpr.State withOperator(UnaryOp operator) {
			return new UnaryExpr.State(operator, expr);
		}

		public UnaryExpr.State withExpr(STree<? extends Expr.State> expr) {
			return new UnaryExpr.State(operator, expr);
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
			UnaryExpr.State state = (UnaryExpr.State) o;
			if (!operator.equals(state.operator))
				return false;
			if (!expr.equals(state.expr))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + operator.hashCode();
			result = 37 * result + expr.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<UnaryExpr.State, Expr.State, Expr> EXPR = new STypeSafeTraversal<UnaryExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(UnaryExpr.State state) {
			return state.expr;
		}

		@Override
		protected UnaryExpr.State doRebuildParentState(UnaryExpr.State state, STree<Expr.State> child) {
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

	private static STypeSafeProperty<UnaryExpr.State, UnaryOp> OPERATOR = new STypeSafeProperty<UnaryExpr.State, UnaryOp>() {

		@Override
		protected UnaryOp doRetrieve(UnaryExpr.State state) {
			return state.operator;
		}

		@Override
		protected UnaryExpr.State doRebuildParentState(UnaryExpr.State state, UnaryOp value) {
			return state.withOperator(value);
		}
	};

	private final static LexicalShape opShape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return ((State) tree.state).operator.token;
		}
	});

	public final static LexicalShape shape = alternative(new LSCondition() {
		public boolean test(STree tree) {
			final UnaryOp op = ((State) tree.state).operator;
			return isPrefix(op);
		}
	}, composite(opShape, child(EXPR)), composite(child(EXPR), opShape));

	public enum UnaryOp {
		Positive(LToken.Plus),
		Negative(LToken.Minus),
		PreIncrement(LToken.Increment),
		PreDecrement(LToken.Decrement),
		Not(LToken.Not),
		Inverse(LToken.Inverse),
		PostIncrement(LToken.Increment),
		PostDecrement(LToken.Decrement),
		// Keep last comma
		;


		protected final LToken token;

		UnaryOp(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
