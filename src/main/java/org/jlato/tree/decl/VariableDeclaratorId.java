package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface VariableDeclaratorId extends Node, TreeCombinators<VariableDeclaratorId> {

	Name name();

	VariableDeclaratorId withName(Name name);

	VariableDeclaratorId withName(Mutation<Name> mutation);

	NodeList<ArrayDim> dims();

	VariableDeclaratorId withDims(NodeList<ArrayDim> dims);

	VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation);
}
