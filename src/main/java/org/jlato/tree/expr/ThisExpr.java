package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A 'this' expression.
 */
public interface ThisExpr extends Expr, TreeCombinators<ThisExpr> {

	/**
	 * Returns the 'class' expression of this 'this' expression.
	 *
	 * @return the 'class' expression of this 'this' expression.
	 */
	NodeOption<Expr> classExpr();

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(NodeOption<Expr> classExpr);

	/**
	 * Mutates the 'class' expression of this 'this' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withClassExpr(Expr classExpr);

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @return the resulting mutated 'this' expression.
	 */
	ThisExpr withNoClassExpr();
}
