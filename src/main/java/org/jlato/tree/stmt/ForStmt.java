package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.*;

public class ForStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ForStmt instantiate(SLocation location) {
			return new ForStmt(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ForStmt(SLocation location) {
		super(location);
	}

	public ForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(init, compare, update, body)))));
	}

	public NodeList<Expr> init() {
		return location.nodeChild(INIT);
	}

	public ForStmt withInit(NodeList<Expr> init) {
		return location.nodeWithChild(INIT, init);
	}

	public Expr compare() {
		return location.nodeChild(COMPARE);
	}

	public ForStmt withCompare(Expr compare) {
		return location.nodeWithChild(COMPARE, compare);
	}

	public NodeList<Expr> update() {
		return location.nodeChild(UPDATE);
	}

	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.nodeWithChild(UPDATE, update);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public ForStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int INIT = 0;
	private static final int COMPARE = 1;
	private static final int UPDATE = 2;
	private static final int BODY = 3;
}
