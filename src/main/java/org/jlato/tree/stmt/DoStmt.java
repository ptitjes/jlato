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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class DoStmt extends TreeBase<DoStmt.State, Stmt, DoStmt> implements Stmt {

	public final static SKind<DoStmt.State> kind = new SKind<DoStmt.State>() {
		public DoStmt instantiate(SLocation<DoStmt.State> location) {
			return new DoStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private DoStmt(SLocation<DoStmt.State> location) {
		super(location);
	}

	public static STree<DoStmt.State> make(Stmt body, Expr condition) {
		return new STree<DoStmt.State>(kind, new DoStmt.State(TreeBase.<Stmt.State>nodeOf(body), TreeBase.<Expr.State>nodeOf(condition)));
	}

	public DoStmt(Stmt body, Expr condition) {
		super(new SLocation<DoStmt.State>(make(body, condition)));
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

	private static final STraversal<DoStmt.State> BODY = SNodeState.childTraversal(0);
	private static final STraversal<DoStmt.State> CONDITION = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.Do).withSpacingAfter(space()),
			child(BODY),
			token(LToken.While).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<Stmt.State> body;

		public final STree<Expr.State> condition;

		State(STree<Stmt.State> body, STree<Expr.State> condition) {
			this.body = body;
			this.condition = condition;
		}

		public DoStmt.State withBody(STree<Stmt.State> body) {
			return new DoStmt.State(body, condition);
		}

		public DoStmt.State withCondition(STree<Expr.State> condition) {
			return new DoStmt.State(body, condition);
		}

		public STraversal<DoStmt.State> firstChild() {
			return null;
		}

		public STraversal<DoStmt.State> lastChild() {
			return null;
		}
	}
}
