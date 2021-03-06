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
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An explicit constructor invocation statement.
 */
public interface ExplicitConstructorInvocationStmt extends Stmt, TreeCombinators<ExplicitConstructorInvocationStmt> {

	/**
	 * Returns the type args of this explicit constructor invocation statement.
	 *
	 * @return the type args of this explicit constructor invocation statement.
	 */
	NodeList<Type> typeArgs();

	/**
	 * Replaces the type args of this explicit constructor invocation statement.
	 *
	 * @param typeArgs the replacement for the type args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs);

	/**
	 * Mutates the type args of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the type args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation);

	/**
	 * Tests whether this explicit constructor invocation statement is 'this'.
	 *
	 * @return <code>true</code> if this explicit constructor invocation statement is 'this', <code>false</code> otherwise.
	 */
	boolean isThis();

	/**
	 * Sets whether this explicit constructor invocation statement is 'this'.
	 *
	 * @param isThis <code>true</code> if this explicit constructor invocation statement is 'this', <code>false</code> otherwise.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt setThis(boolean isThis);

	/**
	 * Mutates whether this explicit constructor invocation statement is 'this'.
	 *
	 * @param mutation the mutation to apply to whether this explicit constructor invocation statement is 'this'.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt setThis(Mutation<Boolean> mutation);

	/**
	 * Returns the expression of this explicit constructor invocation statement.
	 *
	 * @return the expression of this explicit constructor invocation statement.
	 */
	NodeOption<Expr> expr();

	/**
	 * Replaces the expression of this explicit constructor invocation statement.
	 *
	 * @param expr the replacement for the expression of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withExpr(NodeOption<Expr> expr);

	/**
	 * Mutates the expression of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the expression of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the expression of this explicit constructor invocation statement.
	 *
	 * @param expr the replacement for the expression of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withExpr(Expr expr);

	/**
	 * Replaces the expression of this explicit constructor invocation statement.
	 *
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withNoExpr();

	/**
	 * Returns the args of this explicit constructor invocation statement.
	 *
	 * @return the args of this explicit constructor invocation statement.
	 */
	NodeList<Expr> args();

	/**
	 * Replaces the args of this explicit constructor invocation statement.
	 *
	 * @param args the replacement for the args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args);

	/**
	 * Mutates the args of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation);
}
