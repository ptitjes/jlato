package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface NormalAnnotationExpr extends AnnotationExpr, TreeCombinators<NormalAnnotationExpr> {

	QualifiedName name();

	NormalAnnotationExpr withName(QualifiedName name);

	NormalAnnotationExpr withName(Mutation<QualifiedName> mutation);

	NodeList<MemberValuePair> pairs();

	NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs);

	NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation);
}
