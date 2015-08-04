package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface PackageDecl extends Node, TreeCombinators<PackageDecl> {

	NodeList<AnnotationExpr> annotations();

	PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations);

	PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	QualifiedName name();

	PackageDecl withName(QualifiedName name);

	PackageDecl withName(Mutation<QualifiedName> mutation);
}
