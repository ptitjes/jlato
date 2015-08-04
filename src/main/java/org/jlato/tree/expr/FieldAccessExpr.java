package org.jlato.tree.expr;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface FieldAccessExpr extends Expr, TreeCombinators<FieldAccessExpr> {

	NodeOption<Expr> scope();

	FieldAccessExpr withScope(NodeOption<Expr> scope);

	FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation);

	Name name();

	FieldAccessExpr withName(Name name);

	FieldAccessExpr withName(Mutation<Name> mutation);
}
