package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface DoStmt extends Stmt, TreeCombinators<DoStmt> {

	Stmt body();

	DoStmt withBody(Stmt body);

	DoStmt withBody(Mutation<Stmt> mutation);

	Expr condition();

	DoStmt withCondition(Expr condition);

	DoStmt withCondition(Mutation<Expr> mutation);
}
