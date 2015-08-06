package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SExpressionStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ExpressionStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * An expression statement.
 */
public class TDExpressionStmt extends TDTree<SExpressionStmt, Stmt, ExpressionStmt> implements ExpressionStmt {

	/**
	 * Returns the kind of this expression statement.
	 *
	 * @return the kind of this expression statement.
	 */
	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	/**
	 * Creates an expression statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDExpressionStmt(TDLocation<SExpressionStmt> location) {
		super(location);
	}

	/**
	 * Creates an expression statement with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 */
	public TDExpressionStmt(Expr expr) {
		super(new TDLocation<SExpressionStmt>(SExpressionStmt.make(TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the expression of this expression statement.
	 *
	 * @return the expression of this expression statement.
	 */
	public Expr expr() {
		return location.safeTraversal(SExpressionStmt.EXPR);
	}

	/**
	 * Replaces the expression of this expression statement.
	 *
	 * @param expr the replacement for the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	public ExpressionStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SExpressionStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this expression statement.
	 *
	 * @param mutation the mutation to apply to the expression of this expression statement.
	 * @return the resulting mutated expression statement.
	 */
	public ExpressionStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SExpressionStmt.EXPR, mutation);
	}
}
