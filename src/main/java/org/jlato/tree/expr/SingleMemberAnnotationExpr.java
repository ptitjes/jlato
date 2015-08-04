package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface SingleMemberAnnotationExpr extends AnnotationExpr, TreeCombinators<SingleMemberAnnotationExpr> {

	QualifiedName name();

	SingleMemberAnnotationExpr withName(QualifiedName name);

	SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation);

	Expr memberValue();

	SingleMemberAnnotationExpr withMemberValue(Expr memberValue);

	SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation);
}
