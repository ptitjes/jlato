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

package org.jlato.internal.td.type;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SWildcardType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.ReferenceType;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.WildcardType;
import org.jlato.util.Mutation;

/**
 * A wildcard type.
 */
public class TDWildcardType extends TDTree<SWildcardType, Type, WildcardType> implements WildcardType {

	/**
	 * Returns the kind of this wildcard type.
	 *
	 * @return the kind of this wildcard type.
	 */
	public Kind kind() {
		return Kind.WildcardType;
	}

	/**
	 * Creates a wildcard type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDWildcardType(TDLocation<SWildcardType> location) {
		super(location);
	}

	/**
	 * Creates a wildcard type with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param ext         the upper bound child tree.
	 * @param sup         the lower bound child tree.
	 */
	public TDWildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new TDLocation<SWildcardType>(SWildcardType.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SNodeOption>treeOf(ext), TDTree.<SNodeOption>treeOf(sup))));
	}

	/**
	 * Returns the annotations of this wildcard type.
	 *
	 * @return the annotations of this wildcard type.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SWildcardType.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this wildcard type.
	 *
	 * @param annotations the replacement for the annotations of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SWildcardType.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the annotations of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SWildcardType.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the upper bound of this wildcard type.
	 *
	 * @return the upper bound of this wildcard type.
	 */
	public NodeOption<ReferenceType> ext() {
		return location.safeTraversal(SWildcardType.EXT);
	}

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @param ext the replacement for the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withExt(NodeOption<ReferenceType> ext) {
		return location.safeTraversalReplace(SWildcardType.EXT, ext);
	}

	/**
	 * Mutates the upper bound of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withExt(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(SWildcardType.EXT, mutation);
	}

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @param ext the replacement for the upper bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withExt(ReferenceType ext) {
		return location.safeTraversalReplace(SWildcardType.EXT, Trees.some(ext));
	}

	/**
	 * Replaces the upper bound of this wildcard type.
	 *
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withNoExt() {
		return location.safeTraversalReplace(SWildcardType.EXT, Trees.<ReferenceType>none());
	}

	/**
	 * Returns the lower bound of this wildcard type.
	 *
	 * @return the lower bound of this wildcard type.
	 */
	public NodeOption<ReferenceType> sup() {
		return location.safeTraversal(SWildcardType.SUP);
	}

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @param sup the replacement for the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withSup(NodeOption<ReferenceType> sup) {
		return location.safeTraversalReplace(SWildcardType.SUP, sup);
	}

	/**
	 * Mutates the lower bound of this wildcard type.
	 *
	 * @param mutation the mutation to apply to the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withSup(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(SWildcardType.SUP, mutation);
	}

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @param sup the replacement for the lower bound of this wildcard type.
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withSup(ReferenceType sup) {
		return location.safeTraversalReplace(SWildcardType.SUP, Trees.some(sup));
	}

	/**
	 * Replaces the lower bound of this wildcard type.
	 *
	 * @return the resulting mutated wildcard type.
	 */
	public WildcardType withNoSup() {
		return location.safeTraversalReplace(SWildcardType.SUP, Trees.<ReferenceType>none());
	}
}
