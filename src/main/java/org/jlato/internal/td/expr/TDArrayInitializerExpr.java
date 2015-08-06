package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SArrayInitializerExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An array initializer expression.
 */
public class TDArrayInitializerExpr extends TDTree<SArrayInitializerExpr, Expr, ArrayInitializerExpr> implements ArrayInitializerExpr {

	/**
	 * Returns the kind of this array initializer expression.
	 *
	 * @return the kind of this array initializer expression.
	 */
	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	/**
	 * Creates an array initializer expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayInitializerExpr(TDLocation<SArrayInitializerExpr> location) {
		super(location);
	}

	/**
	 * Creates an array initializer expression with the specified child trees.
	 *
	 * @param values        the values child tree.
	 * @param trailingComma the has a trailing comma child tree.
	 */
	public TDArrayInitializerExpr(NodeList<Expr> values, boolean trailingComma) {
		super(new TDLocation<SArrayInitializerExpr>(SArrayInitializerExpr.make(TDTree.<SNodeList>treeOf(values), trailingComma)));
	}

	/**
	 * Returns the values of this array initializer expression.
	 *
	 * @return the values of this array initializer expression.
	 */
	public NodeList<Expr> values() {
		return location.safeTraversal(SArrayInitializerExpr.VALUES);
	}

	/**
	 * Replaces the values of this array initializer expression.
	 *
	 * @param values the replacement for the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.safeTraversalReplace(SArrayInitializerExpr.VALUES, values);
	}

	/**
	 * Mutates the values of this array initializer expression.
	 *
	 * @param mutation the mutation to apply to the values of this array initializer expression.
	 * @return the resulting mutated array initializer expression.
	 */
	public ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SArrayInitializerExpr.VALUES, mutation);
	}

	/**
	 * Tests whether this array initializer expression has a trailing comma.
	 *
	 * @return <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 */
	public boolean trailingComma() {
		return location.safeProperty(SArrayInitializerExpr.TRAILING_COMMA);
	}

	/**
	 * Sets whether this array initializer expression has a trailing comma.
	 *
	 * @param trailingComma <code>true</code> if this array initializer expression has a trailing comma, <code>false</code> otherwise.
	 * @return the resulting mutated array initializer expression.
	 */
	public ArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(SArrayInitializerExpr.TRAILING_COMMA, trailingComma);
	}

	/**
	 * Mutates whether this array initializer expression has a trailing comma.
	 *
	 * @param mutation the mutation to apply to whether this array initializer expression has a trailing comma.
	 * @return the resulting mutated array initializer expression.
	 */
	public ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SArrayInitializerExpr.TRAILING_COMMA, mutation);
	}
}
