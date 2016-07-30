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

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'switch' statement.
 */
public interface SwitchStmt extends Stmt, TreeCombinators<SwitchStmt> {

	/**
	 * Returns the selector of this 'switch' statement.
	 *
	 * @return the selector of this 'switch' statement.
	 */
	Expr selector();

	/**
	 * Replaces the selector of this 'switch' statement.
	 *
	 * @param selector the replacement for the selector of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	SwitchStmt withSelector(Expr selector);

	/**
	 * Mutates the selector of this 'switch' statement.
	 *
	 * @param mutation the mutation to apply to the selector of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	SwitchStmt withSelector(Mutation<Expr> mutation);

	/**
	 * Returns the cases of this 'switch' statement.
	 *
	 * @return the cases of this 'switch' statement.
	 */
	NodeList<SwitchCase> cases();

	/**
	 * Replaces the cases of this 'switch' statement.
	 *
	 * @param cases the replacement for the cases of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	SwitchStmt withCases(NodeList<SwitchCase> cases);

	/**
	 * Mutates the cases of this 'switch' statement.
	 *
	 * @param mutation the mutation to apply to the cases of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation);
}
