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

import org.jlato.internal.bu.expr.SClassExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ClassExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A 'class' expression.
 */
public class TDClassExpr extends TDTree<SClassExpr, Expr, ClassExpr> implements ClassExpr {

	/**
	 * Returns the kind of this 'class' expression.
	 *
	 * @return the kind of this 'class' expression.
	 */
	public Kind kind() {
		return Kind.ClassExpr;
	}

	/**
	 * Creates a 'class' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDClassExpr(TDLocation<SClassExpr> location) {
		super(location);
	}

	/**
	 * Creates a 'class' expression with the specified child trees.
	 *
	 * @param type the type child tree.
	 */
	public TDClassExpr(Type type) {
		super(new TDLocation<SClassExpr>(SClassExpr.make(TDTree.<SType>treeOf(type))));
	}

	/**
	 * Returns the type of this 'class' expression.
	 *
	 * @return the type of this 'class' expression.
	 */
	public Type type() {
		return location.safeTraversal(SClassExpr.TYPE);
	}

	/**
	 * Replaces the type of this 'class' expression.
	 *
	 * @param type the replacement for the type of this 'class' expression.
	 * @return the resulting mutated 'class' expression.
	 */
	public ClassExpr withType(Type type) {
		return location.safeTraversalReplace(SClassExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this 'class' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'class' expression.
	 * @return the resulting mutated 'class' expression.
	 */
	public ClassExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SClassExpr.TYPE, mutation);
	}
}
