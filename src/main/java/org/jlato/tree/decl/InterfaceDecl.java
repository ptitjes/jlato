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
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * An interface declaration.
 */
public interface InterfaceDecl extends TypeDecl, Documentable<InterfaceDecl>, TreeCombinators<InterfaceDecl> {

	/**
	 * Returns the modifiers of this interface declaration.
	 *
	 * @return the modifiers of this interface declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this interface declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this interface declaration.
	 *
	 * @return the name of this interface declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this interface declaration.
	 *
	 * @param name the replacement for the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withName(Name name);

	/**
	 * Mutates the name of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this interface declaration.
	 *
	 * @param name the replacement for the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withName(String name);

	/**
	 * Returns the type parameters of this interface declaration.
	 *
	 * @return the type parameters of this interface declaration.
	 */
	NodeList<TypeParameter> typeParams();

	/**
	 * Replaces the type parameters of this interface declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams);

	/**
	 * Mutates the type parameters of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	/**
	 * Returns the 'extends' clause of this interface declaration.
	 *
	 * @return the 'extends' clause of this interface declaration.
	 */
	NodeList<QualifiedType> extendsClause();

	/**
	 * Replaces the 'extends' clause of this interface declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause);

	/**
	 * Mutates the 'extends' clause of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the members of this interface declaration.
	 *
	 * @return the members of this interface declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this interface declaration.
	 *
	 * @param members the replacement for the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
