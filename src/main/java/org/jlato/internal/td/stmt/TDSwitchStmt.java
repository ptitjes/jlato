package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SSwitchStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.tree.stmt.SwitchStmt;
import org.jlato.util.Mutation;

public class TDSwitchStmt extends TreeBase<SSwitchStmt, Stmt, SwitchStmt> implements SwitchStmt {

	public Kind kind() {
		return Kind.SwitchStmt;
	}

	public TDSwitchStmt(SLocation<SSwitchStmt> location) {
		super(location);
	}

	public TDSwitchStmt(Expr selector, NodeList<SwitchCase> cases) {
		super(new SLocation<SSwitchStmt>(SSwitchStmt.make(TreeBase.<SExpr>treeOf(selector), TreeBase.<SNodeListState>treeOf(cases))));
	}

	public Expr selector() {
		return location.safeTraversal(SSwitchStmt.SELECTOR);
	}

	public SwitchStmt withSelector(Expr selector) {
		return location.safeTraversalReplace(SSwitchStmt.SELECTOR, selector);
	}

	public SwitchStmt withSelector(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSwitchStmt.SELECTOR, mutation);
	}

	public NodeList<SwitchCase> cases() {
		return location.safeTraversal(SSwitchStmt.CASES);
	}

	public SwitchStmt withCases(NodeList<SwitchCase> cases) {
		return location.safeTraversalReplace(SSwitchStmt.CASES, cases);
	}

	public SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation) {
		return location.safeTraversalMutate(SSwitchStmt.CASES, mutation);
	}
}
