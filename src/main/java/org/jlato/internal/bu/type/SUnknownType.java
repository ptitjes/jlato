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

package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDUnknownType;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an unknown type.
 */
public class SUnknownType extends SNode<SUnknownType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new unknown type.
	 *
	 * @return the new <code>BUTree</code> with an unknown type.
	 */
	public static BUTree<SUnknownType> make() {
		return new BUTree<SUnknownType>(new SUnknownType());
	}

	/**
	 * Constructs an unknown type state.
	 */
	public SUnknownType() {
	}

	/**
	 * Returns the kind of this unknown type.
	 *
	 * @return the kind of this unknown type.
	 */
	@Override
	public Kind kind() {
		return Kind.UnknownType;
	}

	/**
	 * Builds an unknown type facade for the specified unknown type <code>TDLocation</code>.
	 *
	 * @param location the unknown type <code>TDLocation</code>.
	 * @return an unknown type facade for the specified unknown type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SUnknownType> location) {
		return new TDUnknownType(location);
	}

	/**
	 * Returns the shape for this unknown type state.
	 *
	 * @return the shape for this unknown type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this unknown type state.
	 *
	 * @return the first child traversal for this unknown type state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this unknown type state.
	 *
	 * @return the last child traversal for this unknown type state.
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

	public static final LexicalShape shape = none();
}
