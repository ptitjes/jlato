package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

public class MarkerAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MarkerAnnotationExpr instantiate(SLocation location) {
			return new MarkerAnnotationExpr(location);
		}
	};

	private MarkerAnnotationExpr(SLocation location) {
		super(location);
	}

	public MarkerAnnotationExpr(QName name) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(name)))));
	}

}
