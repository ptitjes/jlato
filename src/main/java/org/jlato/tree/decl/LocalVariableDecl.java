package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface LocalVariableDecl extends Decl, TreeCombinators<LocalVariableDecl> {

	NodeList<ExtendedModifier> modifiers();

	LocalVariableDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	LocalVariableDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Type type();

	LocalVariableDecl withType(Type type);

	LocalVariableDecl withType(Mutation<Type> mutation);

	NodeList<VariableDeclarator> variables();

	LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables);

	LocalVariableDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation);
}
