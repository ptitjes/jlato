package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SSuperExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SuperExpr;
import org.jlato.util.Mutation;

/**
 * A 'super' expression.
 */
public class TDSuperExpr extends TDTree<SSuperExpr, Expr, SuperExpr> implements SuperExpr {

	/**
	 * Returns the kind of this 'super' expression.
	 *
	 * @return the kind of this 'super' expression.
	 */
	public Kind kind() {
		return Kind.SuperExpr;
	}

	/**
	 * Creates a 'super' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDSuperExpr(TDLocation<SSuperExpr> location) {
		super(location);
	}

	/**
	 * Creates a 'super' expression with the specified child trees.
	 *
	 * @param classExpr the 'class' expression child tree.
	 */
	public TDSuperExpr(NodeOption<Expr> classExpr) {
		super(new TDLocation<SSuperExpr>(SSuperExpr.make(TDTree.<SNodeOption>treeOf(classExpr))));
	}

	/**
	 * Returns the 'class' expression of this 'super' expression.
	 *
	 * @return the 'class' expression of this 'super' expression.
	 */
	public NodeOption<Expr> classExpr() {
		return location.safeTraversal(SSuperExpr.CLASS_EXPR);
	}

	/**
	 * Replaces the 'class' expression of this 'super' expression.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withClassExpr(NodeOption<Expr> classExpr) {
		return location.safeTraversalReplace(SSuperExpr.CLASS_EXPR, classExpr);
	}

	/**
	 * Mutates the 'class' expression of this 'super' expression.
	 *
	 * @param mutation the mutation to apply to the 'class' expression of this 'super' expression.
	 * @return the resulting mutated 'super' expression.
	 */
	public SuperExpr withClassExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SSuperExpr.CLASS_EXPR, mutation);
	}
}
