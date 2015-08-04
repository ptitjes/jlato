package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface AssertStmt extends Stmt, TreeCombinators<AssertStmt> {

	Expr check();

	AssertStmt withCheck(Expr check);

	AssertStmt withCheck(Mutation<Expr> mutation);

	NodeOption<Expr> msg();

	AssertStmt withMsg(NodeOption<Expr> msg);

	AssertStmt withMsg(Mutation<NodeOption<Expr>> mutation);
}
