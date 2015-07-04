package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class ArrayDimExpr extends Tree {

	public final static Kind kind = new Kind() {
		public ArrayDimExpr instantiate(SLocation location) {
			return new ArrayDimExpr(location);
		}
	};

	private ArrayDimExpr(SLocation location) {
		super(location);
	}

	public ArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expression) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(annotations, expression)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Expr expression() {
		return location.nodeChild(EXPRESSION);
	}

	public ArrayDimExpr withExpression(Expr expression) {
		return location.nodeWithChild(EXPRESSION, expression);
	}

	private static final int ANNOTATIONS = 0;
	private static final int EXPRESSION = 1;
}
