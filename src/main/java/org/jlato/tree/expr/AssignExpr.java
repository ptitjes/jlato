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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class AssignExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AssignExpr instantiate(SLocation location) {
			return new AssignExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AssignExpr(SLocation location) {
		super(location);
	}

	public AssignExpr(Expr target, AssignOp operator, Expr value) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(target, value), dataOf(operator)))));
	}

	public Expr target() {
		return location.nodeChild(TARGET);
	}

	public AssignExpr withTarget(Expr target) {
		return location.nodeWithChild(TARGET, target);
	}

	public AssignOp op() {
		return location.data(OPERATOR);
	}

	public AssignExpr withOp(AssignOp operator) {
		return location.withData(OPERATOR, operator);
	}

	public Expr value() {
		return location.nodeChild(VALUE);
	}

	public AssignExpr withValue(Expr value) {
		return location.nodeWithChild(VALUE, value);
	}

	private static final int TARGET = 0;
	private static final int VALUE = 1;

	private static final int OPERATOR = 0;

	public final static LexicalShape shape = composite(
			child(TARGET),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((AssignOp) tree.state.data(OPERATOR)).token;
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
