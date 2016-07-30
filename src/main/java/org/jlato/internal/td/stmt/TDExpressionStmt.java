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

package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SExpressionStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ExpressionStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * An expression statement.
 */
public class TDExpressionStmt extends TDTree<SExpressionStmt, Stmt, ExpressionStmt> implements ExpressionStmt {

	/**
	 * Returns the kind of this expression statement.
	 *
	 * @return the kind of this expression statement.
	 */
	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	/**
	 * Creates an expression statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDExpressionStmt(TDLocation<SExpressionStmt> location) {
		super(location);
	}

	/**
	 * Creates an expression statement with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 */
	public TDExpressionStmt(Expr expr) {
		super(new TDLocation<SExpressionStmt>(SExpressionStmt.make(TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the expression of this expression statement.
	 *
	 * @return the expression of this expression statement.
	 */
	public Expr expr() {
		return location.safeTraversal(SExpressionStmt.EXPR);
	}

	/**
	 * Replaces the expression of this expression statement.
	 *
	 * @param expr the replacement for the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	public ExpressionStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SExpressionStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this expression statement.
	 *
	 * @param mutation the mutation to apply to the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	public ExpressionStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SExpressionStmt.EXPR, mutation);
	}
}
