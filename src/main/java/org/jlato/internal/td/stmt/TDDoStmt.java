package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SDoStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.DoStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A 'do-while' statement.
 */
public class TDDoStmt extends TDTree<SDoStmt, Stmt, DoStmt> implements DoStmt {

	/**
	 * Returns the kind of this 'do-while' statement.
	 *
	 * @return the kind of this 'do-while' statement.
	 */
	public Kind kind() {
		return Kind.DoStmt;
	}

	/**
	 * Creates a 'do-while' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDDoStmt(TDLocation<SDoStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'do-while' statement with the specified child trees.
	 *
	 * @param body      the body child tree.
	 * @param condition the condition child tree.
	 */
	public TDDoStmt(Stmt body, Expr condition) {
		super(new TDLocation<SDoStmt>(SDoStmt.make(TDTree.<SStmt>treeOf(body), TDTree.<SExpr>treeOf(condition))));
	}

	/**
	 * Returns the body of this 'do-while' statement.
	 *
	 * @return the body of this 'do-while' statement.
	 */
	public Stmt body() {
		return location.safeTraversal(SDoStmt.BODY);
	}

	/**
	 * Replaces the body of this 'do-while' statement.
	 *
	 * @param body the replacement for the body of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	public DoStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SDoStmt.BODY, body);
	}

	/**
	 * Mutates the body of this 'do-while' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	public DoStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SDoStmt.BODY, mutation);
	}

	/**
	 * Returns the condition of this 'do-while' statement.
	 *
	 * @return the condition of this 'do-while' statement.
	 */
	public Expr condition() {
		return location.safeTraversal(SDoStmt.CONDITION);
	}

	/**
	 * Replaces the condition of this 'do-while' statement.
	 *
	 * @param condition the replacement for the condition of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	public DoStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SDoStmt.CONDITION, condition);
	}

	/**
	 * Mutates the condition of this 'do-while' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'do-while' statement.
	 * @return the resulting mutated 'do-while' statement.
	 */
	public DoStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SDoStmt.CONDITION, mutation);
	}
}
