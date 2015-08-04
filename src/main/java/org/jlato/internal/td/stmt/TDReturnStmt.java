package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.stmt.SReturnStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ReturnStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDReturnStmt extends TDTree<SReturnStmt, Stmt, ReturnStmt> implements ReturnStmt {

	public Kind kind() {
		return Kind.ReturnStmt;
	}

	public TDReturnStmt(TDLocation<SReturnStmt> location) {
		super(location);
	}

	public TDReturnStmt(NodeOption<Expr> expr) {
		super(new TDLocation<SReturnStmt>(SReturnStmt.make(TDTree.<SNodeOption>treeOf(expr))));
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
