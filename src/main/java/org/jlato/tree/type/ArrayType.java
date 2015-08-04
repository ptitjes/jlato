package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.util.Mutation;

public interface ArrayType extends ReferenceType, TreeCombinators<ArrayType> {

	Type componentType();

	ArrayType withComponentType(Type componentType);

	ArrayType withComponentType(Mutation<Type> mutation);

	NodeList<ArrayDim> dims();

	ArrayType withDims(NodeList<ArrayDim> dims);

	ArrayType withDims(Mutation<NodeList<ArrayDim>> mutation);
}
