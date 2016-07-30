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

package org.jlato.tree.expr;

import org.jlato.tree.Node;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An annotation member value pair.
 */
public interface MemberValuePair extends Node, TreeCombinators<MemberValuePair> {

	/**
	 * Returns the name of this annotation member value pair.
	 *
	 * @return the name of this annotation member value pair.
	 */
	Name name();

	/**
	 * Replaces the name of this annotation member value pair.
	 *
	 * @param name the replacement for the name of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	MemberValuePair withName(Name name);

	/**
	 * Mutates the name of this annotation member value pair.
	 *
	 * @param mutation the mutation to apply to the name of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	MemberValuePair withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this annotation member value pair.
	 *
	 * @param name the replacement for the name of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	MemberValuePair withName(String name);

	/**
	 * Returns the value of this annotation member value pair.
	 *
	 * @return the value of this annotation member value pair.
	 */
	Expr value();

	/**
	 * Replaces the value of this annotation member value pair.
	 *
	 * @param value the replacement for the value of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	MemberValuePair withValue(Expr value);

	/**
	 * Mutates the value of this annotation member value pair.
	 *
	 * @param mutation the mutation to apply to the value of this annotation member value pair.
	 * @return the resulting mutated annotation member value pair.
	 */
	MemberValuePair withValue(Mutation<Expr> mutation);
}
