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

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An annotation type member declaration.
 */
public interface AnnotationMemberDecl extends MemberDecl, Documentable<AnnotationMemberDecl>, TreeCombinators<AnnotationMemberDecl> {

	/**
	 * Returns the modifiers of this annotation type member declaration.
	 *
	 * @return the modifiers of this annotation type member declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this annotation type member declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this annotation type member declaration.
	 *
	 * @return the type of this annotation type member declaration.
	 */
	Type type();

	/**
	 * Replaces the type of this annotation type member declaration.
	 *
	 * @param type the replacement for the type of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withType(Type type);

	/**
	 * Mutates the type of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the type of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withType(Mutation<Type> mutation);

	/**
	 * Returns the name of this annotation type member declaration.
	 *
	 * @return the name of this annotation type member declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this annotation type member declaration.
	 *
	 * @param name the replacement for the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withName(Name name);

	/**
	 * Mutates the name of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this annotation type member declaration.
	 *
	 * @param name the replacement for the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withName(String name);

	/**
	 * Returns the dimensions of this annotation type member declaration.
	 *
	 * @return the dimensions of this annotation type member declaration.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this annotation type member declaration.
	 *
	 * @param dims the replacement for the dimensions of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the dimensions of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withDims(Mutation<NodeList<ArrayDim>> mutation);

	/**
	 * Returns the default value of this annotation type member declaration.
	 *
	 * @return the default value of this annotation type member declaration.
	 */
	NodeOption<Expr> defaultValue();

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @param defaultValue the replacement for the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withDefaultValue(NodeOption<Expr> defaultValue);

	/**
	 * Mutates the default value of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withDefaultValue(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @param defaultValue the replacement for the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withDefaultValue(Expr defaultValue);

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @return the resulting mutated annotation type member declaration.
	 */
	AnnotationMemberDecl withNoDefaultValue();
}
