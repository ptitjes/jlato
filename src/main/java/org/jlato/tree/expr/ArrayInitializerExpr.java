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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class ArrayInitializerExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayInitializerExpr instantiate(SLocation location) {
			return new ArrayInitializerExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayInitializerExpr(SLocation location) {
		super(location);
	}

	public ArrayInitializerExpr(NodeList<Expr> values) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(values)))));
	}

	public NodeList<Expr> values() {
		return location.nodeChild(VALUES);
	}

	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.nodeWithChild(VALUES, values);
	}

	private static final int VALUES = 0;

	public final static LexicalShape shape = composite(
			nonEmptyChildren(VALUES,
					composite(
							token(LToken.BraceLeft).withSpacingAfter(space()),
							children(VALUES, token(LToken.Comma)),
							token(LToken.BraceRight).withSpacingBefore(space())
					),
					composite(token(LToken.BraceLeft), token(LToken.BraceRight))
			)
	);
}
