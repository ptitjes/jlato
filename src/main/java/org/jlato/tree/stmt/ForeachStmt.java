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
import org.jlato.tree.expr.VariableDeclarationExpr;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class ForeachStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ForeachStmt instantiate(SLocation location) {
			return new ForeachStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ForeachStmt(SLocation location) {
		super(location);
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(var, iterable, body)))));
	}

	public VariableDeclarationExpr var() {
		return location.nodeChild(VAR);
	}

	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.nodeWithChild(VAR, var);
	}

	public Expr iterable() {
		return location.nodeChild(ITERABLE);
	}

	public ForeachStmt withIterable(Expr iterable) {
		return location.nodeWithChild(ITERABLE, iterable);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public ForeachStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int VAR = 0;
	private static final int ITERABLE = 1;
	private static final int BODY = 2;

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
