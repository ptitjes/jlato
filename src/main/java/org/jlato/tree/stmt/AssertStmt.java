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
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An 'assert' statement.
 */
public interface AssertStmt extends Stmt, TreeCombinators<AssertStmt> {

	/**
	 * Returns the check of this 'assert' statement.
	 *
	 * @return the check of this 'assert' statement.
	 */
	Expr check();

	/**
	 * Replaces the check of this 'assert' statement.
	 *
	 * @param check the replacement for the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withCheck(Expr check);

	/**
	 * Mutates the check of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the check of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withCheck(Mutation<Expr> mutation);

	/**
	 * Returns the msg of this 'assert' statement.
	 *
	 * @return the msg of this 'assert' statement.
	 */
	NodeOption<Expr> msg();

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(NodeOption<Expr> msg);

	/**
	 * Mutates the msg of this 'assert' statement.
	 *
	 * @param mutation the mutation to apply to the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement.
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withMsg(Expr msg);

	/**
	 * Replaces the msg of this 'assert' statement.
	 *
	 * @return the resulting mutated 'assert' statement.
	 */
	AssertStmt withNoMsg();
}
