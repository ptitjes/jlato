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

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SReturnStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ReturnStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A 'return' statement.
 */
public class TDReturnStmt extends TDTree<SReturnStmt, Stmt, ReturnStmt> implements ReturnStmt {

	/**
	 * Returns the kind of this 'return' statement.
	 *
	 * @return the kind of this 'return' statement.
	 */
	public Kind kind() {
		return Kind.ReturnStmt;
	}

	/**
	 * Creates a 'return' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDReturnStmt(TDLocation<SReturnStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'return' statement with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 */
	public TDReturnStmt(NodeOption<Expr> expr) {
		super(new TDLocation<SReturnStmt>(SReturnStmt.make(TDTree.<SNodeOption>treeOf(expr))));
	}

	/**
	 * Returns the expression of this 'return' statement.
	 *
	 * @return the expression of this 'return' statement.
	 */
	public NodeOption<Expr> expr() {
		return location.safeTraversal(SReturnStmt.EXPR);
	}

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @param expr the replacement for the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	public ReturnStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(SReturnStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this 'return' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	public ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SReturnStmt.EXPR, mutation);
	}

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @param expr the replacement for the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	public ReturnStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SReturnStmt.EXPR, Trees.some(expr));
	}

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @return the resulting mutated 'return' statement.
	 */
	public ReturnStmt withNoExpr() {
		return location.safeTraversalReplace(SReturnStmt.EXPR, Trees.<Expr>none());
	}
}
