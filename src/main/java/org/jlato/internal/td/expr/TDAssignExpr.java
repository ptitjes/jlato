package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SAssignExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AssignExpr;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * An assignment expression.
 */
public class TDAssignExpr extends TDTree<SAssignExpr, Expr, AssignExpr> implements AssignExpr {

	/**
	 * Returns the kind of this assignment expression.
	 *
	 * @return the kind of this assignment expression.
	 */
	public Kind kind() {
		return Kind.AssignExpr;
	}

	/**
	 * Creates an assignment expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDAssignExpr(TDLocation<SAssignExpr> location) {
		super(location);
	}

	/**
	 * Creates an assignment expression with the specified child trees.
	 *
	 * @param target the target child tree.
	 * @param op     the op child tree.
	 * @param value  the value child tree.
	 */
	public TDAssignExpr(Expr target, AssignOp op, Expr value) {
		super(new TDLocation<SAssignExpr>(SAssignExpr.make(TDTree.<SExpr>treeOf(target), op, TDTree.<SExpr>treeOf(value))));
	}

	/**
	 * Returns the target of this assignment expression.
	 *
	 * @return the target of this assignment expression.
	 */
	public Expr target() {
		return location.safeTraversal(SAssignExpr.TARGET);
	}

	/**
	 * Replaces the target of this assignment expression.
	 *
	 * @param target the replacement for the target of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withTarget(Expr target) {
		return location.safeTraversalReplace(SAssignExpr.TARGET, target);
	}

	/**
	 * Mutates the target of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the target of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withTarget(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssignExpr.TARGET, mutation);
	}

	/**
	 * Returns the op of this assignment expression.
	 *
	 * @return the op of this assignment expression.
	 */
	public AssignOp op() {
		return location.safeProperty(SAssignExpr.OP);
	}

	/**
	 * Replaces the op of this assignment expression.
	 *
	 * @param op the replacement for the op of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withOp(AssignOp op) {
		return location.safePropertyReplace(SAssignExpr.OP, op);
	}

	/**
	 * Mutates the op of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the op of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withOp(Mutation<AssignOp> mutation) {
		return location.safePropertyMutate(SAssignExpr.OP, mutation);
	}

	/**
	 * Returns the value of this assignment expression.
	 *
	 * @return the value of this assignment expression.
	 */
	public Expr value() {
		return location.safeTraversal(SAssignExpr.VALUE);
	}

	/**
	 * Replaces the value of this assignment expression.
	 *
	 * @param value the replacement for the value of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withValue(Expr value) {
		return location.safeTraversalReplace(SAssignExpr.VALUE, value);
	}

	/**
	 * Mutates the value of this assignment expression.
	 *
	 * @param mutation the mutation to apply to the value of this assignment expression.
	 * @return the resulting mutated assignment expression.
	 */
	public AssignExpr withValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssignExpr.VALUE, mutation);
	}
}
