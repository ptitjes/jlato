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
 * A 'throw' statement.
 */
public interface ThrowStmt extends Stmt, TreeCombinators<ThrowStmt> {

	/**
	 * Returns the expression of this 'throw' statement.
	 *
	 * @return the expression of this 'throw' statement.
	 */
	Expr expr();

	/**
	 * Replaces the expression of this 'throw' statement.
	 *
	 * @param expr the replacement for the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	ThrowStmt withExpr(Expr expr);

	/**
	 * Mutates the expression of this 'throw' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	ThrowStmt withExpr(Mutation<Expr> mutation);
}
