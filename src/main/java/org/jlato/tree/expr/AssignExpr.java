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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class AssignExpr extends TreeBase<AssignExpr.State, Expr, AssignExpr> implements Expr {

	public Kind kind() {
		return Kind.AssignExpr;
	}

	private AssignExpr(SLocation<AssignExpr.State> location) {
		super(location);
	}

	public static STree<AssignExpr.State> make(STree<? extends Expr.State> target, AssignOp operator, STree<? extends Expr.State> value) {
		return new STree<AssignExpr.State>(new AssignExpr.State(target, operator, value));
	}

	public AssignExpr(Expr target, AssignOp operator, Expr value) {
		super(new SLocation<AssignExpr.State>(make(TreeBase.<Expr.State>nodeOf(target), operator, TreeBase.<Expr.State>nodeOf(value))));
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
		return location.safeProperty(OPERATOR);
	}

	public AssignExpr withOp(AssignOp operator) {
		return location.safePropertyReplace(OPERATOR, operator);
	}

	public AssignExpr withOp(Mutation<AssignOp> mutation) {
		return location.safePropertyMutate(OPERATOR, mutation);
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

	public final static LexicalShape shape = composite(
			child(TARGET),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).operator.token;
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
