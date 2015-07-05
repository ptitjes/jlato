package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class MarkerAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MarkerAnnotationExpr instantiate(SLocation location) {
			return new MarkerAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MarkerAnnotationExpr(SLocation location) {
		super(location);
	}

	public MarkerAnnotationExpr(QName name) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name)))));
	}

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME)
	);
}
