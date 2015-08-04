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

public class TDDoStmt extends TDTree<SDoStmt, Stmt, DoStmt> implements DoStmt {

	public Kind kind() {
		return Kind.DoStmt;
	}

	public TDDoStmt(TDLocation<SDoStmt> location) {
		super(location);
	}

	public TDDoStmt(Stmt body, Expr condition) {
		super(new TDLocation<SDoStmt>(SDoStmt.make(TDTree.<SStmt>treeOf(body), TDTree.<SExpr>treeOf(condition))));
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
