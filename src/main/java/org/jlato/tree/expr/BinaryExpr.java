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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class BinaryExpr extends TreeBase<BinaryExpr.State, Expr, BinaryExpr> implements Expr {

	public Kind kind() {
		return Kind.BinaryExpr;
	}

	private BinaryExpr(SLocation<BinaryExpr.State> location) {
		super(location);
	}

	public static STree<BinaryExpr.State> make(STree<? extends Expr.State> left, BinaryOp operator, STree<? extends Expr.State> right) {
		return new STree<BinaryExpr.State>(new BinaryExpr.State(left, operator, right));
	}

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(new SLocation<BinaryExpr.State>(make(TreeBase.<Expr.State>nodeOf(left), operator, TreeBase.<Expr.State>nodeOf(right))));
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
		return location.safeProperty(OPERATOR);
	}

	public BinaryExpr withOp(BinaryOp operator) {
		return location.safePropertyReplace(OPERATOR, operator);
	}

	public BinaryExpr withOp(Mutation<BinaryOp> mutation) {
		return location.safePropertyMutate(OPERATOR, mutation);
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

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> left;

		public final BinaryOp operator;

		public final STree<? extends Expr.State> right;

		State(STree<? extends Expr.State> left, BinaryOp operator, STree<? extends Expr.State> right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		public BinaryExpr.State withLeft(STree<? extends Expr.State> left) {
			return new BinaryExpr.State(left, operator, right);
		}

		public BinaryExpr.State withOperator(BinaryOp operator) {
			return new BinaryExpr.State(left, operator, right);
		}

		public BinaryExpr.State withRight(STree<? extends Expr.State> right) {
			return new BinaryExpr.State(left, operator, right);
		}

		@Override
		public Kind kind() {
			return Kind.BinaryExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<BinaryExpr.State> location) {
			return new BinaryExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return LEFT;
		}

		@Override
		public STraversal lastChild() {
			return RIGHT;
		}
	}

	private static STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr> LEFT = new STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(BinaryExpr.State state) {
			return state.left;
		}

		@Override
		protected BinaryExpr.State doRebuildParentState(BinaryExpr.State state, STree<Expr.State> child) {
			return state.withLeft(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return RIGHT;
		}
	};

	private static STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr> RIGHT = new STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(BinaryExpr.State state) {
			return state.right;
		}

		@Override
		protected BinaryExpr.State doRebuildParentState(BinaryExpr.State state, STree<Expr.State> child) {
			return state.withRight(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return LEFT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<BinaryExpr.State, BinaryOp> OPERATOR = new STypeSafeProperty<BinaryExpr.State, BinaryOp>() {

		@Override
		protected BinaryOp doRetrieve(BinaryExpr.State state) {
			return state.operator;
		}

		@Override
		protected BinaryExpr.State doRebuildParentState(BinaryExpr.State state, BinaryOp value) {
			return state.withOperator(value);
		}
	};

	public final static LexicalShape shape = composite(
			child(LEFT),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).operator.token;
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
}
