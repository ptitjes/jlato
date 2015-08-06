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
