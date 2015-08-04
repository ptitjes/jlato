package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.util.Mutation;

public interface WildcardType extends Type, TreeCombinators<WildcardType> {

	NodeList<AnnotationExpr> annotations();

	WildcardType withAnnotations(NodeList<AnnotationExpr> annotations);

	WildcardType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	NodeOption<ReferenceType> ext();

	WildcardType withExt(NodeOption<ReferenceType> ext);

	WildcardType withExt(Mutation<NodeOption<ReferenceType>> mutation);

	NodeOption<ReferenceType> sup();

	WildcardType withSup(NodeOption<ReferenceType> sup);

	WildcardType withSup(Mutation<NodeOption<ReferenceType>> mutation);
}
