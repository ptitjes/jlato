package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class ExpressionStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ExpressionStmt instantiate(SLocation location) {
			return new ExpressionStmt(location);
		}
	};

	private ExpressionStmt(SLocation location) {
		super(location);
	}

	public ExpressionStmt(Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(expr)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public ExpressionStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int EXPR = 0;
}
