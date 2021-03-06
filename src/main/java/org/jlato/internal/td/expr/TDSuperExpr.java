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
import org.jlato.internal.bu.expr.SSuperExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SuperExpr;
import org.jlato.util.Mutation;

/**
 * A 'super' expression.
 */
public class TDSuperExpr extends TDTree<SSuperExpr, Expr, SuperExpr> implements SuperExpr {

	/**
	 * Returns the kind of this 'super' expression.
	 *
	 * @return the kind of this 'super' expression.
	 */
	public Kind kind() {
		return Kind.SuperExpr;
	}

	/**
	 * Creates a 'super' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSuperExpr(TDLocation<SSuperExpr> location) {
		super(location);
	}

	/**
	 * Creates a 'super' expression with the specified child trees.
	 *
	 * @param classExpr the 'class' expression child tree.
	 */
	public TDSuperExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SSuperExpr>(SSuperExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	/**
	 * Returns the 'class' expression of this 'super' expression.
	 *
	 * @return the 'class' expression of this 'super' expression.
	 */
	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SSuperExpr.CLASS_EXPR);
	}

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SSuperExpr.CLASS_EXPR, classExpr);
	}

	/**
	 * Mutates the 'class' expression of this 'super' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SSuperExpr.CLASS_EXPR, mutation);
	}

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withClassExpr(Expr classExpr) {
		return location.safeTraversalReplace(SSuperExpr.CLASS_EXPR, Trees.some(classExpr));
	}

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withNoClassExpr() {
		return location.safeTraversalReplace(SSuperExpr.CLASS_EXPR, Trees.<Expr>none());
	}
}
