package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface ThrowStmt extends Stmt, TreeCombinators<ThrowStmt> {

	Expr expr();

	ThrowStmt withExpr(Expr expr);

	ThrowStmt withExpr(Mutation<Expr> mutation);
}
