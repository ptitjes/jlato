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

import org.jlato.tree.Node;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.util.Mutation;

/**
 * A 'catch' clause.
 */
public interface CatchClause extends Node, TreeCombinators<CatchClause> {

	/**
	 * Returns the parameter of this 'catch' clause.
	 *
	 * @return the parameter of this 'catch' clause.
	 */
	FormalParameter param();

	/**
	 * Replaces the parameter of this 'catch' clause.
	 *
	 * @param param the replacement for the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withParam(FormalParameter param);

	/**
	 * Mutates the parameter of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withParam(Mutation<FormalParameter> mutation);

	/**
	 * Returns the 'catch' block of this 'catch' clause.
	 *
	 * @return the 'catch' block of this 'catch' clause.
	 */
	BlockStmt catchBlock();

	/**
	 * Replaces the 'catch' block of this 'catch' clause.
	 *
	 * @param catchBlock the replacement for the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withCatchBlock(BlockStmt catchBlock);

	/**
	 * Mutates the 'catch' block of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withCatchBlock(Mutation<BlockStmt> mutation);
}
