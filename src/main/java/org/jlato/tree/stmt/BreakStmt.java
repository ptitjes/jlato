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

package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A 'break' statement.
 */
public interface BreakStmt extends Stmt, TreeCombinators<BreakStmt> {

	/**
	 * Returns the identifier of this 'break' statement.
	 *
	 * @return the identifier of this 'break' statement.
	 */
	NodeOption<Name> id();

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @param id the replacement for the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(NodeOption<Name> id);

	/**
	 * Mutates the identifier of this 'break' statement.
	 *
	 * @param mutation the mutation to apply to the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(Mutation<NodeOption<Name>> mutation);

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @param id the replacement for the identifier of this 'break' statement.
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withId(Name id);

	/**
	 * Replaces the identifier of this 'break' statement.
	 *
	 * @return the resulting mutated 'break' statement.
	 */
	BreakStmt withNoId();
}
