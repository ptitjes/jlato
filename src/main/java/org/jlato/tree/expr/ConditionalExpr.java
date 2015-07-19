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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ConditionalExpr extends TreeBase<ConditionalExpr.State, Expr, ConditionalExpr> implements Expr {

	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	private ConditionalExpr(SLocation<ConditionalExpr.State> location) {
		super(location);
	}

	public static STree<ConditionalExpr.State> make(STree<? extends Expr.State> condition, STree<? extends Expr.State> thenExpr, STree<? extends Expr.State> elseExpr) {
		return new STree<ConditionalExpr.State>(new ConditionalExpr.State(condition, thenExpr, elseExpr));
	}

	public ConditionalExpr(Expr condition, Expr thenExpr, Expr elseExpr) {
		super(new SLocation<ConditionalExpr.State>(make(TreeBase.<Expr.State>nodeOf(condition), TreeBase.<Expr.State>nodeOf(thenExpr), TreeBase.<Expr.State>nodeOf(elseExpr))));
	}

	public Expr condition() {
		return location.safeTraversal(CONDITION);
	}

	public ConditionalExpr withCondition(Expr condition) {
		return location.safeTraversalReplace(CONDITION, condition);
	}

	public ConditionalExpr withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(CONDITION, mutation);
	}

	public Expr thenExpr() {
		return location.safeTraversal(THEN_EXPR);
	}

	public ConditionalExpr withThenExpr(Expr thenExpr) {
		return location.safeTraversalReplace(THEN_EXPR, thenExpr);
	}

	public ConditionalExpr withThenExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(THEN_EXPR, mutation);
	}

	public Expr elseExpr() {
		return location.safeTraversal(ELSE_EXPR);
	}

	public ConditionalExpr withElseExpr(Expr elseExpr) {
		return location.safeTraversalReplace(ELSE_EXPR, elseExpr);
	}

	public ConditionalExpr withElseExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(ELSE_EXPR, mutation);
	}

	public final static LexicalShape shape = composite(
			child(CONDITION),
			token(LToken.QuestionMark).withSpacing(space(), space()),
			child(THEN_EXPR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ELSE_EXPR)
	);
}
