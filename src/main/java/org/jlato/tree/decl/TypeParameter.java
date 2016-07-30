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

package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A type parameter.
 */
public interface TypeParameter extends Node, TreeCombinators<TypeParameter> {

	/**
	 * Returns the annotations of this type parameter.
	 *
	 * @return the annotations of this type parameter.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this type parameter.
	 *
	 * @param annotations the replacement for the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this type parameter.
	 *
	 * @param mutation the mutation to apply to the annotations of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the name of this type parameter.
	 *
	 * @return the name of this type parameter.
	 */
	Name name();

	/**
	 * Replaces the name of this type parameter.
	 *
	 * @param name the replacement for the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withName(Name name);

	/**
	 * Mutates the name of this type parameter.
	 *
	 * @param mutation the mutation to apply to the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this type parameter.
	 *
	 * @param name the replacement for the name of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withName(String name);

	/**
	 * Returns the bounds of this type parameter.
	 *
	 * @return the bounds of this type parameter.
	 */
	NodeList<Type> bounds();

	/**
	 * Replaces the bounds of this type parameter.
	 *
	 * @param bounds the replacement for the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withBounds(NodeList<Type> bounds);

	/**
	 * Mutates the bounds of this type parameter.
	 *
	 * @param mutation the mutation to apply to the bounds of this type parameter.
	 * @return the resulting mutated type parameter.
	 */
	TypeParameter withBounds(Mutation<NodeList<Type>> mutation);
}
