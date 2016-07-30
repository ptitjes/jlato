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
import org.jlato.internal.bu.decl.SFieldDecl;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FieldDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A field declaration.
 */
public class TDFieldDecl extends TDTree<SFieldDecl, MemberDecl, FieldDecl> implements FieldDecl {

	/**
	 * Returns the kind of this field declaration.
	 *
	 * @return the kind of this field declaration.
	 */
	public Kind kind() {
		return Kind.FieldDecl;
	}

	/**
	 * Creates a field declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDFieldDecl(TDLocation<SFieldDecl> location) {
		super(location);
	}

	/**
	 * Creates a field declaration with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param type      the type child tree.
	 * @param variables the variables child tree.
	 */
	public TDFieldDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new TDLocation<SFieldDecl>(SFieldDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SNodeList>treeOf(variables))));
	}

	/**
	 * Returns the modifiers of this field declaration.
	 *
	 * @return the modifiers of this field declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SFieldDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this field declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SFieldDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this field declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SFieldDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the type of this field declaration.
	 *
	 * @return the type of this field declaration.
	 */
	public Type type() {
		return location.safeTraversal(SFieldDecl.TYPE);
	}

	/**
	 * Replaces the type of this field declaration.
	 *
	 * @param type the replacement for the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withType(Type type) {
		return location.safeTraversalReplace(SFieldDecl.TYPE, type);
	}

	/**
	 * Mutates the type of this field declaration.
	 *
	 * @param mutation the mutation to apply to the type of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SFieldDecl.TYPE, mutation);
	}

	/**
	 * Returns the variables of this field declaration.
	 *
	 * @return the variables of this field declaration.
	 */
	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(SFieldDecl.VARIABLES);
	}

	/**
	 * Replaces the variables of this field declaration.
	 *
	 * @param variables the replacement for the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(SFieldDecl.VARIABLES, variables);
	}

	/**
	 * Mutates the variables of this field declaration.
	 *
	 * @param mutation the mutation to apply to the variables of this field declaration.
	 * @return the resulting mutated field declaration.
	 */
	public FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(SFieldDecl.VARIABLES, mutation);
	}
}
