package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

public class ArrayDim extends Tree {

	public final static Kind kind = new Kind() {
		public ArrayDim instantiate(SLocation location) {
			return new ArrayDim(location);
		}
	};

	private ArrayDim(SLocation location) {
		super(location);
	}

	public ArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(annotations)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	private static final int ANNOTATIONS = 0;
}
