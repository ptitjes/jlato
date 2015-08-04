package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public interface PrimitiveType extends Type, TreeCombinators<PrimitiveType> {

	NodeList<AnnotationExpr> annotations();

	PrimitiveType withAnnotations(NodeList<AnnotationExpr> annotations);

	PrimitiveType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	Primitive primitive();

	PrimitiveType withPrimitive(Primitive primitive);

	PrimitiveType withPrimitive(Mutation<Primitive> mutation);
}
