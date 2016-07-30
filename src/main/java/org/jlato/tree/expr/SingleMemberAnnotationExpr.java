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

package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A single member annotation expression.
 */
public interface SingleMemberAnnotationExpr extends AnnotationExpr, TreeCombinators<SingleMemberAnnotationExpr> {

	/**
	 * Returns the name of this single member annotation expression.
	 *
	 * @return the name of this single member annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this single member annotation expression.
	 *
	 * @param name the replacement for the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation);

	/**
	 * Returns the member value of this single member annotation expression.
	 *
	 * @return the member value of this single member annotation expression.
	 */
	Expr memberValue();

	/**
	 * Replaces the member value of this single member annotation expression.
	 *
	 * @param memberValue the replacement for the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withMemberValue(Expr memberValue);

	/**
	 * Mutates the member value of this single member annotation expression.
	 *
	 * @param mutation the mutation to apply to the member value of this single member annotation expression.
	 * @return the resulting mutated single member annotation expression.
	 */
	SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation);
}
