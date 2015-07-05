package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class ArrayAccessExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayAccessExpr instantiate(SLocation location) {
			return new ArrayAccessExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ArrayAccessExpr(SLocation location) {
		super(location);
	}

	public ArrayAccessExpr(Expr name, Expr index) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name, index)))));
	}

	public Expr name() {
		return location.nodeChild(NAME);
	}

	public ArrayAccessExpr withName(Expr name) {
		return location.nodeWithChild(NAME, name);
	}

	public Expr index() {
		return location.nodeChild(INDEX);
	}

	public ArrayAccessExpr withIndex(Expr index) {
		return location.nodeWithChild(INDEX, index);
	}

	private static final int NAME = 0;
	private static final int INDEX = 1;
}
