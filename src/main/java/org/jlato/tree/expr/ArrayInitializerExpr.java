package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

public class ArrayInitializerExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayInitializerExpr instantiate(SLocation location) {
			return new ArrayInitializerExpr(location);
		}
	};

	private ArrayInitializerExpr(SLocation location) {
		super(location);
	}

	public ArrayInitializerExpr(NodeList<Expr> values) {
		super(new SLocation(new SNode(kind, runOf(values))));
	}

	public NodeList<Expr> values() {
		return location.nodeChild(VALUES);
	}

	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.nodeWithChild(VALUES, values);
	}

	private static final int VALUES = 0;
}
