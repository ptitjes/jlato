package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class DoStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public DoStmt instantiate(SLocation location) {
			return new DoStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private DoStmt(SLocation location) {
		super(location);
	}

	public DoStmt(Stmt body, Expr condition) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(body, condition)))));
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public DoStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public DoStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	private static final int BODY = 0;
	private static final int CONDITION = 1;
}
