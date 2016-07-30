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

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A normal annotation expression.
 */
public interface NormalAnnotationExpr extends AnnotationExpr, TreeCombinators<NormalAnnotationExpr> {

	/**
	 * Returns the name of this normal annotation expression.
	 *
	 * @return the name of this normal annotation expression.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this normal annotation expression.
	 *
	 * @param name the replacement for the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withName(QualifiedName name);

	/**
	 * Mutates the name of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the name of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withName(Mutation<QualifiedName> mutation);

	/**
	 * Returns the pairs of this normal annotation expression.
	 *
	 * @return the pairs of this normal annotation expression.
	 */
	NodeList<MemberValuePair> pairs();

	/**
	 * Replaces the pairs of this normal annotation expression.
	 *
	 * @param pairs the replacement for the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs);

	/**
	 * Mutates the pairs of this normal annotation expression.
	 *
	 * @param mutation the mutation to apply to the pairs of this normal annotation expression.
	 * @return the resulting mutated normal annotation expression.
	 */
	NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation);
}
