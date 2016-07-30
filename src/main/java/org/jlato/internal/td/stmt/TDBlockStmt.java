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

package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A block statement.
 */
public class TDBlockStmt extends TDTree<SBlockStmt, Stmt, BlockStmt> implements BlockStmt {

	/**
	 * Returns the kind of this block statement.
	 *
	 * @return the kind of this block statement.
	 */
	public Kind kind() {
		return Kind.BlockStmt;
	}

	/**
	 * Creates a block statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDBlockStmt(TDLocation<SBlockStmt> location) {
		super(location);
	}

	/**
	 * Creates a block statement with the specified child trees.
	 *
	 * @param stmts the statements child tree.
	 */
	public TDBlockStmt(NodeList<Stmt> stmts) {
		super(new TDLocation<SBlockStmt>(SBlockStmt.make(TDTree.<SNodeList>treeOf(stmts))));
	}

	/**
	 * Returns the statements of this block statement.
	 *
	 * @return the statements of this block statement.
	 */
	public NodeList<Stmt> stmts() {
		return location.safeTraversal(SBlockStmt.STMTS);
	}

	/**
	 * Replaces the statements of this block statement.
	 *
	 * @param stmts the replacement for the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(SBlockStmt.STMTS, stmts);
	}

	/**
	 * Mutates the statements of this block statement.
	 *
	 * @param mutation the mutation to apply to the statements of this block statement.
	 * @return the resulting mutated block statement.
	 */
	public BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(SBlockStmt.STMTS, mutation);
	}
}
