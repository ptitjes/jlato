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
import org.jlato.internal.bu.decl.SAnnotationMemberDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.AnnotationMemberDecl;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An annotation type member declaration.
 */
public class TDAnnotationMemberDecl extends TDTree<SAnnotationMemberDecl, MemberDecl, AnnotationMemberDecl> implements AnnotationMemberDecl {

	/**
	 * Returns the kind of this annotation type member declaration.
	 *
	 * @return the kind of this annotation type member declaration.
	 */
	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	/**
	 * Creates an annotation type member declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDAnnotationMemberDecl(TDLocation<SAnnotationMemberDecl> location) {
		super(location);
	}

	/**
	 * Creates an annotation type member declaration with the specified child trees.
	 *
	 * @param modifiers    the modifiers child tree.
	 * @param type         the type child tree.
	 * @param name         the name child tree.
	 * @param dims         the dimensions child tree.
	 * @param defaultValue the default value child tree.
	 */
	public TDAnnotationMemberDecl(NodeList<ExtendedModifier> modifiers, Type type, Name name, NodeList<ArrayDim> dims, NodeOption<Expr> defaultValue) {
		super(new TDLocation<SAnnotationMemberDecl>(SAnnotationMemberDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(dims), TDTree.<SNodeOption>treeOf(defaultValue))));
	}

	/**
	 * Returns the modifiers of this annotation type member declaration.
	 *
	 * @return the modifiers of this annotation type member declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SAnnotationMemberDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this annotation type member declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the type of this annotation type member declaration.
	 *
	 * @return the type of this annotation type member declaration.
	 */
	public Type type() {
		return location.safeTraversal(SAnnotationMemberDecl.TYPE);
	}

	/**
	 * Replaces the type of this annotation type member declaration.
	 *
	 * @param type the replacement for the type of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withType(Type type) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.TYPE, type);
	}

	/**
	 * Mutates the type of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the type of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.TYPE, mutation);
	}

	/**
	 * Returns the name of this annotation type member declaration.
	 *
	 * @return the name of this annotation type member declaration.
	 */
	public Name name() {
		return location.safeTraversal(SAnnotationMemberDecl.NAME);
	}

	/**
	 * Replaces the name of this annotation type member declaration.
	 *
	 * @param name the replacement for the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withName(Name name) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.NAME, name);
	}

	/**
	 * Mutates the name of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this annotation type member declaration.
	 *
	 * @param name the replacement for the name of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withName(String name) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the dimensions of this annotation type member declaration.
	 *
	 * @return the dimensions of this annotation type member declaration.
	 */
	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SAnnotationMemberDecl.DIMS);
	}

	/**
	 * Replaces the dimensions of this annotation type member declaration.
	 *
	 * @param dims the replacement for the dimensions of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DIMS, dims);
	}

	/**
	 * Mutates the dimensions of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the dimensions of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.DIMS, mutation);
	}

	/**
	 * Returns the default value of this annotation type member declaration.
	 *
	 * @return the default value of this annotation type member declaration.
	 */
	public NodeOption<Expr> defaultValue() {
		return location.safeTraversal(SAnnotationMemberDecl.DEFAULT_VALUE);
	}

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @param defaultValue the replacement for the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withDefaultValue(NodeOption<Expr> defaultValue) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DEFAULT_VALUE, defaultValue);
	}

	/**
	 * Mutates the default value of this annotation type member declaration.
	 *
	 * @param mutation the mutation to apply to the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withDefaultValue(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SAnnotationMemberDecl.DEFAULT_VALUE, mutation);
	}

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @param defaultValue the replacement for the default value of this annotation type member declaration.
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withDefaultValue(Expr defaultValue) {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DEFAULT_VALUE, Trees.some(defaultValue));
	}

	/**
	 * Replaces the default value of this annotation type member declaration.
	 *
	 * @return the resulting mutated annotation type member declaration.
	 */
	public AnnotationMemberDecl withNoDefaultValue() {
		return location.safeTraversalReplace(SAnnotationMemberDecl.DEFAULT_VALUE, Trees.<Expr>none());
	}
}
