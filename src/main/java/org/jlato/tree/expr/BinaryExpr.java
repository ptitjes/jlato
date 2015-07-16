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

public class BinaryExpr extends TreeBase<SNodeState, Expr, BinaryExpr> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public BinaryExpr instantiate(SLocation<SNodeState> location) {
			return new BinaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BinaryExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(operator), treesOf(left, right)))));
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

	private static final STraversal<SNodeState> LEFT = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> RIGHT = SNodeState.childTraversal(1);

	private static final int OPERATOR = 0;

	public final static LexicalShape shape = composite(
			child(LEFT),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((BinaryOp) tree.state.data(OPERATOR)).token;
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
