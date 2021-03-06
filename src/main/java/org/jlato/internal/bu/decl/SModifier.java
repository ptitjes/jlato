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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDModifier;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ModifierKeyword;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.keyword;
import static org.jlato.internal.shapes.LexicalShape.token;

/**
 * A state object for a modifier.
 */
public class SModifier extends SNode<SModifier> implements SExtendedModifier {

	/**
	 * Creates a <code>BUTree</code> with a new modifier.
	 *
	 * @param keyword the keyword child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a modifier.
	 */
	public static BUTree<SModifier> make(ModifierKeyword keyword) {
		return new BUTree<SModifier>(new SModifier(keyword));
	}

	/**
	 * The keyword of this modifier state.
	 */
	public final ModifierKeyword keyword;

	/**
	 * Constructs a modifier state.
	 *
	 * @param keyword the keyword child <code>BUTree</code>.
	 */
	public SModifier(ModifierKeyword keyword) {
		this.keyword = keyword;
	}

	/**
	 * Returns the kind of this modifier.
	 *
	 * @return the kind of this modifier.
	 */
	@Override
	public Kind kind() {
		return Kind.Modifier;
	}

	/**
	 * Replaces the keyword of this modifier state.
	 *
	 * @param keyword the replacement for the keyword of this modifier state.
	 * @return the resulting mutated modifier state.
	 */
	public SModifier withKeyword(ModifierKeyword keyword) {
		return new SModifier(keyword);
	}

	/**
	 * Builds a modifier facade for the specified modifier <code>TDLocation</code>.
	 *
	 * @param location the modifier <code>TDLocation</code>.
	 * @return a modifier facade for the specified modifier <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SModifier> location) {
		return new TDModifier(location);
	}

	/**
	 * Returns the shape for this modifier state.
	 *
	 * @return the shape for this modifier state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this modifier state.
	 *
	 * @return the properties for this modifier state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(KEYWORD);
	}

	/**
	 * Returns the first child traversal for this modifier state.
	 *
	 * @return the first child traversal for this modifier state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this modifier state.
	 *
	 * @return the last child traversal for this modifier state.
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
		SModifier state = (SModifier) o;
		if (keyword == null ? state.keyword != null : !keyword.equals(state.keyword))
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
		if (keyword != null) result = 37 * result + keyword.hashCode();
		return result;
	}

	public static STypeSafeProperty<SModifier, ModifierKeyword> KEYWORD = new STypeSafeProperty<SModifier, ModifierKeyword>() {

		@Override
		public ModifierKeyword doRetrieve(SModifier state) {
			return state.keyword;
		}

		@Override
		public SModifier doRebuildParentState(SModifier state, ModifierKeyword value) {
			return state.withKeyword(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(BUTree tree) {
			final ModifierKeyword keyword = ((SModifier) tree.state).keyword;
			switch (keyword) {
				case Public:
					return LToken.Public;
				case Protected:
					return LToken.Protected;
				case Private:
					return LToken.Private;
				case Abstract:
					return LToken.Abstract;
				case Default:
					return LToken.Default;
				case Static:
					return LToken.Static;
				case Final:
					return LToken.Final;
				case Transient:
					return LToken.Transient;
				case Volatile:
					return LToken.Volatile;
				case Synchronized:
					return LToken.Synchronized;
				case Native:
					return LToken.Native;
				case StrictFP:
					return LToken.StrictFP;
				default:
					// Can't happen by definition of enum
					throw new IllegalStateException();
			}
		}
	});
}
