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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.STraversal;

public class ForeachStmt extends TreeBase<SNodeState> implements Stmt {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public ForeachStmt instantiate(SLocation location) {
			return new ForeachStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ForeachStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(var, iterable, body)))));
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

	private static final STraversal<SNodeState> VAR = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> ITERABLE = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(2);

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
