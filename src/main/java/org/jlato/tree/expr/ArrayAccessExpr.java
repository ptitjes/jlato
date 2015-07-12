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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ArrayAccessExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayAccessExpr instantiate(SLocation location) {
			return new ArrayAccessExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayAccessExpr(SLocation location) {
		super(location);
	}

	public ArrayAccessExpr(Expr name, Expr index) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(name, index)))));
	}

	public Expr name() {
		return location.nodeChild(NAME);
	}

	public ArrayAccessExpr withName(Expr name) {
		return location.nodeWithChild(NAME, name);
	}

	public Expr index() {
		return location.nodeChild(INDEX);
	}

	public ArrayAccessExpr withIndex(Expr index) {
		return location.nodeWithChild(INDEX, index);
	}

	private static final int NAME = 0;
	private static final int INDEX = 1;

	public final static LexicalShape shape = composite(
			child(NAME),
			token(LToken.BracketLeft), child(INDEX), token(LToken.BracketRight)
	);
}
