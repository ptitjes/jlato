package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SThrowStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.ThrowStmt;
import org.jlato.util.Mutation;

public class TDThrowStmt extends TreeBase<SThrowStmt, Stmt, ThrowStmt> implements ThrowStmt {

	public Kind kind() {
		return Kind.ThrowStmt;
	}

	public TDThrowStmt(SLocation<SThrowStmt> location) {
		super(location);
	}

	public TDThrowStmt(Expr expr) {
		super(new SLocation<SThrowStmt>(SThrowStmt.make(TreeBase.<SExpr>treeOf(expr))));
	}

	public Expr expr() {
		return location.safeTraversal(SThrowStmt.EXPR);
	}

	public ThrowStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SThrowStmt.EXPR, expr);
	}

	public ThrowStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SThrowStmt.EXPR, mutation);
	}
}
