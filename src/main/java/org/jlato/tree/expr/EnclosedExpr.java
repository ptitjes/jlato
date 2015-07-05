package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class EnclosedExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnclosedExpr instantiate(SLocation location) {
			return new EnclosedExpr(location);
		}
	};

	private EnclosedExpr(SLocation location) {
		super(location);
	}

	public EnclosedExpr(Expr inner) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(inner)))));
	}

	public Expr inner() {
		return location.nodeChild(INNER);
	}

	public EnclosedExpr withInner(Expr inner) {
		return location.nodeWithChild(INNER, inner);
	}

	private static final int INNER = 0;
}
