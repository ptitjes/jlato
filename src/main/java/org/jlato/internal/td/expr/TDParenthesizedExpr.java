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

package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SParenthesizedExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ParenthesizedExpr;
import org.jlato.util.Mutation;

/**
 * A parenthesized expression.
 */
public class TDParenthesizedExpr extends TDTree<SParenthesizedExpr, Expr, ParenthesizedExpr> implements ParenthesizedExpr {

	/**
	 * Returns the kind of this parenthesized expression.
	 *
	 * @return the kind of this parenthesized expression.
	 */
	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	/**
	 * Creates a parenthesized expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDParenthesizedExpr(TDLocation<SParenthesizedExpr> location) {
		super(location);
	}

	/**
	 * Creates a parenthesized expression with the specified child trees.
	 *
	 * @param inner the inner child tree.
	 */
	public TDParenthesizedExpr(Expr inner) {
		super(new TDLocation<SParenthesizedExpr>(SParenthesizedExpr.make(TDTree.<SExpr>treeOf(inner))));
	}

	/**
	 * Returns the inner of this parenthesized expression.
	 *
	 * @return the inner of this parenthesized expression.
	 */
	public Expr inner() {
		return location.safeTraversal(SParenthesizedExpr.INNER);
	}

	/**
	 * Replaces the inner of this parenthesized expression.
	 *
	 * @param inner the replacement for the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	public ParenthesizedExpr withInner(Expr inner) {
		return location.safeTraversalReplace(SParenthesizedExpr.INNER, inner);
	}

	/**
	 * Mutates the inner of this parenthesized expression.
	 *
	 * @param mutation the mutation to apply to the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	public ParenthesizedExpr withInner(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SParenthesizedExpr.INNER, mutation);
	}
}
