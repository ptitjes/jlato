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
 * An 'if' statement.
 */
public interface IfStmt extends Stmt, TreeCombinators<IfStmt> {

	/**
	 * Returns the condition of this 'if' statement.
	 *
	 * @return the condition of this 'if' statement.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this 'if' statement.
	 *
	 * @param condition the replacement for the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withCondition(Expr condition);

	/**
	 * Mutates the condition of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withCondition(Mutation<Expr> mutation);

	/**
	 * Returns the then statement of this 'if' statement.
	 *
	 * @return the then statement of this 'if' statement.
	 */
	Stmt thenStmt();

	/**
	 * Replaces the then statement of this 'if' statement.
	 *
	 * @param thenStmt the replacement for the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withThenStmt(Stmt thenStmt);

	/**
	 * Mutates the then statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withThenStmt(Mutation<Stmt> mutation);

	/**
	 * Returns the else statement of this 'if' statement.
	 *
	 * @return the else statement of this 'if' statement.
	 */
	NodeOption<Stmt> elseStmt();

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withElseStmt(NodeOption<Stmt> elseStmt);

	/**
	 * Mutates the else statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation);

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withElseStmt(Stmt elseStmt);

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @return the resulting mutated 'if' statement.
	 */
	IfStmt withNoElseStmt();
}
