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

public class ThrowStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ThrowStmt instantiate(SLocation location) {
			return new ThrowStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ThrowStmt(SLocation location) {
		super(location);
	}

	public ThrowStmt(Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(expr)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public ThrowStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int EXPR = 0;

	public final static LexicalShape shape = composite(
			token(LToken.Throw), child(EXPR), token(LToken.SemiColon)
	);
}
