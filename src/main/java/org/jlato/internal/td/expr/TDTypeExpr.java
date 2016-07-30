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

import org.jlato.internal.bu.expr.STypeExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.TypeExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type expression.
 */
public class TDTypeExpr extends TDTree<STypeExpr, Expr, TypeExpr> implements TypeExpr {

	/**
	 * Returns the kind of this type expression.
	 *
	 * @return the kind of this type expression.
	 */
	public Kind kind() {
		return Kind.TypeExpr;
	}

	/**
	 * Creates a type expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDTypeExpr(TDLocation<STypeExpr> location) {
		super(location);
	}

	/**
	 * Creates a type expression with the specified child trees.
	 *
	 * @param type the type child tree.
	 */
	public TDTypeExpr(Type type) {
		super(new TDLocation<STypeExpr>(STypeExpr.make(TDTree.<SType>treeOf(type))));
	}

	/**
	 * Returns the type of this type expression.
	 *
	 * @return the type of this type expression.
	 */
	public Type type() {
		return location.safeTraversal(STypeExpr.TYPE);
	}

	/**
	 * Replaces the type of this type expression.
	 *
	 * @param type the replacement for the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	public TypeExpr withType(Type type) {
		return location.safeTraversalReplace(STypeExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this type expression.
	 *
	 * @param mutation the mutation to apply to the type of this type expression.
	 * @return the resulting mutated type expression.
	 */
	public TypeExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(STypeExpr.TYPE, mutation);
	}
}
