package org.jlato.tree.expr;

import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface AnnotationExpr extends Expr, ExtendedModifier {

	QualifiedName name();

	AnnotationExpr withName(QualifiedName name);

	AnnotationExpr withName(Mutation<QualifiedName> mutation);
}
