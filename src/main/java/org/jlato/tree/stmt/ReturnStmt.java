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
 * A 'return' statement.
 */
public interface ReturnStmt extends Stmt, TreeCombinators<ReturnStmt> {

	/**
	 * Returns the expression of this 'return' statement.
	 *
	 * @return the expression of this 'return' statement.
	 */
	NodeOption<Expr> expr();

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @param expr the replacement for the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withExpr(NodeOption<Expr> expr);

	/**
	 * Mutates the expression of this 'return' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @param expr the replacement for the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withExpr(Expr expr);

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withNoExpr();
}
