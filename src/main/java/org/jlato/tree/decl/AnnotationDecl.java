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
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An annotation type declaration.
 */
public interface AnnotationDecl extends TypeDecl, Documentable<AnnotationDecl>, TreeCombinators<AnnotationDecl> {

	/**
	 * Returns the modifiers of this annotation type declaration.
	 *
	 * @return the modifiers of this annotation type declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this annotation type declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this annotation type declaration.
	 *
	 * @return the name of this annotation type declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(Name name);

	/**
	 * Mutates the name of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withName(String name);

	/**
	 * Returns the members of this annotation type declaration.
	 *
	 * @return the members of this annotation type declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this annotation type declaration.
	 *
	 * @param members the replacement for the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
