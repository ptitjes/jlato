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

public class AssignExpr extends TreeBase<AssignExpr.State, Expr, AssignExpr> implements Expr {

	public Kind kind() {
		return Kind.AssignExpr;
	}

	private AssignExpr(SLocation<AssignExpr.State> location) {
		super(location);
	}

	public static STree<AssignExpr.State> make(STree<? extends Expr.State> target, AssignOp op, STree<? extends Expr.State> value) {
		return new STree<AssignExpr.State>(new AssignExpr.State(target, op, value));
	}

	public AssignExpr(Expr target, AssignOp op, Expr value) {
		super(new SLocation<AssignExpr.State>(make(TreeBase.<Expr.State>nodeOf(target), op, TreeBase.<Expr.State>nodeOf(value))));
	}

	public Expr target() {
		return location.safeTraversal(TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.safeTraversalReplace(TARGET, target);
	}

	public AssignExpr withTarget(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(TARGET, mutation);
	}

	public AssignOp op() {
		return location.safeProperty(OP);
	}

	public AssignExpr withOp(AssignOp op) {
		return location.safePropertyReplace(OP, op);
	}

	public AssignExpr withOp(Mutation<AssignOp> mutation) {
		return location.safePropertyMutate(OP, mutation);
	}

	public Expr value() {
		return location.safeTraversal(VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.safeTraversalReplace(VALUE, value);
	}

	public AssignExpr withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(VALUE, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<? extends Expr.State> target;

		public final AssignOp op;

		public final STree<? extends Expr.State> value;

		State(STree<? extends Expr.State> target, AssignOp op, STree<? extends Expr.State> value) {
			this.target = target;
			this.op = op;
			this.value = value;
		}

		public AssignExpr.State withTarget(STree<? extends Expr.State> target) {
			return new AssignExpr.State(target, op, value);
		}

		public AssignExpr.State withOp(AssignOp operator) {
			return new AssignExpr.State(target, operator, value);
		}

		public AssignExpr.State withValue(STree<? extends Expr.State> value) {
			return new AssignExpr.State(target, op, value);
		}

		@Override
		public Kind kind() {
			return Kind.AssignExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<AssignExpr.State> location) {
			return new AssignExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TARGET;
		}

		@Override
		public STraversal lastChild() {
			return VALUE;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			AssignExpr.State state = (AssignExpr.State) o;
			if (!op.equals(state.op))
				return false;
			if (!target.equals(state.target))
				return false;
			if (!value.equals(state.value))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + op.hashCode();
			result = 37 * result + target.hashCode();
			result = 37 * result + value.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<AssignExpr.State, Expr.State, Expr> TARGET = new STypeSafeTraversal<AssignExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(AssignExpr.State state) {
			return state.target;
		}

		@Override
		protected AssignExpr.State doRebuildParentState(AssignExpr.State state, STree<Expr.State> child) {
			return state.withTarget(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return VALUE;
		}
	};

	private static STypeSafeTraversal<AssignExpr.State, Expr.State, Expr> VALUE = new STypeSafeTraversal<AssignExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(AssignExpr.State state) {
			return state.value;
		}

		@Override
		protected AssignExpr.State doRebuildParentState(AssignExpr.State state, STree<Expr.State> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TARGET;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<AssignExpr.State, AssignOp> OP = new STypeSafeProperty<AssignExpr.State, AssignOp>() {

		@Override
		protected AssignOp doRetrieve(AssignExpr.State state) {
			return state.op;
		}

		@Override
		protected AssignExpr.State doRebuildParentState(AssignExpr.State state, AssignOp value) {
			return state.withOp(value);
		}
	};

	public final static LexicalShape shape = composite(
			child(TARGET),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).op.token;
				}
			}).withSpacing(space(), space()),
			child(VALUE)
	);

	public enum AssignOp {
		Normal(LToken.Assign),
		Plus(LToken.AssignPlus),
		Minus(LToken.AssignMinus),
		Times(LToken.AssignTimes),
		Divide(LToken.AssignDivide),
		And(LToken.AssignAnd),
		Or(LToken.AssignOr),
		XOr(LToken.AssignXOr),
		Remainder(LToken.AssignRemainder),
		LeftShift(LToken.AssignLShift),
		RightSignedShift(LToken.AssignRSignedShift),
		RightUnsignedShift(LToken.AssignRUnsignedShift),
		// Keep last comma
		;

		protected final LToken token;

		AssignOp(LToken token) {
			this.token = token;
		}
	}
}
