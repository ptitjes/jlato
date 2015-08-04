package org.jlato.tree.expr;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface MarkerAnnotationExpr extends AnnotationExpr, TreeCombinators<MarkerAnnotationExpr> {

	QualifiedName name();

	MarkerAnnotationExpr withName(QualifiedName name);

	MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation);
}
