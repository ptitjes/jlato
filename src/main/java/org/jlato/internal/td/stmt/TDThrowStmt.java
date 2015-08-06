package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SThrowStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.ThrowStmt;
import org.jlato.util.Mutation;

/**
 * A 'throw' statement.
 */
public class TDThrowStmt extends TDTree<SThrowStmt, Stmt, ThrowStmt> implements ThrowStmt {

	/**
	 * Returns the kind of this 'throw' statement.
	 *
	 * @return the kind of this 'throw' statement.
	 */
	public Kind kind() {
		return Kind.ThrowStmt;
	}

	/**
	 * Creates a 'throw' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDThrowStmt(TDLocation<SThrowStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'throw' statement with the specified child trees.
	 *
	 * @param expr the expression child tree.
	 */
	public TDThrowStmt(Expr expr) {
		super(new TDLocation<SThrowStmt>(SThrowStmt.make(TDTree.<SExpr>treeOf(expr))));
	}

	/**
	 * Returns the expression of this 'throw' statement.
	 *
	 * @return the expression of this 'throw' statement.
	 */
	public Expr expr() {
		return location.safeTraversal(SThrowStmt.EXPR);
	}

	/**
	 * Replaces the expression of this 'throw' statement.
	 *
	 * @param expr the replacement for the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	public ThrowStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SThrowStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this 'throw' statement.
	 *
	 * @param mutation the mutation to apply to the expression of this 'throw' statement.
	 * @return the resulting mutated 'throw' statement.
	 */
	public ThrowStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SThrowStmt.EXPR, mutation);
	}
}
