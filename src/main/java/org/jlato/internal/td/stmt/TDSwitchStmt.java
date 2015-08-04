package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SSwitchStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.tree.stmt.SwitchStmt;
import org.jlato.util.Mutation;

public class TDSwitchStmt extends TDTree<SSwitchStmt, Stmt, SwitchStmt> implements SwitchStmt {

	public Kind kind() {
		return Kind.SwitchStmt;
	}

	public TDSwitchStmt(TDLocation<SSwitchStmt> location) {
		super(location);
	}

	public TDSwitchStmt(Expr selector, NodeList<SwitchCase> cases) {
		super(new TDLocation<SSwitchStmt>(SSwitchStmt.make(TDTree.<SExpr>treeOf(selector), TDTree.<SNodeList>treeOf(cases))));
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
