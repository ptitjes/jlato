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
import org.jlato.internal.bu.expr.SUnaryExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.UnaryExpr;
import org.jlato.tree.expr.UnaryOp;
import org.jlato.util.Mutation;

/**
 * An unary expression.
 */
public class TDUnaryExpr extends TDTree<SUnaryExpr, Expr, UnaryExpr> implements UnaryExpr {

	/**
	 * Returns the kind of this unary expression.
	 *
	 * @return the kind of this unary expression.
	 */
	public Kind kind() {
		return Kind.UnaryExpr;
	}

	/**
	 * Creates an unary expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDUnaryExpr(TDLocation<SUnaryExpr> location) {
		super(location);
	}

	/**
	 * Creates an unary expression with the specified child trees.
	 *
	 * @param op   the op child tree.
	 * @param expr the expression child tree.
	 */
	public TDUnaryExpr(UnaryOp op, Expr expr) {
		super(new TDLocation<SUnaryExpr>(SUnaryExpr.make(op, TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the op of this unary expression.
	 *
	 * @return the op of this unary expression.
	 */
	public UnaryOp op() {
		return location.safeProperty(SUnaryExpr.OP);
	}

	/**
	 * Replaces the op of this unary expression.
	 *
	 * @param op the replacement for the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	public UnaryExpr withOp(UnaryOp op) {
		return location.safePropertyReplace(SUnaryExpr.OP, op);
	}

	/**
	 * Mutates the op of this unary expression.
	 *
	 * @param mutation the mutation to apply to the op of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	public UnaryExpr withOp(Mutation<UnaryOp> mutation) {
		return location.safePropertyMutate(SUnaryExpr.OP, mutation);
	}

	/**
	 * Returns the expression of this unary expression.
	 *
	 * @return the expression of this unary expression.
	 */
	public Expr expr() {
		return location.safeTraversal(SUnaryExpr.EXPR);
	}

	/**
	 * Replaces the expression of this unary expression.
	 *
	 * @param expr the replacement for the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	public UnaryExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SUnaryExpr.EXPR, expr);
	}

	/**
	 * Mutates the expression of this unary expression.
	 *
	 * @param mutation the mutation to apply to the expression of this unary expression.
	 * @return the resulting mutated unary expression.
	 */
	public UnaryExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SUnaryExpr.EXPR, mutation);
	}
}
