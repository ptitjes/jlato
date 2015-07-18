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
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;

public class ForeachStmt extends TreeBase<ForeachStmt.State, Stmt, ForeachStmt> implements Stmt {

	public Kind kind() {
		return Kind.ForeachStmt;
	}

	private ForeachStmt(SLocation<ForeachStmt.State> location) {
		super(location);
	}

	public static STree<ForeachStmt.State> make(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		return new STree<ForeachStmt.State>(new ForeachStmt.State(TreeBase.<VariableDeclarationExpr.State>nodeOf(var), TreeBase.<Expr.State>nodeOf(iterable), TreeBase.<Stmt.State>nodeOf(body)));
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation<ForeachStmt.State>(make(var, iterable, body)));
	}

	public VariableDeclarationExpr var() {
		return location.safeTraversal(VAR);
	}

	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.safeTraversalReplace(VAR, var);
	}

	public ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation) {
		return location.safeTraversalMutate(VAR, mutation);
	}

	public Expr iterable() {
		return location.safeTraversal(ITERABLE);
	}

	public ForeachStmt withIterable(Expr iterable) {
		return location.safeTraversalReplace(ITERABLE, iterable);
	}

	public ForeachStmt withIterable(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(ITERABLE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public ForeachStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ForeachStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<ForeachStmt.State> VAR = new STraversal<ForeachStmt.State>() {

		public STree<?> traverse(ForeachStmt.State state) {
			return state.var;
		}

		public ForeachStmt.State rebuildParentState(ForeachStmt.State state, STree<?> child) {
			return state.withVar((STree) child);
		}

		public STraversal<ForeachStmt.State> leftSibling(ForeachStmt.State state) {
			return null;
		}

		public STraversal<ForeachStmt.State> rightSibling(ForeachStmt.State state) {
			return ITERABLE;
		}
	};
	private static final STraversal<ForeachStmt.State> ITERABLE = new STraversal<ForeachStmt.State>() {

		public STree<?> traverse(ForeachStmt.State state) {
			return state.iterable;
		}

		public ForeachStmt.State rebuildParentState(ForeachStmt.State state, STree<?> child) {
			return state.withIterable((STree) child);
		}

		public STraversal<ForeachStmt.State> leftSibling(ForeachStmt.State state) {
			return VAR;
		}

		public STraversal<ForeachStmt.State> rightSibling(ForeachStmt.State state) {
			return BODY;
		}
	};
	private static final STraversal<ForeachStmt.State> BODY = new STraversal<ForeachStmt.State>() {

		public STree<?> traverse(ForeachStmt.State state) {
			return state.body;
		}

		public ForeachStmt.State rebuildParentState(ForeachStmt.State state, STree<?> child) {
			return state.withBody((STree) child);
		}

		public STraversal<ForeachStmt.State> leftSibling(ForeachStmt.State state) {
			return ITERABLE;
		}

		public STraversal<ForeachStmt.State> rightSibling(ForeachStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);

	public static class State extends SNodeState<State> {

		public final STree<VariableDeclarationExpr.State> var;

		public final STree<Expr.State> iterable;

		public final STree<Stmt.State> body;

		State(STree<VariableDeclarationExpr.State> var, STree<Expr.State> iterable, STree<Stmt.State> body) {
			this.var = var;
			this.iterable = iterable;
			this.body = body;
		}

		public ForeachStmt.State withVar(STree<VariableDeclarationExpr.State> var) {
			return new ForeachStmt.State(var, iterable, body);
		}

		public ForeachStmt.State withIterable(STree<Expr.State> iterable) {
			return new ForeachStmt.State(var, iterable, body);
		}

		public ForeachStmt.State withBody(STree<Stmt.State> body) {
			return new ForeachStmt.State(var, iterable, body);
		}

		public STraversal<ForeachStmt.State> firstChild() {
			return null;
		}

		public STraversal<ForeachStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ForeachStmt.State> location) {
			return new ForeachStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ForeachStmt;
		}
	}
}
