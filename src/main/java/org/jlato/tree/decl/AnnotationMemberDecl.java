package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface AnnotationMemberDecl extends MemberDecl, TreeCombinators<AnnotationMemberDecl> {

	NodeList<ExtendedModifier> modifiers();

	AnnotationMemberDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	AnnotationMemberDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Type type();

	AnnotationMemberDecl withType(Type type);

	AnnotationMemberDecl withType(Mutation<Type> mutation);

	Name name();

	AnnotationMemberDecl withName(Name name);

	AnnotationMemberDecl withName(Mutation<Name> mutation);

	NodeList<ArrayDim> dims();

	AnnotationMemberDecl withDims(NodeList<ArrayDim> dims);

	AnnotationMemberDecl withDims(Mutation<NodeList<ArrayDim>> mutation);

	NodeOption<Expr> defaultValue();

	AnnotationMemberDecl withDefaultValue(NodeOption<Expr> defaultValue);

	AnnotationMemberDecl withDefaultValue(Mutation<NodeOption<Expr>> mutation);
}
