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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEmptyTypeDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.token;

/**
 * A state object for an empty type declaration.
 */
public class SEmptyTypeDecl extends SNode<SEmptyTypeDecl> implements STypeDecl {

	/**
	 * Creates a <code>BUTree</code> with a new empty type declaration.
	 *
	 * @return the new <code>BUTree</code> with an empty type declaration.
	 */
	public static BUTree<SEmptyTypeDecl> make() {
		return new BUTree<SEmptyTypeDecl>(new SEmptyTypeDecl());
	}

	/**
	 * Constructs an empty type declaration state.
	 */
	public SEmptyTypeDecl() {
	}

	/**
	 * Returns the kind of this empty type declaration.
	 *
	 * @return the kind of this empty type declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	/**
	 * Builds an empty type declaration facade for the specified empty type declaration <code>TDLocation</code>.
	 *
	 * @param location the empty type declaration <code>TDLocation</code>.
	 * @return an empty type declaration facade for the specified empty type declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SEmptyTypeDecl> location) {
		return new TDEmptyTypeDecl(location);
	}

	/**
	 * Returns the shape for this empty type declaration state.
	 *
	 * @return the shape for this empty type declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this empty type declaration state.
	 *
	 * @return the first child traversal for this empty type declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this empty type declaration state.
	 *
	 * @return the last child traversal for this empty type declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return null;
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
		return result;
	}

	public static final LexicalShape shape = token(LToken.SemiColon);
}
