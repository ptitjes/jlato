package org.jlato.tree.expr;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * A lambda expression.
 */
public interface LambdaExpr extends Expr, TreeCombinators<LambdaExpr> {

	/**
	 * Returns the parameters of this lambda expression.
	 *
	 * @return the parameters of this lambda expression.
	 */
	NodeList<FormalParameter> params();

	/**
	 * Replaces the parameters of this lambda expression.
	 *
	 * @param params the replacement for the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withParams(NodeList<FormalParameter> params);

	/**
	 * Mutates the parameters of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation);

	/**
	 * Tests whether this lambda expression has its arguments parenthesized.
	 *
	 * @return <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 */
	boolean hasParens();

	/**
	 * Sets whether this lambda expression has its arguments parenthesized.
	 *
	 * @param hasParens <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr setParens(boolean hasParens);

	/**
	 * Mutates whether this lambda expression has its arguments parenthesized.
	 *
	 * @param mutation the mutation to apply to whether this lambda expression has its arguments parenthesized.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr setParens(Mutation<Boolean> mutation);

	/**
	 * Returns the body of this lambda expression.
	 *
	 * @return the body of this lambda expression.
	 */
	NodeEither<Expr, BlockStmt> body();

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(NodeEither<Expr, BlockStmt> body);

	/**
	 * Mutates the body of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation);
}
