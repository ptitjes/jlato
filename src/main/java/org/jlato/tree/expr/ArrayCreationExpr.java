package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface ArrayCreationExpr extends Expr, TreeCombinators<ArrayCreationExpr> {

	Type type();

	ArrayCreationExpr withType(Type type);

	ArrayCreationExpr withType(Mutation<Type> mutation);

	NodeList<ArrayDimExpr> dimExprs();

	ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs);

	ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation);

	NodeList<ArrayDim> dims();

	ArrayCreationExpr withDims(NodeList<ArrayDim> dims);

	ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation);

	NodeOption<ArrayInitializerExpr> init();

	ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> init);

	ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation);
}
