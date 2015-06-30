package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;

public class ContinueStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ContinueStmt instantiate(SLocation location) {
			return new ContinueStmt(location);
		}
	};

	private ContinueStmt(SLocation location) {
		super(location);
	}

	public ContinueStmt(NameExpr id) {
		super(new SLocation(new SNode(kind, runOf(id))));
	}

	public NameExpr id() {
		return location.nodeChild(ID);
	}

	public ContinueStmt withId(NameExpr id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int ID = 0;
}
