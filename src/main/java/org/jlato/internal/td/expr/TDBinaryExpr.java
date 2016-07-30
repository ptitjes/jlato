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

import org.jlato.internal.bu.expr.SBinaryExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.BinaryExpr;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A binary expression.
 */
public class TDBinaryExpr extends TDTree<SBinaryExpr, Expr, BinaryExpr> implements BinaryExpr {

	/**
	 * Returns the kind of this binary expression.
	 *
	 * @return the kind of this binary expression.
	 */
	public Kind kind() {
		return Kind.BinaryExpr;
	}

	/**
	 * Creates a binary expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDBinaryExpr(TDLocation<SBinaryExpr> location) {
		super(location);
	}

	/**
	 * Creates a binary expression with the specified child trees.
	 *
	 * @param left  the left child tree.
	 * @param op    the op child tree.
	 * @param right the right child tree.
	 */
	public TDBinaryExpr(Expr left, BinaryOp op, Expr right) {
		super(new TDLocation<SBinaryExpr>(SBinaryExpr.make(TDTree.<SExpr>treeOf(left), op, TDTree.<SExpr>treeOf(right))));
	}

	/**
	 * Returns the left of this binary expression.
	 *
	 * @return the left of this binary expression.
	 */
	public Expr left() {
		return location.safeTraversal(SBinaryExpr.LEFT);
	}

	/**
	 * Replaces the left of this binary expression.
	 *
	 * @param left the replacement for the left of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withLeft(Expr left) {
		return location.safeTraversalReplace(SBinaryExpr.LEFT, left);
	}

	/**
	 * Mutates the left of this binary expression.
	 *
	 * @param mutation the mutation to apply to the left of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withLeft(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SBinaryExpr.LEFT, mutation);
	}

	/**
	 * Returns the op of this binary expression.
	 *
	 * @return the op of this binary expression.
	 */
	public BinaryOp op() {
		return location.safeProperty(SBinaryExpr.OP);
	}

	/**
	 * Replaces the op of this binary expression.
	 *
	 * @param op the replacement for the op of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withOp(BinaryOp op) {
		return location.safePropertyReplace(SBinaryExpr.OP, op);
	}

	/**
	 * Mutates the op of this binary expression.
	 *
	 * @param mutation the mutation to apply to the op of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withOp(Mutation<BinaryOp> mutation) {
		return location.safePropertyMutate(SBinaryExpr.OP, mutation);
	}

	/**
	 * Returns the right of this binary expression.
	 *
	 * @return the right of this binary expression.
	 */
	public Expr right() {
		return location.safeTraversal(SBinaryExpr.RIGHT);
	}

	/**
	 * Replaces the right of this binary expression.
	 *
	 * @param right the replacement for the right of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withRight(Expr right) {
		return location.safeTraversalReplace(SBinaryExpr.RIGHT, right);
	}

	/**
	 * Mutates the right of this binary expression.
	 *
	 * @param mutation the mutation to apply to the right of this binary expression.
	 * @return the resulting mutated binary expression.
	 */
	public BinaryExpr withRight(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SBinaryExpr.RIGHT, mutation);
	}
}
