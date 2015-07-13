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
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SuperExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SuperExpr instantiate(SLocation location) {
			return new SuperExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SuperExpr(SLocation location) {
		super(location);
	}

	public SuperExpr(Expr classExpr) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(classExpr)))));
	}

	public Expr classExpr() {
		return location.nodeChild(CLASS_EXPR);
	}

	public SuperExpr withClassExpr(Expr classExpr) {
		return location.nodeWithChild(CLASS_EXPR, classExpr);
	}

	public SuperExpr withClassExpr(Mutation<Expr> classExpr) {
		return location.nodeWithChild(CLASS_EXPR, classExpr);
	}

	private static final int CLASS_EXPR = 0;

	public final static LexicalShape shape = composite(
			nonNullChild(CLASS_EXPR, composite(child(CLASS_EXPR), token(LToken.Dot))),
			token(LToken.Super)
	);
}
