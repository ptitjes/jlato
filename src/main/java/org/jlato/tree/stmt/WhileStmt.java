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
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'while' statement.
 */
public interface WhileStmt extends Stmt, TreeCombinators<WhileStmt> {

	/**
	 * Returns the condition of this 'while' statement.
	 *
	 * @return the condition of this 'while' statement.
	 */
	Expr condition();

	/**
	 * Replaces the condition of this 'while' statement.
	 *
	 * @param condition the replacement for the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withCondition(Expr condition);

	/**
	 * Mutates the condition of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withCondition(Mutation<Expr> mutation);

	/**
	 * Returns the body of this 'while' statement.
	 *
	 * @return the body of this 'while' statement.
	 */
	Stmt body();

	/**
	 * Replaces the body of this 'while' statement.
	 *
	 * @param body the replacement for the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withBody(Stmt body);

	/**
	 * Mutates the body of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	WhileStmt withBody(Mutation<Stmt> mutation);
}
