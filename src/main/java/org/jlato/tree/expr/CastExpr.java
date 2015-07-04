package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class CastExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CastExpr instantiate(SLocation location) {
			return new CastExpr(location);
		}
	};

	private CastExpr(SLocation location) {
		super(location);
	}

	public CastExpr(Type type, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(type, expr)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public CastExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public CastExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int TYPE = 0;
	private static final int EXPR = 1;
}
