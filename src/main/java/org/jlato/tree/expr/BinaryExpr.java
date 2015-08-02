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

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class BinaryExpr extends TreeBase<BinaryExpr.State, Expr, BinaryExpr> implements Expr {

	public Kind kind() {
		return Kind.BinaryExpr;
	}

	private BinaryExpr(SLocation<BinaryExpr.State> location) {
		super(location);
	}

	public static STree<BinaryExpr.State> make(STree<? extends Expr.State> left, BinaryOp op, STree<? extends Expr.State> right) {
		return new STree<BinaryExpr.State>(new BinaryExpr.State(left, op, right));
	}

	public BinaryExpr(Expr left, BinaryOp op, Expr right) {
		super(new SLocation<BinaryExpr.State>(make(TreeBase.<Expr.State>treeOf(left), op, TreeBase.<Expr.State>treeOf(right))));
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
		return location.safeProperty(OP);
	}

	public BinaryExpr withOp(BinaryOp op) {
		return location.safePropertyReplace(OP, op);
	}

	public BinaryExpr withOp(Mutation<BinaryOp> mutation) {
		return location.safePropertyMutate(OP, mutation);
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

		public final BinaryOp op;

		public final STree<? extends Expr.State> right;

		State(STree<? extends Expr.State> left, BinaryOp op, STree<? extends Expr.State> right) {
			this.left = left;
			this.op = op;
			this.right = right;
		}

		public BinaryExpr.State withLeft(STree<? extends Expr.State> left) {
			return new BinaryExpr.State(left, op, right);
		}

		public BinaryExpr.State withOp(BinaryOp op) {
			return new BinaryExpr.State(left, op, right);
		}

		public BinaryExpr.State withRight(STree<? extends Expr.State> right) {
			return new BinaryExpr.State(left, op, right);
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
		public Iterable<SProperty> allProperties() {
			return Collections.<SProperty>singleton(OP);
		}

		@Override
		public STraversal firstChild() {
			return LEFT;
		}

		@Override
		public STraversal lastChild() {
			return RIGHT;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (left == null ? state.left != null : !left.equals(state.left))
				return false;
			if (op == null ? state.op != null : !op.equals(state.op))
				return false;
			if (right == null ? state.right != null : !right.equals(state.right))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (left != null) result = 37 * result + left.hashCode();
			if (op != null) result = 37 * result + op.hashCode();
			if (right != null) result = 37 * result + right.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr> LEFT = new STypeSafeTraversal<BinaryExpr.State, Expr.State, Expr>() {

		@Override
		public STree<?> doTraverse(BinaryExpr.State state) {
			return state.left;
		}

		@Override
		public BinaryExpr.State doRebuildParentState(BinaryExpr.State state, STree<Expr.State> child) {
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
		public STree<?> doTraverse(BinaryExpr.State state) {
			return state.right;
		}

		@Override
		public BinaryExpr.State doRebuildParentState(BinaryExpr.State state, STree<Expr.State> child) {
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

	private static STypeSafeProperty<BinaryExpr.State, BinaryOp> OP = new STypeSafeProperty<BinaryExpr.State, BinaryOp>() {

		@Override
		public BinaryOp doRetrieve(BinaryExpr.State state) {
			return state.op;
		}

		@Override
		public BinaryExpr.State doRebuildParentState(BinaryExpr.State state, BinaryOp value) {
			return state.withOp(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(LEFT),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					final BinaryOp op = ((State) tree.state).op;
					switch (op) {
						case Or:
							return LToken.Or;
						case And:
							return LToken.And;
						case BinOr:
							return LToken.BinOr;
						case BinAnd:
							return LToken.BinAnd;
						case XOr:
							return LToken.XOr;
						case Equal:
							return LToken.Equal;
						case NotEqual:
							return LToken.NotEqual;
						case Less:
							return LToken.Less;
						case Greater:
							return LToken.Greater;
						case LessOrEqual:
							return LToken.LessOrEqual;
						case GreaterOrEqual:
							return LToken.GreaterOrEqual;
						case LeftShift:
							return LToken.LShift;
						case RightSignedShift:
							return LToken.RSignedShift;
						case RightUnsignedShift:
							return LToken.RUnsignedShift;
						case Plus:
							return LToken.Plus;
						case Minus:
							return LToken.Minus;
						case Times:
							return LToken.Times;
						case Divide:
							return LToken.Divide;
						case Remainder:
							return LToken.Remainder;
						default:
							// Can't happen by definition of enum
							throw new IllegalStateException();
					}
				}
			}).withSpacing(space(), space()),
			child(RIGHT)
	);
}
