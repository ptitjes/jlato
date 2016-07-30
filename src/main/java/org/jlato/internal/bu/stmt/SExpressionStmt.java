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

package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDExpressionStmt;
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
 * A state object for an expression statement.
 */
public class SExpressionStmt extends SNode<SExpressionStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new expression statement.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an expression statement.
	 */
	public static BUTree<SExpressionStmt> make(BUTree<? extends SExpr> expr) {
		return new BUTree<SExpressionStmt>(new SExpressionStmt(expr));
	}

	/**
	 * The expression of this expression statement state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * Constructs an expression statement state.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 */
	public SExpressionStmt(BUTree<? extends SExpr> expr) {
		this.expr = expr;
	}

	/**
	 * Returns the kind of this expression statement.
	 *
	 * @return the kind of this expression statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	/**
	 * Replaces the expression of this expression statement state.
	 *
	 * @param expr the replacement for the expression of this expression statement state.
	 * @return the resulting mutated expression statement state.
	 */
	public SExpressionStmt withExpr(BUTree<? extends SExpr> expr) {
		return new SExpressionStmt(expr);
	}

	/**
	 * Builds an expression statement facade for the specified expression statement <code>TDLocation</code>.
	 *
	 * @param location the expression statement <code>TDLocation</code>.
	 * @return an expression statement facade for the specified expression statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SExpressionStmt> location) {
		return new TDExpressionStmt(location);
	}

	/**
	 * Returns the shape for this expression statement state.
	 *
	 * @return the shape for this expression statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this expression statement state.
	 *
	 * @return the first child traversal for this expression statement state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this expression statement state.
	 *
	 * @return the last child traversal for this expression statement state.
	 */
	@Override
	public STraversal lastChild() {
		return EXPR;
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
		SExpressionStmt state = (SExpressionStmt) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
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
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SExpressionStmt, SExpr, Expr> EXPR = new STypeSafeTraversal<SExpressionStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SExpressionStmt state) {
			return state.expr;
		}

		@Override
		public SExpressionStmt doRebuildParentState(SExpressionStmt state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR), token(LToken.SemiColon)
	);
}
