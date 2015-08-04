package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface UnionType extends Type, TreeCombinators<UnionType> {

	NodeList<Type> types();

	UnionType withTypes(NodeList<Type> types);

	UnionType withTypes(Mutation<NodeList<Type>> mutation);
}
