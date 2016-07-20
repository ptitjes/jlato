package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A 'super' expression.
 */
public interface SuperExpr extends Expr, TreeCombinators<SuperExpr> {

	/**
	 * Returns the 'class' expression of this 'super' expression.
	 *
	 * @return the 'class' expression of this 'super' expression.
	 */
	NodeOption<Expr> classExpr();

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(NodeOption<Expr> classExpr);

	/**
	 * Mutates the 'class' expression of this 'super' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withClassExpr(Expr classExpr);

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @return the resulting mutated 'super' expression.
	 */
	SuperExpr withNoClassExpr();
}
