package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.*;

public class SwitchStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SwitchStmt instantiate(SLocation location) {
			return new SwitchStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private SwitchStmt(SLocation location) {
		super(location);
	}

	public SwitchStmt(Expr selector, NodeList<SwitchEntryStmt> entries) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(selector, entries)))));
	}

	public Expr selector() {
		return location.nodeChild(SELECTOR);
	}

	public SwitchStmt withSelector(Expr selector) {
		return location.nodeWithChild(SELECTOR, selector);
	}

	public NodeList<SwitchEntryStmt> entries() {
		return location.nodeChild(ENTRIES);
	}

	public SwitchStmt withEntries(NodeList<SwitchEntryStmt> entries) {
		return location.nodeWithChild(ENTRIES, entries);
	}

	private static final int SELECTOR = 0;
	private static final int ENTRIES = 1;
}
