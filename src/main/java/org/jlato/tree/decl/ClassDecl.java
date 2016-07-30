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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * A class declaration.
 */
public interface ClassDecl extends TypeDecl, Documentable<ClassDecl>, TreeCombinators<ClassDecl> {

	/**
	 * Returns the modifiers of this class declaration.
	 *
	 * @return the modifiers of this class declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this class declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this class declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this class declaration.
	 *
	 * @return the name of this class declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this class declaration.
	 *
	 * @param name the replacement for the name of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withName(Name name);

	/**
	 * Mutates the name of this class declaration.
	 *
	 * @param mutation the mutation to apply to the name of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this class declaration.
	 *
	 * @param name the replacement for the name of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withName(String name);

	/**
	 * Returns the type parameters of this class declaration.
	 *
	 * @return the type parameters of this class declaration.
	 */
	NodeList<TypeParameter> typeParams();

	/**
	 * Replaces the type parameters of this class declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withTypeParams(NodeList<TypeParameter> typeParams);

	/**
	 * Mutates the type parameters of this class declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	/**
	 * Returns the 'extends' clause of this class declaration.
	 *
	 * @return the 'extends' clause of this class declaration.
	 */
	NodeOption<QualifiedType> extendsClause();

	/**
	 * Replaces the 'extends' clause of this class declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withExtendsClause(NodeOption<QualifiedType> extendsClause);

	/**
	 * Mutates the 'extends' clause of this class declaration.
	 *
	 * @param mutation the mutation to apply to the 'extends' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withExtendsClause(Mutation<NodeOption<QualifiedType>> mutation);

	/**
	 * Replaces the 'extends' clause of this class declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withExtendsClause(QualifiedType extendsClause);

	/**
	 * Replaces the 'extends' clause of this class declaration.
	 *
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withNoExtendsClause();

	/**
	 * Returns the 'implements' clause of this class declaration.
	 *
	 * @return the 'implements' clause of this class declaration.
	 */
	NodeList<QualifiedType> implementsClause();

	/**
	 * Replaces the 'implements' clause of this class declaration.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause);

	/**
	 * Mutates the 'implements' clause of this class declaration.
	 *
	 * @param mutation the mutation to apply to the 'implements' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the members of this class declaration.
	 *
	 * @return the members of this class declaration.
	 */
	NodeList<MemberDecl> members();

	/**
	 * Replaces the members of this class declaration.
	 *
	 * @param members the replacement for the members of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withMembers(NodeList<MemberDecl> members);

	/**
	 * Mutates the members of this class declaration.
	 *
	 * @param mutation the mutation to apply to the members of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	ClassDecl withMembers(Mutation<NodeList<MemberDecl>> mutation);
}
