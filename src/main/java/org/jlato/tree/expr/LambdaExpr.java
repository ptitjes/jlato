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

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * A lambda expression.
 */
public interface LambdaExpr extends Expr, TreeCombinators<LambdaExpr> {

	/**
	 * Returns the parameters of this lambda expression.
	 *
	 * @return the parameters of this lambda expression.
	 */
	NodeList<FormalParameter> params();

	/**
	 * Replaces the parameters of this lambda expression.
	 *
	 * @param params the replacement for the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withParams(NodeList<FormalParameter> params);

	/**
	 * Mutates the parameters of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation);

	/**
	 * Tests whether this lambda expression has its arguments parenthesized.
	 *
	 * @return <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 */
	boolean hasParens();

	/**
	 * Sets whether this lambda expression has its arguments parenthesized.
	 *
	 * @param hasParens <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr setParens(boolean hasParens);

	/**
	 * Mutates whether this lambda expression has its arguments parenthesized.
	 *
	 * @param mutation the mutation to apply to whether this lambda expression has its arguments parenthesized.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr setParens(Mutation<Boolean> mutation);

	/**
	 * Returns the body of this lambda expression.
	 *
	 * @return the body of this lambda expression.
	 */
	NodeEither<Expr, BlockStmt> body();

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(NodeEither<Expr, BlockStmt> body);

	/**
	 * Mutates the body of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation);

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(Expr body);

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(BlockStmt body);
}
