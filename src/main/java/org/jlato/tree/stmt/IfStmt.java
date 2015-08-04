package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface IfStmt extends Stmt, TreeCombinators<IfStmt> {

	Expr condition();

	IfStmt withCondition(Expr condition);

	IfStmt withCondition(Mutation<Expr> mutation);

	Stmt thenStmt();

	IfStmt withThenStmt(Stmt thenStmt);

	IfStmt withThenStmt(Mutation<Stmt> mutation);

	NodeOption<Stmt> elseStmt();

	IfStmt withElseStmt(NodeOption<Stmt> elseStmt);

	IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation);
}
