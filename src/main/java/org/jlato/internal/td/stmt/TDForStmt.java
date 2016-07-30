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
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SForStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ForStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A 'for' statement.
 */
public class TDForStmt extends TDTree<SForStmt, Stmt, ForStmt> implements ForStmt {

	/**
	 * Returns the kind of this 'for' statement.
	 *
	 * @return the kind of this 'for' statement.
	 */
	public Kind kind() {
		return Kind.ForStmt;
	}

	/**
	 * Creates a 'for' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDForStmt(TDLocation<SForStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'for' statement with the specified child trees.
	 *
	 * @param init    the init child tree.
	 * @param compare the compare child tree.
	 * @param update  the update child tree.
	 * @param body    the body child tree.
	 */
	public TDForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new TDLocation<SForStmt>(SForStmt.make(TDTree.<SNodeList>treeOf(init), TDTree.<SExpr>treeOf(compare), TDTree.<SNodeList>treeOf(update), TDTree.<SStmt>treeOf(body))));
	}

	/**
	 * Returns the init of this 'for' statement.
	 *
	 * @return the init of this 'for' statement.
	 */
	public NodeList<Expr> init() {
		return location.safeTraversal(SForStmt.INIT);
	}

	/**
	 * Replaces the init of this 'for' statement.
	 *
	 * @param init the replacement for the init of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withInit(NodeList<Expr> init) {
		return location.safeTraversalReplace(SForStmt.INIT, init);
	}

	/**
	 * Mutates the init of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the init of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withInit(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SForStmt.INIT, mutation);
	}

	/**
	 * Returns the compare of this 'for' statement.
	 *
	 * @return the compare of this 'for' statement.
	 */
	public Expr compare() {
		return location.safeTraversal(SForStmt.COMPARE);
	}

	/**
	 * Replaces the compare of this 'for' statement.
	 *
	 * @param compare the replacement for the compare of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withCompare(Expr compare) {
		return location.safeTraversalReplace(SForStmt.COMPARE, compare);
	}

	/**
	 * Mutates the compare of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the compare of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withCompare(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SForStmt.COMPARE, mutation);
	}

	/**
	 * Returns the update of this 'for' statement.
	 *
	 * @return the update of this 'for' statement.
	 */
	public NodeList<Expr> update() {
		return location.safeTraversal(SForStmt.UPDATE);
	}

	/**
	 * Replaces the update of this 'for' statement.
	 *
	 * @param update the replacement for the update of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.safeTraversalReplace(SForStmt.UPDATE, update);
	}

	/**
	 * Mutates the update of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the update of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withUpdate(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SForStmt.UPDATE, mutation);
	}

	/**
	 * Returns the body of this 'for' statement.
	 *
	 * @return the body of this 'for' statement.
	 */
	public Stmt body() {
		return location.safeTraversal(SForStmt.BODY);
	}

	/**
	 * Replaces the body of this 'for' statement.
	 *
	 * @param body the replacement for the body of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SForStmt.BODY, body);
	}

	/**
	 * Mutates the body of this 'for' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'for' statement.
	 * @return the resulting mutated 'for' statement.
	 */
	public ForStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SForStmt.BODY, mutation);
	}
}
