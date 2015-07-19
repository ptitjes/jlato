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
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class WhileStmt extends TreeBase<WhileStmt.State, Stmt, WhileStmt> implements Stmt {

	public Kind kind() {
		return Kind.WhileStmt;
	}

	private WhileStmt(SLocation<WhileStmt.State> location) {
		super(location);
	}

	public static STree<WhileStmt.State> make(STree<Expr.State> condition, STree<Stmt.State> body) {
		return new STree<WhileStmt.State>(new WhileStmt.State(condition, body));
	}

	public WhileStmt(Expr condition, Stmt body) {
		super(new SLocation<WhileStmt.State>(make(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Stmt.State>nodeOf(body))));
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public WhileStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public WhileStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public WhileStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public WhileStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.While), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
