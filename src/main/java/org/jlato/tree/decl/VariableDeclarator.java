package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface VariableDeclarator extends Node, TreeCombinators<VariableDeclarator> {

	VariableDeclaratorId id();

	VariableDeclarator withId(VariableDeclaratorId id);

	VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation);

	NodeOption<Expr> init();

	VariableDeclarator withInit(NodeOption<Expr> init);

	VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation);
}
