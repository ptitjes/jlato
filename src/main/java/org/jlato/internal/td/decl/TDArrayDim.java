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
import org.jlato.internal.bu.decl.SArrayDim;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

/**
 * An array dimension.
 */
public class TDArrayDim extends TDTree<SArrayDim, Node, ArrayDim> implements ArrayDim {

	/**
	 * Returns the kind of this array dimension.
	 *
	 * @return the kind of this array dimension.
	 */
	public Kind kind() {
		return Kind.ArrayDim;
	}

	/**
	 * Creates an array dimension for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayDim(TDLocation<SArrayDim> location) {
		super(location);
	}

	/**
	 * Creates an array dimension with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 */
	public TDArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new TDLocation<SArrayDim>(SArrayDim.make(TDTree.<SNodeList>treeOf(annotations))));
	}

	/**
	 * Returns the annotations of this array dimension.
	 *
	 * @return the annotations of this array dimension.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SArrayDim.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this array dimension.
	 *
	 * @param annotations the replacement for the annotations of this array dimension.
	 * @return the resulting mutated array dimension.
	 */
	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SArrayDim.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this array dimension.
	 *
	 * @param mutation the mutation to apply to the annotations of this array dimension.
	 * @return the resulting mutated array dimension.
	 */
	public ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SArrayDim.ANNOTATIONS, mutation);
	}
}
