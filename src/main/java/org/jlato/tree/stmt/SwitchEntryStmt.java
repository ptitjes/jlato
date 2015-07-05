package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.SLocation;
import org.jlato.tree.*;

public class SwitchEntryStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SwitchEntryStmt instantiate(SLocation location) {
			return new SwitchEntryStmt(location);
		}
	};

	private SwitchEntryStmt(SLocation location) {
		super(location);
	}

	public SwitchEntryStmt(Expr label, NodeList<Stmt> stmts) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(label, stmts)))));
	}

	public Expr label() {
		return location.nodeChild(LABEL);
	}

	public SwitchEntryStmt withLabel(Expr label) {
		return location.nodeWithChild(LABEL, label);
	}

	public NodeList<Stmt> stmts() {
		return location.nodeChild(STMTS);
	}

	public SwitchEntryStmt withStmts(NodeList<Stmt> stmts) {
		return location.nodeWithChild(STMTS, stmts);
	}

	private static final int LABEL = 0;
	private static final int STMTS = 1;
}
