package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SArrayDim;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public class TDArrayDim extends TreeBase<SArrayDim, Tree, ArrayDim> implements ArrayDim {

	public Kind kind() {
		return Kind.ArrayDim;
	}

	public TDArrayDim(SLocation<SArrayDim> location) {
		super(location);
	}

	public TDArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation<SArrayDim>(SArrayDim.make(TreeBase.<SNodeListState>treeOf(annotations))));
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
