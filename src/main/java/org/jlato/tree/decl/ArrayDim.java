package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.Factory.children;
import static org.jlato.internal.shapes.LexicalShape.Factory.composite;
import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class ArrayDim extends Tree {

	public final static Kind kind = new Kind() {
		public ArrayDim instantiate(SLocation location) {
			return new ArrayDim(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayDim(SLocation location) {
		super(location);
	}

	public ArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	private static final int ANNOTATIONS = 0;

	public final static LexicalShape shape = composite(
			children(ANNOTATIONS),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);
}
