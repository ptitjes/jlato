package org.jlato.internal.td.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.type.SPrimitiveType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.Primitive;
import org.jlato.tree.type.PrimitiveType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDPrimitiveType extends TreeBase<SPrimitiveType, Type, PrimitiveType> implements PrimitiveType {

	public Kind kind() {
		return Kind.PrimitiveType;
	}

	public TDPrimitiveType(SLocation<SPrimitiveType> location) {
		super(location);
	}

	public TDPrimitiveType(NodeList<AnnotationExpr> annotations, Primitive primitive) {
		super(new SLocation<SPrimitiveType>(SPrimitiveType.make(TreeBase.<SNodeListState>treeOf(annotations), primitive)));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SPrimitiveType.ANNOTATIONS);
	}

	public PrimitiveType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SPrimitiveType.ANNOTATIONS, annotations);
	}

	public PrimitiveType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SPrimitiveType.ANNOTATIONS, mutation);
	}

	public Primitive primitive() {
		return location.safeProperty(SPrimitiveType.PRIMITIVE);
	}

	public PrimitiveType withPrimitive(Primitive primitive) {
		return location.safePropertyReplace(SPrimitiveType.PRIMITIVE, primitive);
	}

	public PrimitiveType withPrimitive(Mutation<Primitive> mutation) {
		return location.safePropertyMutate(SPrimitiveType.PRIMITIVE, mutation);
	}
}
