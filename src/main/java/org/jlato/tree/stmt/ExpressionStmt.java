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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class ExpressionStmt extends TreeBase<ExpressionStmt.State, Stmt, ExpressionStmt> implements Stmt {

	public final static SKind<ExpressionStmt.State> kind = new SKind<ExpressionStmt.State>() {

	};

	private ExpressionStmt(SLocation<ExpressionStmt.State> location) {
		super(location);
	}

	public static STree<ExpressionStmt.State> make(Expr expr) {
		return new STree<ExpressionStmt.State>(kind, new ExpressionStmt.State(TreeBase.<Expr.State>nodeOf(expr)));
	}

	public ExpressionStmt(Expr expr) {
		super(new SLocation<ExpressionStmt.State>(make(expr)));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ExpressionStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ExpressionStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	private static final STraversal<ExpressionStmt.State> EXPR = new STraversal<ExpressionStmt.State>() {

		public STree<?> traverse(ExpressionStmt.State state) {
			return state.expr;
		}

		public ExpressionStmt.State rebuildParentState(ExpressionStmt.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<ExpressionStmt.State> leftSibling(ExpressionStmt.State state) {
			return null;
		}

		public STraversal<ExpressionStmt.State> rightSibling(ExpressionStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(EXPR), token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> expr;

		State(STree<Expr.State> expr) {
			this.expr = expr;
		}

		public ExpressionStmt.State withExpr(STree<Expr.State> expr) {
			return new ExpressionStmt.State(expr);
		}

		public STraversal<ExpressionStmt.State> firstChild() {
			return null;
		}

		public STraversal<ExpressionStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ExpressionStmt.State> location) {
			return new ExpressionStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
