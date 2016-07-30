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
import org.jlato.internal.bu.stmt.SSwitchStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.tree.stmt.SwitchStmt;
import org.jlato.util.Mutation;

/**
 * A 'switch' statement.
 */
public class TDSwitchStmt extends TDTree<SSwitchStmt, Stmt, SwitchStmt> implements SwitchStmt {

	/**
	 * Returns the kind of this 'switch' statement.
	 *
	 * @return the kind of this 'switch' statement.
	 */
	public Kind kind() {
		return Kind.SwitchStmt;
	}

	/**
	 * Creates a 'switch' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSwitchStmt(TDLocation<SSwitchStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'switch' statement with the specified child trees.
	 *
	 * @param selector the selector child tree.
	 * @param cases    the cases child tree.
	 */
	public TDSwitchStmt(Expr selector, NodeList<SwitchCase> cases) {
		super(new TDLocation<SSwitchStmt>(SSwitchStmt.make(TDTree.<SExpr>treeOf(selector), TDTree.<SNodeList>treeOf(cases))));
	}

	/**
	 * Returns the selector of this 'switch' statement.
	 *
	 * @return the selector of this 'switch' statement.
	 */
	public Expr selector() {
		return location.safeTraversal(SSwitchStmt.SELECTOR);
	}

	/**
	 * Replaces the selector of this 'switch' statement.
	 *
	 * @param selector the replacement for the selector of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	public SwitchStmt withSelector(Expr selector) {
		return location.safeTraversalReplace(SSwitchStmt.SELECTOR, selector);
	}

	/**
	 * Mutates the selector of this 'switch' statement.
	 *
	 * @param mutation the mutation to apply to the selector of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	public SwitchStmt withSelector(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSwitchStmt.SELECTOR, mutation);
	}

	/**
	 * Returns the cases of this 'switch' statement.
	 *
	 * @return the cases of this 'switch' statement.
	 */
	public NodeList<SwitchCase> cases() {
		return location.safeTraversal(SSwitchStmt.CASES);
	}

	/**
	 * Replaces the cases of this 'switch' statement.
	 *
	 * @param cases the replacement for the cases of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	public SwitchStmt withCases(NodeList<SwitchCase> cases) {
		return location.safeTraversalReplace(SSwitchStmt.CASES, cases);
	}

	/**
	 * Mutates the cases of this 'switch' statement.
	 *
	 * @param mutation the mutation to apply to the cases of this 'switch' statement.
	 * @return the resulting mutated 'switch' statement.
	 */
	public SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation) {
		return location.safeTraversalMutate(SSwitchStmt.CASES, mutation);
	}
}
