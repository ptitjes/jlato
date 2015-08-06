package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SParenthesizedExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ParenthesizedExpr;
import org.jlato.util.Mutation;

/**
 * A parenthesized expression.
 */
public class TDParenthesizedExpr extends TDTree<SParenthesizedExpr, Expr, ParenthesizedExpr> implements ParenthesizedExpr {

	/**
	 * Returns the kind of this parenthesized expression.
	 *
	 * @return the kind of this parenthesized expression.
	 */
	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	/**
	 * Creates a parenthesized expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDParenthesizedExpr(TDLocation<SParenthesizedExpr> location) {
		super(location);
	}

	/**
	 * Creates a parenthesized expression with the specified child trees.
	 *
	 * @param inner the inner child tree.
	 */
	public TDParenthesizedExpr(Expr inner) {
		super(new TDLocation<SParenthesizedExpr>(SParenthesizedExpr.make(TDTree.<SExpr>treeOf(inner))));
	}

	/**
	 * Returns the inner of this parenthesized expression.
	 *
	 * @return the inner of this parenthesized expression.
	 */
	public Expr inner() {
		return location.safeTraversal(SParenthesizedExpr.INNER);
	}

	/**
	 * Replaces the inner of this parenthesized expression.
	 *
	 * @param inner the replacement for the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	public ParenthesizedExpr withInner(Expr inner) {
		return location.safeTraversalReplace(SParenthesizedExpr.INNER, inner);
	}

	/**
	 * Mutates the inner of this parenthesized expression.
	 *
	 * @param mutation the mutation to apply to the inner of this parenthesized expression.
	 * @return the resulting mutated parenthesized expression.
	 */
	public ParenthesizedExpr withInner(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SParenthesizedExpr.INNER, mutation);
	}
}
