package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class MarkerAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MarkerAnnotationExpr instantiate(SLocation location) {
			return new MarkerAnnotationExpr(location);
		}
	};

	private MarkerAnnotationExpr(SLocation location) {
		super(location);
	}

	public MarkerAnnotationExpr() {
		super(new SLocation(new SNode(kind, runOf())));
	}

}
