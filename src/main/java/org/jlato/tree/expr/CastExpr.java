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
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class CastExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CastExpr instantiate(SLocation location) {
			return new CastExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CastExpr(SLocation location) {
		super(location);
	}

	public CastExpr(Type type, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(type, expr)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public CastExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public CastExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int TYPE = 0;
	private static final int EXPR = 1;

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);
}
