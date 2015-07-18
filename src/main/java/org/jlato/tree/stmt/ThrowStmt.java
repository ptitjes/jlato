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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class ThrowStmt extends TreeBase<ThrowStmt.State, Stmt, ThrowStmt> implements Stmt {

	public Kind kind() {
		return Kind.ThrowStmt;
	}

	private ThrowStmt(SLocation<ThrowStmt.State> location) {
		super(location);
	}

	public static STree<ThrowStmt.State> make(STree<Expr.State> expr) {
		return new STree<ThrowStmt.State>(new ThrowStmt.State(expr));
	}

	public ThrowStmt(Expr expr) {
		super(new SLocation<ThrowStmt.State>(make(TreeBase.<Expr.State>nodeOf(expr))));
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ThrowStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ThrowStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	private static final STraversal<ThrowStmt.State> EXPR = new STraversal<ThrowStmt.State>() {

		public STree<?> traverse(ThrowStmt.State state) {
			return state.expr;
		}

		public ThrowStmt.State rebuildParentState(ThrowStmt.State state, STree<?> child) {
			return state.withExpr((STree) child);
		}

		public STraversal<ThrowStmt.State> leftSibling(ThrowStmt.State state) {
			return null;
		}

		public STraversal<ThrowStmt.State> rightSibling(ThrowStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Throw), child(EXPR), token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> expr;

		State(STree<Expr.State> expr) {
			this.expr = expr;
		}

		public ThrowStmt.State withExpr(STree<Expr.State> expr) {
			return new ThrowStmt.State(expr);
		}

		public STraversal<ThrowStmt.State> firstChild() {
			return EXPR;
		}

		public STraversal<ThrowStmt.State> lastChild() {
			return EXPR;
		}

		public Tree instantiate(SLocation<ThrowStmt.State> location) {
			return new ThrowStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ThrowStmt;
		}
	}
}
