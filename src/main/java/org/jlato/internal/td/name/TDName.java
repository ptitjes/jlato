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

package org.jlato.internal.td.name;

import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A name.
 */
public class TDName extends TDTree<SName, Expr, Name> implements Name {

	/**
	 * Returns the kind of this name.
	 *
	 * @return the kind of this name.
	 */
	public Kind kind() {
		return Kind.Name;
	}

	/**
	 * Creates a name for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDName(TDLocation<SName> location) {
		super(location);
	}

	/**
	 * Creates a name with the specified child trees.
	 *
	 * @param id the identifier child tree.
	 */
	public TDName(String id) {
		super(new TDLocation<SName>(SName.make(id)));
	}

	/**
	 * Returns the identifier of this name.
	 *
	 * @return the identifier of this name.
	 */
	public String id() {
		return location.safeProperty(SName.ID);
	}

	/**
	 * Replaces the identifier of this name.
	 *
	 * @param id the replacement for the identifier of this name.
	 * @return the resulting mutated name.
	 */
	public Name withId(String id) {
		return location.safePropertyReplace(SName.ID, id);
	}

	/**
	 * Mutates the identifier of this name.
	 *
	 * @param mutation the mutation to apply to the identifier of this name.
	 * @return the resulting mutated name.
	 */
	public Name withId(Mutation<String> mutation) {
		return location.safePropertyMutate(SName.ID, mutation);
	}
}
