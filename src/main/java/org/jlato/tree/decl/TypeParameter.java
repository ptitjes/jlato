package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface TypeParameter extends Tree, TreeCombinators<TypeParameter> {

	NodeList<AnnotationExpr> annotations();

	TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations);

	TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	Name name();

	TypeParameter withName(Name name);

	TypeParameter withName(Mutation<Name> mutation);

	NodeList<Type> bounds();

	TypeParameter withBounds(NodeList<Type> bounds);

	TypeParameter withBounds(Mutation<NodeList<Type>> mutation);
}
