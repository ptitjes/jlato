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
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method declaration.
 */
public interface MethodDecl extends MemberDecl, Documentable<MethodDecl>, TreeCombinators<MethodDecl> {

	/**
	 * Returns the modifiers of this method declaration.
	 *
	 * @return the modifiers of this method declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this method declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this method declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type parameters of this method declaration.
	 *
	 * @return the type parameters of this method declaration.
	 */
	NodeList<TypeParameter> typeParams();

	/**
	 * Replaces the type parameters of this method declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withTypeParams(NodeList<TypeParameter> typeParams);

	/**
	 * Mutates the type parameters of this method declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation);

	/**
	 * Returns the additional annotations of this method declaration.
	 *
	 * @return the additional annotations of this method declaration.
	 */
	NodeList<AnnotationExpr> additionalAnnotations();

	/**
	 * Replaces the additional annotations of this method declaration.
	 *
	 * @param additionalAnnotations the replacement for the additional annotations of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withAdditionalAnnotations(NodeList<AnnotationExpr> additionalAnnotations);

	/**
	 * Mutates the additional annotations of this method declaration.
	 *
	 * @param mutation the mutation to apply to the additional annotations of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withAdditionalAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the type of this method declaration.
	 *
	 * @return the type of this method declaration.
	 */
	Type type();

	/**
	 * Replaces the type of this method declaration.
	 *
	 * @param type the replacement for the type of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withType(Type type);

	/**
	 * Mutates the type of this method declaration.
	 *
	 * @param mutation the mutation to apply to the type of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withType(Mutation<Type> mutation);

	/**
	 * Returns the name of this method declaration.
	 *
	 * @return the name of this method declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this method declaration.
	 *
	 * @param name the replacement for the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withName(Name name);

	/**
	 * Mutates the name of this method declaration.
	 *
	 * @param mutation the mutation to apply to the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this method declaration.
	 *
	 * @param name the replacement for the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withName(String name);

	/**
	 * Returns the parameters of this method declaration.
	 *
	 * @return the parameters of this method declaration.
	 */
	NodeList<FormalParameter> params();

	/**
	 * Replaces the parameters of this method declaration.
	 *
	 * @param params the replacement for the parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withParams(NodeList<FormalParameter> params);

	/**
	 * Mutates the parameters of this method declaration.
	 *
	 * @param mutation the mutation to apply to the parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation);

	/**
	 * Returns the dimensions of this method declaration.
	 *
	 * @return the dimensions of this method declaration.
	 */
	NodeList<ArrayDim> dims();

	/**
	 * Replaces the dimensions of this method declaration.
	 *
	 * @param dims the replacement for the dimensions of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withDims(NodeList<ArrayDim> dims);

	/**
	 * Mutates the dimensions of this method declaration.
	 *
	 * @param mutation the mutation to apply to the dimensions of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withDims(Mutation<NodeList<ArrayDim>> mutation);

	/**
	 * Returns the 'throws' clause of this method declaration.
	 *
	 * @return the 'throws' clause of this method declaration.
	 */
	NodeList<QualifiedType> throwsClause();

	/**
	 * Replaces the 'throws' clause of this method declaration.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withThrowsClause(NodeList<QualifiedType> throwsClause);

	/**
	 * Mutates the 'throws' clause of this method declaration.
	 *
	 * @param mutation the mutation to apply to the 'throws' clause of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation);

	/**
	 * Returns the body of this method declaration.
	 *
	 * @return the body of this method declaration.
	 */
	NodeOption<BlockStmt> body();

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @param body the replacement for the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withBody(NodeOption<BlockStmt> body);

	/**
	 * Mutates the body of this method declaration.
	 *
	 * @param mutation the mutation to apply to the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withBody(Mutation<NodeOption<BlockStmt>> mutation);

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @param body the replacement for the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withBody(BlockStmt body);

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @return the resulting mutated method declaration.
	 */
	MethodDecl withNoBody();
}
