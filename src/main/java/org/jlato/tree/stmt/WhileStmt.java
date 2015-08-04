package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface WhileStmt extends Stmt, TreeCombinators<WhileStmt> {

	Expr condition();

	WhileStmt withCondition(Expr condition);

	WhileStmt withCondition(Mutation<Expr> mutation);

	Stmt body();

	WhileStmt withBody(Stmt body);

	WhileStmt withBody(Mutation<Stmt> mutation);
}
