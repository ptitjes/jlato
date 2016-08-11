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

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDArrayDim;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;

/**
 * A state object for an array dimension.
 */
public class SArrayDim extends SNode<SArrayDim> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new array dimension.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array dimension.
	 */
	public static BUTree<SArrayDim> make(BUTree<SNodeList> annotations) {
		return new BUTree<SArrayDim>(new SArrayDim(annotations));
	}

	/**
	 * The annotations of this array dimension state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * Constructs an array dimension state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 */
	public SArrayDim(BUTree<SNodeList> annotations) {
		this.annotations = annotations;
	}

	/**
	 * Returns the kind of this array dimension.
	 *
	 * @return the kind of this array dimension.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayDim;
	}

	/**
	 * Replaces the annotations of this array dimension state.
	 *
	 * @param annotations the replacement for the annotations of this array dimension state.
	 * @return the resulting mutated array dimension state.
	 */
	public SArrayDim withAnnotations(BUTree<SNodeList> annotations) {
		return new SArrayDim(annotations);
	}

	/**
	 * Builds an array dimension facade for the specified array dimension <code>TDLocation</code>.
	 *
	 * @param location the array dimension <code>TDLocation</code>.
	 * @return an array dimension facade for the specified array dimension <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayDim> location) {
		return new TDArrayDim(location);
	}

	/**
	 * Returns the shape for this array dimension state.
	 *
	 * @return the shape for this array dimension state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this array dimension state.
	 *
	 * @return the first child traversal for this array dimension state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this array dimension state.
	 *
	 * @return the last child traversal for this array dimension state.
	 */
	@Override
	public STraversal lastChild() {
		return ANNOTATIONS;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SArrayDim state = (SArrayDim) o;
		if (!annotations.equals(state.annotations))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayDim, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDim, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayDim state) {
			return state.annotations;
		}

		@Override
		public SArrayDim doRebuildParentState(SArrayDim state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
