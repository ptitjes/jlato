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
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

/**
 * A primitive type.
 */
public interface PrimitiveType extends Type, TreeCombinators<PrimitiveType> {

	/**
	 * Returns the annotations of this primitive type.
	 *
	 * @return the annotations of this primitive type.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this primitive type.
	 *
	 * @param annotations the replacement for the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this primitive type.
	 *
	 * @param mutation the mutation to apply to the annotations of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the primitive of this primitive type.
	 *
	 * @return the primitive of this primitive type.
	 */
	Primitive primitive();

	/**
	 * Replaces the primitive of this primitive type.
	 *
	 * @param primitive the replacement for the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withPrimitive(Primitive primitive);

	/**
	 * Mutates the primitive of this primitive type.
	 *
	 * @param mutation the mutation to apply to the primitive of this primitive type.
	 * @return the resulting mutated primitive type.
	 */
	PrimitiveType withPrimitive(Mutation<Primitive> mutation);
}
