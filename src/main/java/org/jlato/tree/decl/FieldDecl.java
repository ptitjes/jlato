package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface FieldDecl extends MemberDecl, TreeCombinators<FieldDecl> {

	NodeList<ExtendedModifier> modifiers();

	FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Type type();

	FieldDecl withType(Type type);

	FieldDecl withType(Mutation<Type> mutation);

	NodeList<VariableDeclarator> variables();

	FieldDecl withVariables(NodeList<VariableDeclarator> variables);

	FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation);
}
