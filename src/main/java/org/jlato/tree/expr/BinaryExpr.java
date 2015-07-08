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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class BinaryExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BinaryExpr instantiate(SLocation location) {
			return new BinaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BinaryExpr(SLocation location) {
		super(location);
	}

	public BinaryExpr(Expr left, BinaryOp operator, Expr right) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(left, right), dataOf(operator)))));
	}

	public Expr left() {
		return location.nodeChild(LEFT);
	}

	public BinaryExpr withLeft(Expr left) {
		return location.nodeWithChild(LEFT, left);
	}

	public BinaryOp op() {
		return location.nodeData(OPERATOR);
	}

	public BinaryExpr withOp(BinaryOp operator) {
		return location.nodeWithData(OPERATOR, operator);
	}

	public Expr right() {
		return location.nodeChild(RIGHT);
	}

	public BinaryExpr withRight(Expr right) {
		return location.nodeWithChild(RIGHT, right);
	}

	private static final int LEFT = 0;
	private static final int RIGHT = 1;

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
