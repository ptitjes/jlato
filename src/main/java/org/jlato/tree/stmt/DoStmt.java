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
import static org.jlato.printer.SpacingConstraint.space;

public class DoStmt extends TreeBase<SNodeState, Stmt, DoStmt> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public DoStmt instantiate(SLocation<SNodeState> location) {
			return new DoStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private DoStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public DoStmt(Stmt body, Expr condition) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(body, condition)))));
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public DoStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public DoStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public DoStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public DoStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> CONDITION = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.Do).withSpacingAfter(space()),
			child(BODY),
			token(LToken.While).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);
}
