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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class UnaryExpr extends TreeBase<SNodeState> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public UnaryExpr instantiate(SLocation<SNodeState> location) {
			return new UnaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private UnaryExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public UnaryExpr(UnaryOp operator, Expr expr) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(operator), treesOf(expr)))));
	}

	public UnaryOp op() {
		return location.data(OPERATOR);
	}

	public UnaryExpr withOp(UnaryOp operator) {
		return location.withData(OPERATOR, operator);
	}

	public UnaryExpr withOp(Mutation<UnaryOp> mutation) {
		return location.mutateData(OPERATOR, mutation);
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

	private static final STraversal<SNodeState> EXPR = SNodeState.childTraversal(0);

	private static final int OPERATOR = 0;

	private final static LexicalShape opShape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return ((UnaryOp) tree.state.data(OPERATOR)).token;
		}
	});

	public final static LexicalShape shape = alternative(new LSCondition() {
		public boolean test(STree tree) {
			final UnaryOp op = (UnaryOp) tree.state.data(OPERATOR);
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
