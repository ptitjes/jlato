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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class IfStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public IfStmt instantiate(SLocation location) {
			return new IfStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private IfStmt(SLocation location) {
		super(location);
	}

	public IfStmt(Expr condition, Stmt thenStmt, Stmt elseStmt) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(condition, thenStmt, elseStmt)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Stmt thenStmt() {
		return location.nodeChild(THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.nodeWithChild(THEN_STMT, thenStmt);
	}

	public Stmt elseStmt() {
		return location.nodeChild(ELSE_STMT);
	}

	public IfStmt withElseStmt(Stmt elseStmt) {
		return location.nodeWithChild(ELSE_STMT, elseStmt);
	}

	private static final int CONDITION = 0;
	private static final int THEN_STMT = 1;
	private static final int ELSE_STMT = 2;

	public final static LexicalShape shape = composite(
			token(LToken.If), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(THEN_STMT),
			nonNullChild(ELSE_STMT, composite(
					token(LToken.Else).withSpacing(space(), space()),
					child(ELSE_STMT)
			))
	);
}
