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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ForeachStmt extends TreeBase<ForeachStmt.State, Stmt, ForeachStmt> implements Stmt {

	public Kind kind() {
		return Kind.ForeachStmt;
	}

	private ForeachStmt(SLocation<ForeachStmt.State> location) {
		super(location);
	}

	public static STree<ForeachStmt.State> make(STree<VariableDeclarationExpr.State> var, STree<? extends Expr.State> iterable, STree<? extends Stmt.State> body) {
		return new STree<ForeachStmt.State>(new ForeachStmt.State(var, iterable, body));
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation<ForeachStmt.State>(make(TreeBase.<VariableDeclarationExpr.State>nodeOf(var), TreeBase.<Expr.State>nodeOf(iterable), TreeBase.<Stmt.State>nodeOf(body))));
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

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
