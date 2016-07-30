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

package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

/**
 * A wildcard type.
 */
public interface WildcardType extends Type, TreeCombinators<WildcardType> {

	/**
	 * Returns the annotations of this wildcard type.
	 *
	 * @return the annotations of this wildcard type.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this wildcard type.
	 *
	 * @param annotations the replacement for the annotations of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the annotations of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the upper bound of this wildcard type.
	 *
	 * @return the upper bound of this wildcard type.
	 */
	NodeOption<ReferenceType> ext();

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @param ext the replacement for the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withExt(NodeOption<ReferenceType> ext);

	/**
	 * Mutates the upper bound of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withExt(Mutation<NodeOption<ReferenceType>> mutation);

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @param ext the replacement for the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withExt(ReferenceType ext);

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withNoExt();

	/**
	 * Returns the lower bound of this wildcard type.
	 *
	 * @return the lower bound of this wildcard type.
	 */
	NodeOption<ReferenceType> sup();

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @param sup the replacement for the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withSup(NodeOption<ReferenceType> sup);

	/**
	 * Mutates the lower bound of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withSup(Mutation<NodeOption<ReferenceType>> mutation);

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @param sup the replacement for the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withSup(ReferenceType sup);

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @return the resulting mutated wildcard type.
	 */
	WildcardType withNoSup();
}
