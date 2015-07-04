package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class IfStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public IfStmt instantiate(SLocation location) {
			return new IfStmt(location);
		}
	};

	private IfStmt(SLocation location) {
		super(location);
	}

	public IfStmt(Expr condition, Stmt thenStmt, Stmt elseStmt) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(condition, thenStmt, elseStmt)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Stmt thenStmt() {
		return location.nodeChild(THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.nodeWithChild(THEN_STMT, thenStmt);
	}

	public Stmt elseStmt() {
		return location.nodeChild(ELSE_STMT);
	}

	public IfStmt withElseStmt(Stmt elseStmt) {
		return location.nodeWithChild(ELSE_STMT, elseStmt);
	}

	private static final int CONDITION = 0;
	private static final int THEN_STMT = 1;
	private static final int ELSE_STMT = 2;
}
