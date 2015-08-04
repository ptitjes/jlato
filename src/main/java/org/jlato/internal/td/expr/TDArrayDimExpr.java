package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.expr.SArrayDimExpr;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDArrayDimExpr extends TDTree<SArrayDimExpr, Node, ArrayDimExpr> implements ArrayDimExpr {

	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	public TDArrayDimExpr(SLocation<SArrayDimExpr> location) {
		super(location);
	}

	public TDArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expr) {
		super(new SLocation<SArrayDimExpr>(SArrayDimExpr.make(TDTree.<SNodeListState>treeOf(annotations), TDTree.<SExpr>treeOf(expr))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SArrayDimExpr.ANNOTATIONS);
	}

	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SArrayDimExpr.ANNOTATIONS, annotations);
	}

	public ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SArrayDimExpr.ANNOTATIONS, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(SArrayDimExpr.EXPR);
	}

	public ArrayDimExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(SArrayDimExpr.EXPR, expr);
	}

	public ArrayDimExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SArrayDimExpr.EXPR, mutation);
	}
}
