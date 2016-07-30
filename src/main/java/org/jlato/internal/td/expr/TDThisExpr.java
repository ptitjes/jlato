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

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SThisExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ThisExpr;
import org.jlato.util.Mutation;

/**
 * A 'this' expression.
 */
public class TDThisExpr extends TDTree<SThisExpr, Expr, ThisExpr> implements ThisExpr {

	/**
	 * Returns the kind of this 'this' expression.
	 *
	 * @return the kind of this 'this' expression.
	 */
	public Kind kind() {
		return Kind.ThisExpr;
	}

	/**
	 * Creates a 'this' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDThisExpr(TDLocation<SThisExpr> location) {
		super(location);
	}

	/**
	 * Creates a 'this' expression with the specified child trees.
	 *
	 * @param classExpr the 'class' expression child tree.
	 */
	public TDThisExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SThisExpr>(SThisExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	/**
	 * Returns the 'class' expression of this 'this' expression.
	 *
	 * @return the 'class' expression of this 'this' expression.
	 */
	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SThisExpr.CLASS_EXPR);
	}

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SThisExpr.CLASS_EXPR, classExpr);
	}

	/**
	 * Mutates the 'class' expression of this 'this' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SThisExpr.CLASS_EXPR, mutation);
	}

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withClassExpr(Expr classExpr) {
		return location.safeTraversalReplace(SThisExpr.CLASS_EXPR, Trees.some(classExpr));
	}

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withNoClassExpr() {
		return location.safeTraversalReplace(SThisExpr.CLASS_EXPR, Trees.<Expr>none());
	}
}
