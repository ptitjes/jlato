package org.jlato.tree.expr;

import org.jlato.tree.Expr;

public abstract class AnnotationExpr extends Expr {

	protected AnnotationExpr(SLocation location) {
		super(location);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public AnnotationExpr withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	private static final int NAME = 0;
}
