package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.stmt.SWhileStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.WhileStmt;
import org.jlato.util.Mutation;

/**
 * A 'while' statement.
 */
public class TDWhileStmt extends TDTree<SWhileStmt, Stmt, WhileStmt> implements WhileStmt {

	/**
	 * Returns the kind of this 'while' statement.
	 *
	 * @return the kind of this 'while' statement.
	 */
	public Kind kind() {
		return Kind.WhileStmt;
	}

	/**
	 * Creates a 'while' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDWhileStmt(TDLocation<SWhileStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'while' statement with the specified child trees.
	 *
	 * @param condition the condition child tree.
	 * @param body      the body child tree.
	 */
	public TDWhileStmt(Expr condition, Stmt body) {
		super(new TDLocation<SWhileStmt>(SWhileStmt.make(TDTree.<SExpr>treeOf(condition), TDTree.<SStmt>treeOf(body))));
	}

	/**
	 * Returns the condition of this 'while' statement.
	 *
	 * @return the condition of this 'while' statement.
	 */
	public Expr condition() {
		return location.safeTraversal(SWhileStmt.CONDITION);
	}

	/**
	 * Replaces the condition of this 'while' statement.
	 *
	 * @param condition the replacement for the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	public WhileStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SWhileStmt.CONDITION, condition);
	}

	/**
	 * Mutates the condition of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the condition of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	public WhileStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SWhileStmt.CONDITION, mutation);
	}

	/**
	 * Returns the body of this 'while' statement.
	 *
	 * @return the body of this 'while' statement.
	 */
	public Stmt body() {
		return location.safeTraversal(SWhileStmt.BODY);
	}

	/**
	 * Replaces the body of this 'while' statement.
	 *
	 * @param body the replacement for the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	public WhileStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SWhileStmt.BODY, body);
	}

	/**
	 * Mutates the body of this 'while' statement.
	 *
	 * @param mutation the mutation to apply to the body of this 'while' statement.
	 * @return the resulting mutated 'while' statement.
	 */
	public WhileStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SWhileStmt.BODY, mutation);
	}
}
