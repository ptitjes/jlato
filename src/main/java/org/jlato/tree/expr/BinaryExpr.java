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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class BinaryExpr extends TreeBase<BinaryExpr.State, Expr, BinaryExpr> implements Expr {

	public final static SKind<BinaryExpr.State> kind = new SKind<BinaryExpr.State>() {
		public BinaryExpr instantiate(SLocation<BinaryExpr.State> location) {
			return new BinaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BinaryExpr(SLocation<BinaryExpr.State> location) {
		super(location);
	}

	public static STree<BinaryExpr.State> make(Expr left, BinaryOp operator, Expr right) {
		return new STree<BinaryExpr.State>(kind, new BinaryExpr.State(TreeBase.<Expr.State>nodeOf(left), operator, TreeBase.<Expr.State>nodeOf(right)));
	}

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(new SLocation<BinaryExpr.State>(make(left, operator, right)));
	}

	public Expr left() {
		return location.safeTraversal(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.safeTraversalReplace(LEFT, left);
	}

	public BinaryExpr withLeft(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(LEFT, mutation);
	}

	public BinaryOp op() {
		return location.data(OPERATOR);
	}

	public BinaryExpr withOp(BinaryOp operator) {
		return location.withData(OPERATOR, operator);
	}

	public BinaryExpr withOp(Mutation<BinaryOp> mutation) {
		return location.mutateData(OPERATOR, mutation);
	}

	public Expr right() {
		return location.safeTraversal(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.safeTraversalReplace(RIGHT, right);
	}

	public BinaryExpr withRight(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(RIGHT, mutation);
	}

	private static final STraversal<BinaryExpr.State> LEFT = new STraversal<BinaryExpr.State>() {

		public STree<?> traverse(BinaryExpr.State state) {
			return state.left;
		}

		public BinaryExpr.State rebuildParentState(BinaryExpr.State state, STree<?> child) {
			return state.withLeft((STree) child);
		}

		public STraversal<BinaryExpr.State> leftSibling(BinaryExpr.State state) {
			return null;
		}

		public STraversal<BinaryExpr.State> rightSibling(BinaryExpr.State state) {
			return RIGHT;
		}
	};
	private static final STraversal<BinaryExpr.State> RIGHT = new STraversal<BinaryExpr.State>() {

		public STree<?> traverse(BinaryExpr.State state) {
			return state.right;
		}

		public BinaryExpr.State rebuildParentState(BinaryExpr.State state, STree<?> child) {
			return state.withRight((STree) child);
		}

		public STraversal<BinaryExpr.State> leftSibling(BinaryExpr.State state) {
			return LEFT;
		}

		public STraversal<BinaryExpr.State> rightSibling(BinaryExpr.State state) {
			return null;
		}
	};

	private static final SProperty<BinaryExpr.State> OPERATOR = new SProperty<BinaryExpr.State>() {

		public Object retrieve(BinaryExpr.State state) {
			return state.operator;
		}

		public BinaryExpr.State rebuildParentState(BinaryExpr.State state, Object value) {
			return state.withOperator((BinaryOp) value);
		}
	};

	public final static LexicalShape shape = composite(
			child(LEFT),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State)tree.state).operator.token;
				}
			}).withSpacing(space(), space()),
			child(RIGHT)
	);

	public enum BinaryOp {
		Or(LToken.Or),
		And(LToken.And),
		BinOr(LToken.BinOr),
		BinAnd(LToken.BinAnd),
		XOr(LToken.XOr),
		Equal(LToken.Equal),
		NotEqual(LToken.NotEqual),
		Less(LToken.Less),
		Greater(LToken.Greater),
		LessOrEqual(LToken.LessOrEqual),
		GreaterOrEqual(LToken.GreaterOrEqual),
		LeftShift(LToken.LShift),
		RightSignedShift(LToken.RSignedShift),
		RightUnsignedShift(LToken.RUnsignedShift),
		Plus(LToken.Plus),
		Minus(LToken.Minus),
		Times(LToken.Times),
		Divide(LToken.Divide),
		Remainder(LToken.Remainder),
		// Keep last comma
		;

		protected final LToken token;

		BinaryOp(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> left;

		public final BinaryOp operator;

		public final STree<Expr.State> right;

		State(STree<Expr.State> left, BinaryOp operator, STree<Expr.State> right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		public BinaryExpr.State withLeft(STree<Expr.State> left) {
			return new BinaryExpr.State(left, operator, right);
		}

		public BinaryExpr.State withOperator(BinaryOp operator) {
			return new BinaryExpr.State(left, operator, right);
		}

		public BinaryExpr.State withRight(STree<Expr.State> right) {
			return new BinaryExpr.State(left, operator, right);
		}

		public STraversal<BinaryExpr.State> firstChild() {
			return null;
		}

		public STraversal<BinaryExpr.State> lastChild() {
			return null;
		}
	}
}
