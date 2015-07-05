package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

public class BreakStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BreakStmt instantiate(SLocation location) {
			return new BreakStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private BreakStmt(SLocation location) {
		super(location);
	}

	public BreakStmt(Name id) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(id)))));
	}

	public Name id() {
		return location.nodeChild(ID);
	}

	public BreakStmt withId(Name id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int ID = 0;
}
