package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class InstanceOfExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public InstanceOfExpr instantiate(SLocation location) {
			return new InstanceOfExpr(location);
		}
	};

	private InstanceOfExpr(SLocation location) {
		super(location);
	}

	public InstanceOfExpr(Expr expr, Type type) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(expr, type)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public InstanceOfExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public InstanceOfExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	private static final int EXPR = 0;
	private static final int TYPE = 1;
}
