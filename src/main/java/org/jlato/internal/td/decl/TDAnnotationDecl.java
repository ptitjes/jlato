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
import org.jlato.internal.bu.decl.SAnnotationDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.AnnotationDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An annotation type declaration.
 */
public class TDAnnotationDecl extends TDTree<SAnnotationDecl, TypeDecl, AnnotationDecl> implements AnnotationDecl {

	/**
	 * Returns the kind of this annotation type declaration.
	 *
	 * @return the kind of this annotation type declaration.
	 */
	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	/**
	 * Creates an annotation type declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDAnnotationDecl(TDLocation<SAnnotationDecl> location) {
		super(location);
	}

	/**
	 * Creates an annotation type declaration with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param name      the name child tree.
	 * @param members   the members child tree.
	 */
	public TDAnnotationDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<MemberDecl> members) {
		super(new TDLocation<SAnnotationDecl>(SAnnotationDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(members))));
	}

	/**
	 * Returns the modifiers of this annotation type declaration.
	 *
	 * @return the modifiers of this annotation type declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SAnnotationDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this annotation type declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SAnnotationDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the name of this annotation type declaration.
	 *
	 * @return the name of this annotation type declaration.
	 */
	public Name name() {
		return location.safeTraversal(SAnnotationDecl.NAME);
	}

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withName(Name name) {
		return location.safeTraversalReplace(SAnnotationDecl.NAME, name);
	}

	/**
	 * Mutates the name of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this annotation type declaration.
	 *
	 * @param name the replacement for the name of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withName(String name) {
		return location.safeTraversalReplace(SAnnotationDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the members of this annotation type declaration.
	 *
	 * @return the members of this annotation type declaration.
	 */
	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SAnnotationDecl.MEMBERS);
	}

	/**
	 * Replaces the members of this annotation type declaration.
	 *
	 * @param members the replacement for the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SAnnotationDecl.MEMBERS, members);
	}

	/**
	 * Mutates the members of this annotation type declaration.
	 *
	 * @param mutation the mutation to apply to the members of this annotation type declaration.
	 * @return the resulting mutated annotation type declaration.
	 */
	public AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SAnnotationDecl.MEMBERS, mutation);
	}
}
