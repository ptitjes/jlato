package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface FormalParameter extends Node, TreeCombinators<FormalParameter> {

	NodeList<ExtendedModifier> modifiers();

	FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers);

	FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	Type type();

	FormalParameter withType(Type type);

	FormalParameter withType(Mutation<Type> mutation);

	boolean isVarArgs();

	FormalParameter setVarArgs(boolean isVarArgs);

	FormalParameter setVarArgs(Mutation<Boolean> mutation);

	VariableDeclaratorId id();

	FormalParameter withId(VariableDeclaratorId id);

	FormalParameter withId(Mutation<VariableDeclaratorId> mutation);
}
