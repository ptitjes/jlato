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

import org.jlato.internal.bu.expr.SCastExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.CastExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A cast expression.
 */
public class TDCastExpr extends TDTree<SCastExpr, Expr, CastExpr> implements CastExpr {

	/**
	 * Returns the kind of this cast expression.
	 *
	 * @return the kind of this cast expression.
	 */
	public Kind kind() {
		return Kind.CastExpr;
	}

	/**
	 * Creates a cast expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDCastExpr(TDLocation<SCastExpr> location) {
		super(location);
	}

	/**
	 * Creates a cast expression with the specified child trees.
	 *
	 * @param type the type child tree.
	 * @param expr the expression child tree.
	 */
	public TDCastExpr(Type type, Expr expr) {
		super(new TDLocation<SCastExpr>(SCastExpr.make(TDTree.<SType>treeOf(type), TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the type of this cast expression.
	 *
	 * @return the type of this cast expression.
	 */
	public Type type() {
		return location.safeTraversal(SCastExpr.TYPE);
	}

	/**
	 * Replaces the type of this cast expression.
	 *
	 * @param type the replacement for the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	public CastExpr withType(Type type) {
		return location.safeTraversalReplace(SCastExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this cast expression.
	 *
	 * @param mutation the mutation to apply to the type of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	public CastExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SCastExpr.TYPE, mutation);
	}

	/**
	 * Returns the expression of this cast expression.
	 *
	 * @return the expression of this cast expression.
	 */
	public Expr expr() {
		return location.safeTraversal(SCastExpr.EXPR);
	}

	/**
	 * Replaces the expression of this cast expression.
	 *
	 * @param expr the replacement for the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	public CastExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SCastExpr.EXPR, expr);
	}

	/**
	 * Mutates the expression of this cast expression.
	 *
	 * @param mutation the mutation to apply to the expression of this cast expression.
	 * @return the resulting mutated cast expression.
	 */
	public CastExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SCastExpr.EXPR, mutation);
	}
}
