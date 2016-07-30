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
import org.jlato.internal.bu.decl.SEnumDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.EnumDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * An enum declaration.
 */
public class TDEnumDecl extends TDTree<SEnumDecl, TypeDecl, EnumDecl> implements EnumDecl {

	/**
	 * Returns the kind of this enum declaration.
	 *
	 * @return the kind of this enum declaration.
	 */
	public Kind kind() {
		return Kind.EnumDecl;
	}

	/**
	 * Creates an enum declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEnumDecl(TDLocation<SEnumDecl> location) {
		super(location);
	}

	/**
	 * Creates an enum declaration with the specified child trees.
	 *
	 * @param modifiers        the modifiers child tree.
	 * @param name             the name child tree.
	 * @param implementsClause the 'implements' clause child tree.
	 * @param enumConstants    the enum constants child tree.
	 * @param trailingComma    the has a trailing comma child tree.
	 * @param members          the members child tree.
	 */
	public TDEnumDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new TDLocation<SEnumDecl>(SEnumDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(implementsClause), TDTree.<SNodeList>treeOf(enumConstants), trailingComma, TDTree.<SNodeList>treeOf(members))));
	}

	/**
	 * Returns the modifiers of this enum declaration.
	 *
	 * @return the modifiers of this enum declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SEnumDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this enum declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SEnumDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the name of this enum declaration.
	 *
	 * @return the name of this enum declaration.
	 */
	public Name name() {
		return location.safeTraversal(SEnumDecl.NAME);
	}

	/**
	 * Replaces the name of this enum declaration.
	 *
	 * @param name the replacement for the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withName(Name name) {
		return location.safeTraversalReplace(SEnumDecl.NAME, name);
	}

	/**
	 * Mutates the name of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SEnumDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this enum declaration.
	 *
	 * @param name the replacement for the name of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withName(String name) {
		return location.safeTraversalReplace(SEnumDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the 'implements' clause of this enum declaration.
	 *
	 * @return the 'implements' clause of this enum declaration.
	 */
	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(SEnumDecl.IMPLEMENTS_CLAUSE);
	}

	/**
	 * Replaces the 'implements' clause of this enum declaration.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(SEnumDecl.IMPLEMENTS_CLAUSE, implementsClause);
	}

	/**
	 * Mutates the 'implements' clause of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the 'implements' clause of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.IMPLEMENTS_CLAUSE, mutation);
	}

	/**
	 * Returns the enum constants of this enum declaration.
	 *
	 * @return the enum constants of this enum declaration.
	 */
	public NodeList<EnumConstantDecl> enumConstants() {
		return location.safeTraversal(SEnumDecl.ENUM_CONSTANTS);
	}

	/**
	 * Replaces the enum constants of this enum declaration.
	 *
	 * @param enumConstants the replacement for the enum constants of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.safeTraversalReplace(SEnumDecl.ENUM_CONSTANTS, enumConstants);
	}

	/**
	 * Mutates the enum constants of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the enum constants of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withEnumConstants(Mutation<NodeList<EnumConstantDecl>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.ENUM_CONSTANTS, mutation);
	}

	/**
	 * Tests whether this enum declaration has a trailing comma.
	 *
	 * @return <code>true</code> if this enum declaration has a trailing comma, <code>false</code> otherwise.
	 */
	public boolean trailingComma() {
		return location.safeProperty(SEnumDecl.TRAILING_COMMA);
	}

	/**
	 * Sets whether this enum declaration has a trailing comma.
	 *
	 * @param trailingComma <code>true</code> if this enum declaration has a trailing comma, <code>false</code> otherwise.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(SEnumDecl.TRAILING_COMMA, trailingComma);
	}

	/**
	 * Mutates whether this enum declaration has a trailing comma.
	 *
	 * @param mutation the mutation to apply to whether this enum declaration has a trailing comma.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SEnumDecl.TRAILING_COMMA, mutation);
	}

	/**
	 * Returns the members of this enum declaration.
	 *
	 * @return the members of this enum declaration.
	 */
	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SEnumDecl.MEMBERS);
	}

	/**
	 * Replaces the members of this enum declaration.
	 *
	 * @param members the replacement for the members of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SEnumDecl.MEMBERS, members);
	}

	/**
	 * Mutates the members of this enum declaration.
	 *
	 * @param mutation the mutation to apply to the members of this enum declaration.
	 * @return the resulting mutated enum declaration.
	 */
	public EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.MEMBERS, mutation);
	}
}
