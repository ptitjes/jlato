package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.stmt.SReturnStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ReturnStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDReturnStmt extends TreeBase<SReturnStmt, Stmt, ReturnStmt> implements ReturnStmt {

	public Kind kind() {
		return Kind.ReturnStmt;
	}

	public TDReturnStmt(SLocation<SReturnStmt> location) {
		super(location);
	}

	public TDReturnStmt(NodeOption<Expr> expr) {
		super(new SLocation<SReturnStmt>(SReturnStmt.make(TreeBase.<SNodeOptionState>treeOf(expr))));
	}

	public NodeOption<Expr> expr() {
		return location.safeTraversal(SReturnStmt.EXPR);
	}

	public ReturnStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(SReturnStmt.EXPR, expr);
	}

	public ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SReturnStmt.EXPR, mutation);
	}
}
