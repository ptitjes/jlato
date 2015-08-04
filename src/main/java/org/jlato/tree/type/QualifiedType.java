package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface QualifiedType extends ReferenceType, TreeCombinators<QualifiedType> {

	NodeList<AnnotationExpr> annotations();

	QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations);

	QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	NodeOption<QualifiedType> scope();

	QualifiedType withScope(NodeOption<QualifiedType> scope);

	QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation);

	Name name();

	QualifiedType withName(Name name);

	QualifiedType withName(Mutation<Name> mutation);

	NodeOption<NodeList<Type>> typeArgs();

	QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs);

	QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation);
}
