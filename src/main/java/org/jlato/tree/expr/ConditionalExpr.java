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
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ConditionalExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConditionalExpr instantiate(SLocation location) {
			return new ConditionalExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ConditionalExpr(SLocation location) {
		super(location);
	}

	public ConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(condition, thenExpr, elseExpr)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public ConditionalExpr withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public ConditionalExpr withCondition(Rewrite<Expr> condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Expr thenExpr() {
		return location.nodeChild(THEN_EXPR);
	}

	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.nodeWithChild(THEN_EXPR, thenExpr);
	}

	public ConditionalExpr withThenExpr(Rewrite<Expr> thenExpr) {
		return location.nodeWithChild(THEN_EXPR, thenExpr);
	}

	public Expr elseExpr() {
		return location.nodeChild(ELSE_EXPR);
	}

	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.nodeWithChild(ELSE_EXPR, elseExpr);
	}

	public ConditionalExpr withElseExpr(Rewrite<Expr> elseExpr) {
		return location.nodeWithChild(ELSE_EXPR, elseExpr);
	}

	private static final int CONDITION = 0;
	private static final int THEN_EXPR = 1;
	private static final int ELSE_EXPR = 2;

	public final static LexicalShape shape = composite(
			child(CONDITION),
			token(LToken.QuestionMark).withSpacing(space(), space()),
			child(THEN_EXPR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ELSE_EXPR)
	);
}
