package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.bu.stmt.SForeachStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.ForeachStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A "enhanced" 'for' statement.
 */
public class TDForeachStmt extends TDTree<SForeachStmt, Stmt, ForeachStmt> implements ForeachStmt {

	/**
	 * Returns the kind of this "enhanced" 'for' statement.
	 *
	 * @return the kind of this "enhanced" 'for' statement.
	 */
	public Kind kind() {
		return Kind.ForeachStmt;
	}

	/**
	 * Creates a "enhanced" 'for' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDForeachStmt(TDLocation<SForeachStmt> location) {
		super(location);
	}

	/**
	 * Creates a "enhanced" 'for' statement with the specified child trees.
	 *
	 * @param var      the var child tree.
	 * @param iterable the iterable child tree.
	 * @param body     the body child tree.
	 */
	public TDForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new TDLocation<SForeachStmt>(SForeachStmt.make(TDTree.<SVariableDeclarationExpr>treeOf(var), TDTree.<SExpr>treeOf(iterable), TDTree.<SStmt>treeOf(body))));
	}

	/**
	 * Returns the var of this "enhanced" 'for' statement.
	 *
	 * @return the var of this "enhanced" 'for' statement.
	 */
	public VariableDeclarationExpr var() {
		return location.safeTraversal(SForeachStmt.VAR);
	}

	/**
	 * Replaces the var of this "enhanced" 'for' statement.
	 *
	 * @param var the replacement for the var of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.safeTraversalReplace(SForeachStmt.VAR, var);
	}

	/**
	 * Mutates the var of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the var of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation) {
		return location.safeTraversalMutate(SForeachStmt.VAR, mutation);
	}

	/**
	 * Returns the iterable of this "enhanced" 'for' statement.
	 *
	 * @return the iterable of this "enhanced" 'for' statement.
	 */
	public Expr iterable() {
		return location.safeTraversal(SForeachStmt.ITERABLE);
	}

	/**
	 * Replaces the iterable of this "enhanced" 'for' statement.
	 *
	 * @param iterable the replacement for the iterable of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withIterable(Expr iterable) {
		return location.safeTraversalReplace(SForeachStmt.ITERABLE, iterable);
	}

	/**
	 * Mutates the iterable of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the iterable of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withIterable(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SForeachStmt.ITERABLE, mutation);
	}

	/**
	 * Returns the body of this "enhanced" 'for' statement.
	 *
	 * @return the body of this "enhanced" 'for' statement.
	 */
	public Stmt body() {
		return location.safeTraversal(SForeachStmt.BODY);
	}

	/**
	 * Replaces the body of this "enhanced" 'for' statement.
	 *
	 * @param body the replacement for the body of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SForeachStmt.BODY, body);
	}

	/**
	 * Mutates the body of this "enhanced" 'for' statement.
	 *
	 * @param mutation the mutation to apply to the body of this "enhanced" 'for' statement.
	 * @return the resulting mutated "enhanced" 'for' statement.
	 */
	public ForeachStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SForeachStmt.BODY, mutation);
	}
}
