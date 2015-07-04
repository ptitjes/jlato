package org.jlato.tree.expr;

import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QName;

public abstract class AnnotationExpr extends Expr {

	protected AnnotationExpr(SLocation location) {
		super(location);
	}

	public QName name() {
		return location.nodeChild(NAME);
	}

	public AnnotationExpr withName(QName name) {
		return location.nodeWithChild(NAME, name);
	}

	private static final int NAME = 0;
}
