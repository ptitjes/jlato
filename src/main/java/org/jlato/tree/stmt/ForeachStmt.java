package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.VariableDeclarationExpr;

public class ForeachStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ForeachStmt instantiate(SLocation location) {
			return new ForeachStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ForeachStmt(SLocation location) {
		super(location);
	}

	public ForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(var, iterable, body)))));
	}

	public VariableDeclarationExpr var() {
		return location.nodeChild(VAR);
	}

	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.nodeWithChild(VAR, var);
	}

	public Expr iterable() {
		return location.nodeChild(ITERABLE);
	}

	public ForeachStmt withIterable(Expr iterable) {
		return location.nodeWithChild(ITERABLE, iterable);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public ForeachStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int VAR = 0;
	private static final int ITERABLE = 1;
	private static final int BODY = 2;
}
