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
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * A constructor declaration.
 */
public interface ConstructorDecl extends MemberDecl, Documentable<ConstructorDecl>, TreeCombinators<ConstructorDecl> {

	/**
	 * Returns the modifiers of this constructor declaration.
	 *
	 * @return the modifiers of this constructor declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this constructor declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type parameters of this constructor declaration.
	 *
	 * @return the type parameters of this constructor declaration.
	 */
	NodeList<TypeParameter> typeParams();

	/**
	 * Replaces the type parameters of this constructor declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams);

	/**
	 * Mutates the type parameters of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	/**
	 * Returns the name of this constructor declaration.
	 *
	 * @return the name of this constructor declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this constructor declaration.
	 *
	 * @param name the replacement for the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withName(Name name);

	/**
	 * Mutates the name of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this constructor declaration.
	 *
	 * @param name the replacement for the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withName(String name);

	/**
	 * Returns the parameters of this constructor declaration.
	 *
	 * @return the parameters of this constructor declaration.
	 */
	NodeList<FormalParameter> params();

	/**
	 * Replaces the parameters of this constructor declaration.
	 *
	 * @param params the replacement for the parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withParams(NodeList<FormalParameter> params);

	/**
	 * Mutates the parameters of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation);

	/**
	 * Returns the 'throws' clause of this constructor declaration.
	 *
	 * @return the 'throws' clause of this constructor declaration.
	 */
	NodeList<QualifiedType> throwsClause();

	/**
	 * Replaces the 'throws' clause of this constructor declaration.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause);

	/**
	 * Mutates the 'throws' clause of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the 'throws' clause of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the body of this constructor declaration.
	 *
	 * @return the body of this constructor declaration.
	 */
	BlockStmt body();

	/**
	 * Replaces the body of this constructor declaration.
	 *
	 * @param body the replacement for the body of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withBody(BlockStmt body);

	/**
	 * Mutates the body of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the body of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	ConstructorDecl withBody(Mutation<BlockStmt> mutation);
}
