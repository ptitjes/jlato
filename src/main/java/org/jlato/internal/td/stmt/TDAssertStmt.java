package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SAssertStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.AssertStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDAssertStmt extends TDTree<SAssertStmt, Stmt, AssertStmt> implements AssertStmt {

	public Kind kind() {
		return Kind.AssertStmt;
	}

	public TDAssertStmt(SLocation<SAssertStmt> location) {
		super(location);
	}

	public TDAssertStmt(Expr check, NodeOption<Expr> msg) {
		super(new SLocation<SAssertStmt>(SAssertStmt.make(TDTree.<SExpr>treeOf(check), TDTree.<SNodeOptionState>treeOf(msg))));
	}

	public Expr check() {
		return location.safeTraversal(SAssertStmt.CHECK);
	}

	public AssertStmt withCheck(Expr check) {
		return location.safeTraversalReplace(SAssertStmt.CHECK, check);
	}

	public AssertStmt withCheck(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SAssertStmt.CHECK, mutation);
	}

	public NodeOption<Expr> msg() {
		return location.safeTraversal(SAssertStmt.MSG);
	}

	public AssertStmt withMsg(NodeOption<Expr> msg) {
		return location.safeTraversalReplace(SAssertStmt.MSG, msg);
	}

	public AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SAssertStmt.MSG, mutation);
	}
}
