package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface SwitchStmt extends Stmt, TreeCombinators<SwitchStmt> {

	Expr selector();

	SwitchStmt withSelector(Expr selector);

	SwitchStmt withSelector(Mutation<Expr> mutation);

	NodeList<SwitchCase> cases();

	SwitchStmt withCases(NodeList<SwitchCase> cases);

	SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation);
}
