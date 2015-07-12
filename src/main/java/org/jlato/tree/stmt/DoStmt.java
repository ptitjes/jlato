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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class DoStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public DoStmt instantiate(SLocation location) {
			return new DoStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private DoStmt(SLocation location) {
		super(location);
	}

	public DoStmt(Stmt body, Expr condition) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(body, condition)))));
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public DoStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public DoStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	private static final int BODY = 0;
	private static final int CONDITION = 1;

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
