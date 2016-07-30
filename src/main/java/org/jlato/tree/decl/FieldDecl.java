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
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A field declaration.
 */
public interface FieldDecl extends MemberDecl, Documentable<FieldDecl>, TreeCombinators<FieldDecl> {

	/**
	 * Returns the modifiers of this field declaration.
	 *
	 * @return the modifiers of this field declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this field declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this field declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this field declaration.
	 *
	 * @return the type of this field declaration.
	 */
	Type type();

	/**
	 * Replaces the type of this field declaration.
	 *
	 * @param type the replacement for the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withType(Type type);

	/**
	 * Mutates the type of this field declaration.
	 *
	 * @param mutation the mutation to apply to the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withType(Mutation<Type> mutation);

	/**
	 * Returns the variables of this field declaration.
	 *
	 * @return the variables of this field declaration.
	 */
	NodeList<VariableDeclarator> variables();

	/**
	 * Replaces the variables of this field declaration.
	 *
	 * @param variables the replacement for the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withVariables(NodeList<VariableDeclarator> variables);

	/**
	 * Mutates the variables of this field declaration.
	 *
	 * @param mutation the mutation to apply to the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation);
}
