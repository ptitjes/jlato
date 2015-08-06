package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SThisExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ThisExpr;
import org.jlato.util.Mutation;

/**
 * A 'this' expression.
 */
public class TDThisExpr extends TDTree<SThisExpr, Expr, ThisExpr> implements ThisExpr {

	/**
	 * Returns the kind of this 'this' expression.
	 *
	 * @return the kind of this 'this' expression.
	 */
	public Kind kind() {
		return Kind.ThisExpr;
	}

	/**
	 * Creates a 'this' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDThisExpr(TDLocation<SThisExpr> location) {
		super(location);
	}

	/**
	 * Creates a 'this' expression with the specified child trees.
	 *
	 * @param classExpr the 'class' expression child tree.
	 */
	public TDThisExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SThisExpr>(SThisExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	/**
	 * Returns the 'class' expression of this 'this' expression.
	 *
	 * @return the 'class' expression of this 'this' expression.
	 */
	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SThisExpr.CLASS_EXPR);
	}

	/**
	 * Replaces the 'class' expression of this 'this' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SThisExpr.CLASS_EXPR, classExpr);
	}

	/**
	 * Mutates the 'class' expression of this 'this' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'this' expression.
	 * @return the resulting mutated 'this' expression.
	 */
	public ThisExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SThisExpr.CLASS_EXPR, mutation);
	}
}
