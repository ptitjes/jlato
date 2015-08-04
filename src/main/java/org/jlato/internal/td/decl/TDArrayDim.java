package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.decl.SArrayDim;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public class TDArrayDim extends TDTree<SArrayDim, Node, ArrayDim> implements ArrayDim {

	public Kind kind() {
		return Kind.ArrayDim;
	}

	public TDArrayDim(TDLocation<SArrayDim> location) {
		super(location);
	}

	public TDArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new TDLocation<SArrayDim>(SArrayDim.make(TDTree.<SNodeList>treeOf(annotations))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SArrayDim.ANNOTATIONS);
	}

	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SArrayDim.ANNOTATIONS, annotations);
	}

	public ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SArrayDim.ANNOTATIONS, mutation);
	}
}