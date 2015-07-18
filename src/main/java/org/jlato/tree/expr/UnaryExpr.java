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
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class UnaryExpr extends TreeBase<UnaryExpr.State, Expr, UnaryExpr> implements Expr {

	public final static Kind kind = new Kind() {

	};

	private UnaryExpr(SLocation<UnaryExpr.State> location) {
		super(location);
	}

	public static STree<UnaryExpr.State> make(UnaryOp operator, Expr expr) {
		return new STree<UnaryExpr.State>(kind, new UnaryExpr.State(operator, TreeBase.<Expr.State>nodeOf(expr)));
	}

	public UnaryExpr(UnaryOp operator, Expr expr) {
		super(new SLocation<UnaryExpr.State>(make(operator, expr)));
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

	private static final STraversal<UnaryExpr.State> EXPR = new STraversal<UnaryExpr.State>() {

		public STree<?> traverse(UnaryExpr.State state) {
			return state.expr;
		}

		public UnaryExpr.State rebuildParentState(UnaryExpr.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<UnaryExpr.State> leftSibling(UnaryExpr.State state) {
			return null;
		}

		public STraversal<UnaryExpr.State> rightSibling(UnaryExpr.State state) {
			return null;
		}
	};

	private static final SProperty<UnaryExpr.State> OPERATOR = new SProperty<UnaryExpr.State>() {

		public Object retrieve(UnaryExpr.State state) {
			return state.operator;
		}

		public UnaryExpr.State rebuildParentState(UnaryExpr.State state, Object value) {
			return state.withOperator((UnaryOp) value);
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

	public static class State extends SNodeState<State> {

		public final UnaryOp operator;

		public final STree<Expr.State> expr;

		State(UnaryOp operator, STree<Expr.State> expr) {
			this.operator = operator;
			this.expr = expr;
		}

		public UnaryExpr.State withOperator(UnaryOp operator) {
			return new UnaryExpr.State(operator, expr);
		}

		public UnaryExpr.State withExpr(STree<Expr.State> expr) {
			return new UnaryExpr.State(operator, expr);
		}

		public STraversal<UnaryExpr.State> firstChild() {
			return null;
		}

		public STraversal<UnaryExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<UnaryExpr.State> location) {
			return new UnaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
