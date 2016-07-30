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
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SIfStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.IfStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * An 'if' statement.
 */
public class TDIfStmt extends TDTree<SIfStmt, Stmt, IfStmt> implements IfStmt {

	/**
	 * Returns the kind of this 'if' statement.
	 *
	 * @return the kind of this 'if' statement.
	 */
	public Kind kind() {
		return Kind.IfStmt;
	}

	/**
	 * Creates an 'if' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDIfStmt(TDLocation<SIfStmt> location) {
		super(location);
	}

	/**
	 * Creates an 'if' statement with the specified child trees.
	 *
	 * @param condition the condition child tree.
	 * @param thenStmt  the then statement child tree.
	 * @param elseStmt  the else statement child tree.
	 */
	public TDIfStmt(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		super(new TDLocation<SIfStmt>(SIfStmt.make(TDTree.<SExpr>treeOf(condition), TDTree.<SStmt>treeOf(thenStmt), TDTree.<SNodeOption>treeOf(elseStmt))));
	}

	/**
	 * Returns the condition of this 'if' statement.
	 *
	 * @return the condition of this 'if' statement.
	 */
	public Expr condition() {
		return location.safeTraversal(SIfStmt.CONDITION);
	}

	/**
	 * Replaces the condition of this 'if' statement.
	 *
	 * @param condition the replacement for the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SIfStmt.CONDITION, condition);
	}

	/**
	 * Mutates the condition of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SIfStmt.CONDITION, mutation);
	}

	/**
	 * Returns the then statement of this 'if' statement.
	 *
	 * @return the then statement of this 'if' statement.
	 */
	public Stmt thenStmt() {
		return location.safeTraversal(SIfStmt.THEN_STMT);
	}

	/**
	 * Replaces the then statement of this 'if' statement.
	 *
	 * @param thenStmt the replacement for the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.safeTraversalReplace(SIfStmt.THEN_STMT, thenStmt);
	}

	/**
	 * Mutates the then statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the then statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withThenStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SIfStmt.THEN_STMT, mutation);
	}

	/**
	 * Returns the else statement of this 'if' statement.
	 *
	 * @return the else statement of this 'if' statement.
	 */
	public NodeOption<Stmt> elseStmt() {
		return location.safeTraversal(SIfStmt.ELSE_STMT);
	}

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withElseStmt(NodeOption<Stmt> elseStmt) {
		return location.safeTraversalReplace(SIfStmt.ELSE_STMT, elseStmt);
	}

	/**
	 * Mutates the else statement of this 'if' statement.
	 *
	 * @param mutation the mutation to apply to the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation) {
		return location.safeTraversalMutate(SIfStmt.ELSE_STMT, mutation);
	}

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement.
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withElseStmt(Stmt elseStmt) {
		return location.safeTraversalReplace(SIfStmt.ELSE_STMT, Trees.some(elseStmt));
	}

	/**
	 * Replaces the else statement of this 'if' statement.
	 *
	 * @return the resulting mutated 'if' statement.
	 */
	public IfStmt withNoElseStmt() {
		return location.safeTraversalReplace(SIfStmt.ELSE_STMT, Trees.<Stmt>none());
	}
}
