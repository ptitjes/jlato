package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SArrayAccessExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.ArrayAccessExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An array access expression.
 */
public class TDArrayAccessExpr extends TDTree<SArrayAccessExpr, Expr, ArrayAccessExpr> implements ArrayAccessExpr {

	/**
	 * Returns the kind of this array access expression.
	 *
	 * @return the kind of this array access expression.
	 */
	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	/**
	 * Creates an array access expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDArrayAccessExpr(TDLocation<SArrayAccessExpr> location) {
		super(location);
	}

	/**
	 * Creates an array access expression with the specified child trees.
	 *
	 * @param name  the name child tree.
	 * @param index the index child tree.
	 */
	public TDArrayAccessExpr(Expr name, Expr index) {
		super(new TDLocation<SArrayAccessExpr>(SArrayAccessExpr.make(TDTree.<SExpr>treeOf(name), TDTree.<SExpr>treeOf(index))));
	}

	/**
	 * Returns the name of this array access expression.
	 *
	 * @return the name of this array access expression.
	 */
	public Expr name() {
		return location.safeTraversal(SArrayAccessExpr.NAME);
	}

	/**
	 * Replaces the name of this array access expression.
	 *
	 * @param name the replacement for the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	public ArrayAccessExpr withName(Expr name) {
		return location.safeTraversalReplace(SArrayAccessExpr.NAME, name);
	}

	/**
	 * Mutates the name of this array access expression.
	 *
	 * @param mutation the mutation to apply to the name of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	public ArrayAccessExpr withName(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayAccessExpr.NAME, mutation);
	}

	/**
	 * Returns the index of this array access expression.
	 *
	 * @return the index of this array access expression.
	 */
	public Expr index() {
		return location.safeTraversal(SArrayAccessExpr.INDEX);
	}

	/**
	 * Replaces the index of this array access expression.
	 *
	 * @param index the replacement for the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	public ArrayAccessExpr withIndex(Expr index) {
		return location.safeTraversalReplace(SArrayAccessExpr.INDEX, index);
	}

	/**
	 * Mutates the index of this array access expression.
	 *
	 * @param mutation the mutation to apply to the index of this array access expression.
	 * @return the resulting mutated array access expression.
	 */
	public ArrayAccessExpr withIndex(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayAccessExpr.INDEX, mutation);
	}
}
