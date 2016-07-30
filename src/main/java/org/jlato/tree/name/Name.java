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

package org.jlato.tree.name;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A name.
 */
public interface Name extends Expr, TreeCombinators<Name> {

	/**
	 * Returns the identifier of this name.
	 *
	 * @return the identifier of this name.
	 */
	String id();

	/**
	 * Replaces the identifier of this name.
	 *
	 * @param id the replacement for the identifier of this name.
	 * @return the resulting mutated name.
	 */
	Name withId(String id);

	/**
	 * Mutates the identifier of this name.
	 *
	 * @param mutation the mutation to apply to the identifier of this name.
	 * @return the resulting mutated name.
	 */
	Name withId(Mutation<String> mutation);
}
