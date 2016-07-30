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
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

/**
 * A "enhanced" 'for' statement.
 */
public interface ForeachStmt extends Stmt, TreeCombinators<ForeachStmt> {

	/**
	 * Returns the var of this "enhanced" 'for' statement.
	 *
	 * @return the var of this "enhanced" 'for' statement.
	 */
	VariableDeclarationExpr var();

	/**
	 * Replaces the var of this "enhanced" 'for' statement.
	 *
	 * @param var the replacement for the var of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withVar(VariableDeclarationExpr var);

	/**
	 * Mutates the var of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the var of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation);

	/**
	 * Returns the iterable of this "enhanced" 'for' statement.
	 *
	 * @return the iterable of this "enhanced" 'for' statement.
	 */
	Expr iterable();

	/**
	 * Replaces the iterable of this "enhanced" 'for' statement.
	 *
	 * @param iterable the replacement for the iterable of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withIterable(Expr iterable);

	/**
	 * Mutates the iterable of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the iterable of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withIterable(Mutation<Expr> mutation);

	/**
	 * Returns the body of this "enhanced" 'for' statement.
	 *
	 * @return the body of this "enhanced" 'for' statement.
	 */
	Stmt body();

	/**
	 * Replaces the body of this "enhanced" 'for' statement.
	 *
	 * @param body the replacement for the body of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withBody(Stmt body);

	/**
	 * Mutates the body of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the body of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	ForeachStmt withBody(Mutation<Stmt> mutation);
}
