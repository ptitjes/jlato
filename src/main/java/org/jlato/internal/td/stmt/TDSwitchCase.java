package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.stmt.SSwitchCase;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SwitchCase;
import org.jlato.util.Mutation;

public class TDSwitchCase extends TreeBase<SSwitchCase, Node, SwitchCase> implements SwitchCase {

	public Kind kind() {
		return Kind.SwitchCase;
	}

	public TDSwitchCase(SLocation<SSwitchCase> location) {
		super(location);
	}

	public TDSwitchCase(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		super(new SLocation<SSwitchCase>(SSwitchCase.make(TreeBase.<SNodeOptionState>treeOf(label), TreeBase.<SNodeListState>treeOf(stmts))));
	}

	public NodeOption<Expr> label() {
		return location.safeTraversal(SSwitchCase.LABEL);
	}

	public SwitchCase withLabel(NodeOption<Expr> label) {
		return location.safeTraversalReplace(SSwitchCase.LABEL, label);
	}

	public SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SSwitchCase.LABEL, mutation);
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(SSwitchCase.STMTS);
	}

	public SwitchCase withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(SSwitchCase.STMTS, stmts);
	}

	public SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(SSwitchCase.STMTS, mutation);
	}
}
