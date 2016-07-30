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

import org.jlato.internal.bu.decl.SModifier;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.decl.ModifierKeyword;
import org.jlato.util.Mutation;

/**
 * A modifier.
 */
public class TDModifier extends TDTree<SModifier, ExtendedModifier, Modifier> implements Modifier {

	/**
	 * Returns the kind of this modifier.
	 *
	 * @return the kind of this modifier.
	 */
	public Kind kind() {
		return Kind.Modifier;
	}

	/**
	 * Creates a modifier for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDModifier(TDLocation<SModifier> location) {
		super(location);
	}

	/**
	 * Creates a modifier with the specified child trees.
	 *
	 * @param keyword the keyword child tree.
	 */
	public TDModifier(ModifierKeyword keyword) {
		super(new TDLocation<SModifier>(SModifier.make(keyword)));
	}

	/**
	 * Returns the keyword of this modifier.
	 *
	 * @return the keyword of this modifier.
	 */
	public ModifierKeyword keyword() {
		return location.safeProperty(SModifier.KEYWORD);
	}

	/**
	 * Replaces the keyword of this modifier.
	 *
	 * @param keyword the replacement for the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	public Modifier withKeyword(ModifierKeyword keyword) {
		return location.safePropertyReplace(SModifier.KEYWORD, keyword);
	}

	/**
	 * Mutates the keyword of this modifier.
	 *
	 * @param mutation the mutation to apply to the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	public Modifier withKeyword(Mutation<ModifierKeyword> mutation) {
		return location.safePropertyMutate(SModifier.KEYWORD, mutation);
	}
}
