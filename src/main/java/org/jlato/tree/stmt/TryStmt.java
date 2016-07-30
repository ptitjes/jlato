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
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

/**
 * A 'try' statement.
 */
public interface TryStmt extends Stmt, TreeCombinators<TryStmt> {

	/**
	 * Returns the resources of this 'try' statement.
	 *
	 * @return the resources of this 'try' statement.
	 */
	NodeList<VariableDeclarationExpr> resources();

	/**
	 * Replaces the resources of this 'try' statement.
	 *
	 * @param resources the replacement for the resources of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withResources(NodeList<VariableDeclarationExpr> resources);

	/**
	 * Mutates the resources of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the resources of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation);

	/**
	 * Tests whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @return <code>true</code> if this 'try' statement has a trailing semi-colon for its resources, <code>false</code> otherwise.
	 */
	boolean trailingSemiColon();

	/**
	 * Sets whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @param trailingSemiColon <code>true</code> if this 'try' statement has a trailing semi-colon for its resources, <code>false</code> otherwise.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withTrailingSemiColon(boolean trailingSemiColon);

	/**
	 * Mutates whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @param mutation the mutation to apply to whether this 'try' statement has a trailing semi-colon for its resources.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withTrailingSemiColon(Mutation<Boolean> mutation);

	/**
	 * Returns the 'try' block of this 'try' statement.
	 *
	 * @return the 'try' block of this 'try' statement.
	 */
	BlockStmt tryBlock();

	/**
	 * Replaces the 'try' block of this 'try' statement.
	 *
	 * @param tryBlock the replacement for the 'try' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withTryBlock(BlockStmt tryBlock);

	/**
	 * Mutates the 'try' block of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the 'try' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withTryBlock(Mutation<BlockStmt> mutation);

	/**
	 * Returns the catchs of this 'try' statement.
	 *
	 * @return the catchs of this 'try' statement.
	 */
	NodeList<CatchClause> catchs();

	/**
	 * Replaces the catchs of this 'try' statement.
	 *
	 * @param catchs the replacement for the catchs of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withCatchs(NodeList<CatchClause> catchs);

	/**
	 * Mutates the catchs of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the catchs of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation);

	/**
	 * Returns the 'finally' block of this 'try' statement.
	 *
	 * @return the 'finally' block of this 'try' statement.
	 */
	NodeOption<BlockStmt> finallyBlock();

	/**
	 * Replaces the 'finally' block of this 'try' statement.
	 *
	 * @param finallyBlock the replacement for the 'finally' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock);

	/**
	 * Mutates the 'finally' block of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the 'finally' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation);

	/**
	 * Replaces the 'finally' block of this 'try' statement.
	 *
	 * @param finallyBlock the replacement for the 'finally' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withFinallyBlock(BlockStmt finallyBlock);

	/**
	 * Replaces the 'finally' block of this 'try' statement.
	 *
	 * @return the resulting mutated 'try' statement.
	 */
	TryStmt withNoFinallyBlock();
}
