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

package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SMethodDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A method declaration.
 */
public class TDMethodDecl extends TDTree<SMethodDecl, MemberDecl, MethodDecl> implements MethodDecl {

	/**
	 * Returns the kind of this method declaration.
	 *
	 * @return the kind of this method declaration.
	 */
	public Kind kind() {
		return Kind.MethodDecl;
	}

	/**
	 * Creates a method declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDMethodDecl(TDLocation<SMethodDecl> location) {
		super(location);
	}

	/**
	 * Creates a method declaration with the specified child trees.
	 *
	 * @param modifiers    the modifiers child tree.
	 * @param typeParams   the type parameters child tree.
	 * @param type         the type child tree.
	 * @param name         the name child tree.
	 * @param params       the parameters child tree.
	 * @param dims         the dimensions child tree.
	 * @param throwsClause the 'throws' clause child tree.
	 * @param body         the body child tree.
	 */
	public TDMethodDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, NodeOption<BlockStmt> body) {
		super(new TDLocation<SMethodDecl>(SMethodDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SNodeList>treeOf(typeParams), TDTree.<SType>treeOf(type), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(params), TDTree.<SNodeList>treeOf(dims), TDTree.<SNodeList>treeOf(throwsClause), TDTree.<SNodeOption>treeOf(body))));
	}

	/**
	 * Returns the modifiers of this method declaration.
	 *
	 * @return the modifiers of this method declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SMethodDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this method declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SMethodDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this method declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the type parameters of this method declaration.
	 *
	 * @return the type parameters of this method declaration.
	 */
	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SMethodDecl.TYPE_PARAMS);
	}

	/**
	 * Replaces the type parameters of this method declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SMethodDecl.TYPE_PARAMS, typeParams);
	}

	/**
	 * Mutates the type parameters of this method declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.TYPE_PARAMS, mutation);
	}

	/**
	 * Returns the type of this method declaration.
	 *
	 * @return the type of this method declaration.
	 */
	public Type type() {
		return location.safeTraversal(SMethodDecl.TYPE);
	}

	/**
	 * Replaces the type of this method declaration.
	 *
	 * @param type the replacement for the type of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withType(Type type) {
		return location.safeTraversalReplace(SMethodDecl.TYPE, type);
	}

	/**
	 * Mutates the type of this method declaration.
	 *
	 * @param mutation the mutation to apply to the type of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SMethodDecl.TYPE, mutation);
	}

	/**
	 * Returns the name of this method declaration.
	 *
	 * @return the name of this method declaration.
	 */
	public Name name() {
		return location.safeTraversal(SMethodDecl.NAME);
	}

	/**
	 * Replaces the name of this method declaration.
	 *
	 * @param name the replacement for the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withName(Name name) {
		return location.safeTraversalReplace(SMethodDecl.NAME, name);
	}

	/**
	 * Mutates the name of this method declaration.
	 *
	 * @param mutation the mutation to apply to the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this method declaration.
	 *
	 * @param name the replacement for the name of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withName(String name) {
		return location.safeTraversalReplace(SMethodDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the parameters of this method declaration.
	 *
	 * @return the parameters of this method declaration.
	 */
	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SMethodDecl.PARAMS);
	}

	/**
	 * Replaces the parameters of this method declaration.
	 *
	 * @param params the replacement for the parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SMethodDecl.PARAMS, params);
	}

	/**
	 * Mutates the parameters of this method declaration.
	 *
	 * @param mutation the mutation to apply to the parameters of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.PARAMS, mutation);
	}

	/**
	 * Returns the dimensions of this method declaration.
	 *
	 * @return the dimensions of this method declaration.
	 */
	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SMethodDecl.DIMS);
	}

	/**
	 * Replaces the dimensions of this method declaration.
	 *
	 * @param dims the replacement for the dimensions of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SMethodDecl.DIMS, dims);
	}

	/**
	 * Mutates the dimensions of this method declaration.
	 *
	 * @param mutation the mutation to apply to the dimensions of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.DIMS, mutation);
	}

	/**
	 * Returns the 'throws' clause of this method declaration.
	 *
	 * @return the 'throws' clause of this method declaration.
	 */
	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(SMethodDecl.THROWS_CLAUSE);
	}

	/**
	 * Replaces the 'throws' clause of this method declaration.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(SMethodDecl.THROWS_CLAUSE, throwsClause);
	}

	/**
	 * Mutates the 'throws' clause of this method declaration.
	 *
	 * @param mutation the mutation to apply to the 'throws' clause of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.THROWS_CLAUSE, mutation);
	}

	/**
	 * Returns the body of this method declaration.
	 *
	 * @return the body of this method declaration.
	 */
	public NodeOption<BlockStmt> body() {
		return location.safeTraversal(SMethodDecl.BODY);
	}

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @param body the replacement for the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withBody(NodeOption<BlockStmt> body) {
		return location.safeTraversalReplace(SMethodDecl.BODY, body);
	}

	/**
	 * Mutates the body of this method declaration.
	 *
	 * @param mutation the mutation to apply to the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withBody(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.BODY, mutation);
	}

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @param body the replacement for the body of this method declaration.
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(SMethodDecl.BODY, Trees.some(body));
	}

	/**
	 * Replaces the body of this method declaration.
	 *
	 * @return the resulting mutated method declaration.
	 */
	public MethodDecl withNoBody() {
		return location.safeTraversalReplace(SMethodDecl.BODY, Trees.<BlockStmt>none());
	}
}
