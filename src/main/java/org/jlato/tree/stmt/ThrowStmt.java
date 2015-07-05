package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class ThrowStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ThrowStmt instantiate(SLocation location) {
			return new ThrowStmt(location);
		}
	};

	private ThrowStmt(SLocation location) {
		super(location);
	}

	public ThrowStmt(Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(expr)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public ThrowStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int EXPR = 0;
}
