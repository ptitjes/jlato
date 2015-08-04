package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SDoStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.DoStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDDoStmt extends TreeBase<SDoStmt, Stmt, DoStmt> implements DoStmt {

	public Kind kind() {
		return Kind.DoStmt;
	}

	public TDDoStmt(SLocation<SDoStmt> location) {
		super(location);
	}

	public TDDoStmt(Stmt body, Expr condition) {
		super(new SLocation<SDoStmt>(SDoStmt.make(TreeBase.<SStmt>treeOf(body), TreeBase.<SExpr>treeOf(condition))));
	}

	public Stmt body() {
		return location.safeTraversal(SDoStmt.BODY);
	}

	public DoStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SDoStmt.BODY, body);
	}

	public DoStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SDoStmt.BODY, mutation);
	}

	public Expr condition() {
		return location.safeTraversal(SDoStmt.CONDITION);
	}

	public DoStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SDoStmt.CONDITION, condition);
	}

	public DoStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SDoStmt.CONDITION, mutation);
	}
}
