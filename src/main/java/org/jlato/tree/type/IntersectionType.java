package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface IntersectionType extends Type, TreeCombinators<IntersectionType> {

	NodeList<Type> types();

	IntersectionType withTypes(NodeList<Type> types);

	IntersectionType withTypes(Mutation<NodeList<Type>> mutation);
}
