/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDConditionalExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a conditional expression.
 */
public class SConditionalExpr extends SNode<SConditionalExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new conditional expression.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param thenExpr  the then expression child <code>BUTree</code>.
	 * @param elseExpr  the else expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a conditional expression.
	 */
	public static BUTree<SConditionalExpr> make(BUTree<? extends SExpr> condition, BUTree<? extends SExpr> thenExpr, BUTree<? extends SExpr> elseExpr) {
		return new BUTree<SConditionalExpr>(new SConditionalExpr(condition, thenExpr, elseExpr));
	}

	/**
	 * The condition of this conditional expression state.
	 */
	public final BUTree<? extends SExpr> condition;

	/**
	 * The then expression of this conditional expression state.
	 */
	public final BUTree<? extends SExpr> thenExpr;

	/**
	 * The else expression of this conditional expression state.
	 */
	public final BUTree<? extends SExpr> elseExpr;

	/**
	 * Constructs a conditional expression state.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param thenExpr  the then expression child <code>BUTree</code>.
	 * @param elseExpr  the else expression child <code>BUTree</code>.
	 */
	public SConditionalExpr(BUTree<? extends SExpr> condition, BUTree<? extends SExpr> thenExpr, BUTree<? extends SExpr> elseExpr) {
		this.condition = condition;
		this.thenExpr = thenExpr;
		this.elseExpr = elseExpr;
	}

	/**
	 * Returns the kind of this conditional expression.
	 *
	 * @return the kind of this conditional expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	/**
	 * Replaces the condition of this conditional expression state.
	 *
	 * @param condition the replacement for the condition of this conditional expression state.
	 * @return the resulting mutated conditional expression state.
	 */
	public SConditionalExpr withCondition(BUTree<? extends SExpr> condition) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	/**
	 * Replaces the then expression of this conditional expression state.
	 *
	 * @param thenExpr the replacement for the then expression of this conditional expression state.
	 * @return the resulting mutated conditional expression state.
	 */
	public SConditionalExpr withThenExpr(BUTree<? extends SExpr> thenExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	/**
	 * Replaces the else expression of this conditional expression state.
	 *
	 * @param elseExpr the replacement for the else expression of this conditional expression state.
	 * @return the resulting mutated conditional expression state.
	 */
	public SConditionalExpr withElseExpr(BUTree<? extends SExpr> elseExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	/**
	 * Builds a conditional expression facade for the specified conditional expression <code>TDLocation</code>.
	 *
	 * @param location the conditional expression <code>TDLocation</code>.
	 * @return a conditional expression facade for the specified conditional expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SConditionalExpr> location) {
		return new TDConditionalExpr(location);
	}

	/**
	 * Returns the shape for this conditional expression state.
	 *
	 * @return the shape for this conditional expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this conditional expression state.
	 *
	 * @return the first child traversal for this conditional expression state.
	 */
	@Override
	public STraversal firstChild() {
		return CONDITION;
	}

	/**
	 * Returns the last child traversal for this conditional expression state.
	 *
	 * @return the last child traversal for this conditional expression state.
	 */
	@Override
	public STraversal lastChild() {
		return ELSE_EXPR;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SConditionalExpr state = (SConditionalExpr) o;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		if (thenExpr == null ? state.thenExpr != null : !thenExpr.equals(state.thenExpr))
			return false;
		if (elseExpr == null ? state.elseExpr != null : !elseExpr.equals(state.elseExpr))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		if (condition != null) result = 37 * result + condition.hashCode();
		if (thenExpr != null) result = 37 * result + thenExpr.hashCode();
		if (elseExpr != null) result = 37 * result + elseExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> CONDITION = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.condition;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return THEN_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> THEN_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.thenExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withThenExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ELSE_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> ELSE_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.elseExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withElseExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return THEN_EXPR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(CONDITION),
			token(LToken.QuestionMark).withSpacing(space(), space()),
			child(THEN_EXPR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ELSE_EXPR)
	);
}
