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

package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMarkerAnnotationExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.internal.shapes.LexicalShape.token;

/**
 * A state object for a marker annotation expression.
 */
public class SMarkerAnnotationExpr extends SNode<SMarkerAnnotationExpr> implements SAnnotationExpr {

	/**
	 * Creates a <code>BUTree</code> with a new marker annotation expression.
	 *
	 * @param name the name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a marker annotation expression.
	 */
	public static BUTree<SMarkerAnnotationExpr> make(BUTree<SQualifiedName> name) {
		return new BUTree<SMarkerAnnotationExpr>(new SMarkerAnnotationExpr(name));
	}

	/**
	 * The name of this marker annotation expression state.
	 */
	public final BUTree<SQualifiedName> name;

	/**
	 * Constructs a marker annotation expression state.
	 *
	 * @param name the name child <code>BUTree</code>.
	 */
	public SMarkerAnnotationExpr(BUTree<SQualifiedName> name) {
		this.name = name;
	}

	/**
	 * Returns the kind of this marker annotation expression.
	 *
	 * @return the kind of this marker annotation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	/**
	 * Replaces the name of this marker annotation expression state.
	 *
	 * @param name the replacement for the name of this marker annotation expression state.
	 * @return the resulting mutated marker annotation expression state.
	 */
	public SMarkerAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SMarkerAnnotationExpr(name);
	}

	/**
	 * Builds a marker annotation expression facade for the specified marker annotation expression <code>TDLocation</code>.
	 *
	 * @param location the marker annotation expression <code>TDLocation</code>.
	 * @return a marker annotation expression facade for the specified marker annotation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SMarkerAnnotationExpr> location) {
		return new TDMarkerAnnotationExpr(location);
	}

	/**
	 * Returns the shape for this marker annotation expression state.
	 *
	 * @return the shape for this marker annotation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this marker annotation expression state.
	 *
	 * @return the first child traversal for this marker annotation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this marker annotation expression state.
	 *
	 * @return the last child traversal for this marker annotation expression state.
	 */
	@Override
	public STraversal lastChild() {
		return NAME;
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
		SMarkerAnnotationExpr state = (SMarkerAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
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
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMarkerAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SMarkerAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SMarkerAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SMarkerAnnotationExpr doRebuildParentState(SMarkerAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
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
			token(LToken.At), child(NAME)
	);
}
