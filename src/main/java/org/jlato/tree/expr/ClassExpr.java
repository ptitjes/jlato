package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class ClassExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ClassExpr instantiate(SLocation location) {
			return new ClassExpr(location);
		}
	};

	private ClassExpr(SLocation location) {
		super(location);
	}

	public ClassExpr(Type type) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(type)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public ClassExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	private static final int TYPE = 0;
}
