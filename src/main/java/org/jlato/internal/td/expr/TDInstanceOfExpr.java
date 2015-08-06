package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SInstanceOfExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.InstanceOfExpr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An 'instanceof' expression.
 */
public class TDInstanceOfExpr extends TDTree<SInstanceOfExpr, Expr, InstanceOfExpr> implements InstanceOfExpr {

	/**
	 * Returns the kind of this 'instanceof' expression.
	 *
	 * @return the kind of this 'instanceof' expression.
	 */
	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	/**
	 * Creates an 'instanceof' expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDInstanceOfExpr(TDLocation<SInstanceOfExpr> location) {
		super(location);
	}

	/**
	 * Creates an 'instanceof' expression with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 * @param type the type child tree.
	 */
	public TDInstanceOfExpr(Expr expr, Type type) {
		super(new TDLocation<SInstanceOfExpr>(SInstanceOfExpr.make(TDTree.<SExpr>treeOf(expr), TDTree.<SType>treeOf(type))));
	}

	/**
	 * Returns the expression of this 'instanceof' expression.
	 *
	 * @return the expression of this 'instanceof' expression.
	 */
	public Expr expr() {
		return location.safeTraversal(SInstanceOfExpr.EXPR);
	}

	/**
	 * Replaces the expression of this 'instanceof' expression.
	 *
	 * @param expr the replacement for the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SInstanceOfExpr.EXPR, expr);
	}

	/**
	 * Mutates the expression of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the expression of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.EXPR, mutation);
	}

	/**
	 * Returns the type of this 'instanceof' expression.
	 *
	 * @return the type of this 'instanceof' expression.
	 */
	public Type type() {
		return location.safeTraversal(SInstanceOfExpr.TYPE);
	}

	/**
	 * Replaces the type of this 'instanceof' expression.
	 *
	 * @param type the replacement for the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withType(Type type) {
		return location.safeTraversalReplace(SInstanceOfExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this 'instanceof' expression.
	 *
	 * @param mutation the mutation to apply to the type of this 'instanceof' expression.
	 * @return the resulting mutated 'instanceof' expression.
	 */
	public InstanceOfExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SInstanceOfExpr.TYPE, mutation);
	}
}
