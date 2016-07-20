package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SLambdaExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.LambdaExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * A lambda expression.
 */
public class TDLambdaExpr extends TDTree<SLambdaExpr, Expr, LambdaExpr> implements LambdaExpr {

	/**
	 * Returns the kind of this lambda expression.
	 *
	 * @return the kind of this lambda expression.
	 */
	public Kind kind() {
		return Kind.LambdaExpr;
	}

	/**
	 * Creates a lambda expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDLambdaExpr(TDLocation<SLambdaExpr> location) {
		super(location);
	}

	/**
	 * Creates a lambda expression with the specified child trees.
	 *
	 * @param params    the parameters child tree.
	 * @param hasParens the has its arguments parenthesized child tree.
	 * @param body      the body child tree.
	 */
	public TDLambdaExpr(NodeList<FormalParameter> params, boolean hasParens, NodeEither<Expr, BlockStmt> body) {
		super(new TDLocation<SLambdaExpr>(SLambdaExpr.make(TDTree.<SNodeList>treeOf(params), hasParens, TDTree.<SNodeEither>treeOf(body))));
	}

	/**
	 * Returns the parameters of this lambda expression.
	 *
	 * @return the parameters of this lambda expression.
	 */
	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SLambdaExpr.PARAMS);
	}

	/**
	 * Replaces the parameters of this lambda expression.
	 *
	 * @param params the replacement for the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SLambdaExpr.PARAMS, params);
	}

	/**
	 * Mutates the parameters of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the parameters of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SLambdaExpr.PARAMS, mutation);
	}

	/**
	 * Tests whether this lambda expression has its arguments parenthesized.
	 *
	 * @return <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 */
	public boolean hasParens() {
		return location.safeProperty(SLambdaExpr.PARENS);
	}

	/**
	 * Sets whether this lambda expression has its arguments parenthesized.
	 *
	 * @param hasParens <code>true</code> if this lambda expression has its arguments parenthesized, <code>false</code> otherwise.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr setParens(boolean hasParens) {
		return location.safePropertyReplace(SLambdaExpr.PARENS, hasParens);
	}

	/**
	 * Mutates whether this lambda expression has its arguments parenthesized.
	 *
	 * @param mutation the mutation to apply to whether this lambda expression has its arguments parenthesized.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr setParens(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SLambdaExpr.PARENS, mutation);
	}

	/**
	 * Returns the body of this lambda expression.
	 *
	 * @return the body of this lambda expression.
	 */
	public NodeEither<Expr, BlockStmt> body() {
		return location.safeTraversal(SLambdaExpr.BODY);
	}

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withBody(NodeEither<Expr, BlockStmt> body) {
		return location.safeTraversalReplace(SLambdaExpr.BODY, body);
	}

	/**
	 * Mutates the body of this lambda expression.
	 *
	 * @param mutation the mutation to apply to the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation) {
		return location.safeTraversalMutate(SLambdaExpr.BODY, mutation);
	}

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withBody(Expr body) {
		return location.safeTraversalReplace(SLambdaExpr.BODY, Trees.<Expr, BlockStmt>left(body));
	}

	/**
	 * Replaces the body of this lambda expression.
	 *
	 * @param body the replacement for the body of this lambda expression.
	 * @return the resulting mutated lambda expression.
	 */
	public LambdaExpr withBody(BlockStmt body) {
		return location.safeTraversalReplace(SLambdaExpr.BODY, Trees.<Expr, BlockStmt>right(body));
	}
}
