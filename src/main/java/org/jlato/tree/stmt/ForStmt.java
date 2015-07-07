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
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class ForStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ForStmt instantiate(SLocation location) {
			return new ForStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ForStmt(SLocation location) {
		super(location);
	}

	public ForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(init, compare, update, body)))));
	}

	public NodeList<Expr> init() {
		return location.nodeChild(INIT);
	}

	public ForStmt withInit(NodeList<Expr> init) {
		return location.nodeWithChild(INIT, init);
	}

	public Expr compare() {
		return location.nodeChild(COMPARE);
	}

	public ForStmt withCompare(Expr compare) {
		return location.nodeWithChild(COMPARE, compare);
	}

	public NodeList<Expr> update() {
		return location.nodeChild(UPDATE);
	}

	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.nodeWithChild(UPDATE, update);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public ForStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int INIT = 0;
	private static final int COMPARE = 1;
	private static final int UPDATE = 2;
	private static final int BODY = 3;

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			children(INIT, token(LToken.Comma)),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(COMPARE),
			token(LToken.SemiColon).withSpacingAfter(space()),
			children(UPDATE, token(LToken.Comma)),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
