package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A 'return' statement.
 */
public interface ReturnStmt extends Stmt, TreeCombinators<ReturnStmt> {

	/**
	 * Returns the expression of this 'return' statement.
	 *
	 * @return the expression of this 'return' statement.
	 */
	NodeOption<Expr> expr();

	/**
	 * Replaces the expression of this 'return' statement.
	 *
	 * @param expr the replacement for the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withExpr(NodeOption<Expr> expr);

	/**
	 * Mutates the expression of this 'return' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'return' statement.
	 * @return the resulting mutated 'return' statement.
	 */
	ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation);
}
