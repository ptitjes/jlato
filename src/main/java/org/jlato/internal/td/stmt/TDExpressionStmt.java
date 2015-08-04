package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SExpressionStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ExpressionStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDExpressionStmt extends TreeBase<SExpressionStmt, Stmt, ExpressionStmt> implements ExpressionStmt {

	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	public TDExpressionStmt(SLocation<SExpressionStmt> location) {
		super(location);
	}

	public TDExpressionStmt(Expr expr) {
		super(new SLocation<SExpressionStmt>(SExpressionStmt.make(TreeBase.<SExpr>treeOf(expr))));
	}

	public Expr expr() {
		return location.safeTraversal(SExpressionStmt.EXPR);
	}

	public ExpressionStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SExpressionStmt.EXPR, expr);
	}

	public ExpressionStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SExpressionStmt.EXPR, mutation);
	}
}
