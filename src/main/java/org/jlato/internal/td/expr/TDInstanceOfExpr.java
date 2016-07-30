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
import org.jlato.internal.bu.expr.SInstanceOfExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.InstanceOfExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An 'instanceof' expression.
 */
public class TDInstanceOfExpr extends TDTree<SInstanceOfExpr, Expr, InstanceOfExpr> implements InstanceOfExpr {

	/**
	 * Returns the kind of this 'instanceof' expression.
	 *
	 * @return the kind of this 'instanceof' expression.
	 */
	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	/**
	 * Creates an 'instanceof' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDInstanceOfExpr(TDLocation<SInstanceOfExpr> location) {
		super(location);
	}

	/**
	 * Creates an 'instanceof' expression with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 * @param type the type child tree.
	 */
	public TDInstanceOfExpr(Expr expr, Type type) {
		super(new TDLocation<SInstanceOfExpr>(SInstanceOfExpr.make(TDTree.<SExpr>treeOf(expr), TDTree.<SType>treeOf(type))));
	}

	/**
	 * Returns the expression of this 'instanceof' expression.
	 *
	 * @return the expression of this 'instanceof' expression.
	 */
	public Expr expr() {
		return location.safeTraversal(SInstanceOfExpr.EXPR);
	}

	/**
	 * Replaces the expression of this 'instanceof' expression.
	 *
	 * @param expr the replacement for the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SInstanceOfExpr.EXPR, expr);
	}

	/**
	 * Mutates the expression of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.EXPR, mutation);
	}

	/**
	 * Returns the type of this 'instanceof' expression.
	 *
	 * @return the type of this 'instanceof' expression.
	 */
	public Type type() {
		return location.safeTraversal(SInstanceOfExpr.TYPE);
	}

	/**
	 * Replaces the type of this 'instanceof' expression.
	 *
	 * @param type the replacement for the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withType(Type type) {
		return location.safeTraversalReplace(SInstanceOfExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.TYPE, mutation);
	}
}
