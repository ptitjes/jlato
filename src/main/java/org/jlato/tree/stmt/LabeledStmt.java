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

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A labeled statement.
 */
public interface LabeledStmt extends Stmt, TreeCombinators<LabeledStmt> {

	/**
	 * Returns the label of this labeled statement.
	 *
	 * @return the label of this labeled statement.
	 */
	Name label();

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(Name label);

	/**
	 * Mutates the label of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(Mutation<Name> mutation);

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withLabel(String label);

	/**
	 * Returns the statement of this labeled statement.
	 *
	 * @return the statement of this labeled statement.
	 */
	Stmt stmt();

	/**
	 * Replaces the statement of this labeled statement.
	 *
	 * @param stmt the replacement for the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withStmt(Stmt stmt);

	/**
	 * Mutates the statement of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	LabeledStmt withStmt(Mutation<Stmt> mutation);
}
