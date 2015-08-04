package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface ExpressionStmt extends Stmt, TreeCombinators<ExpressionStmt> {

	Expr expr();

	ExpressionStmt withExpr(Expr expr);

	ExpressionStmt withExpr(Mutation<Expr> mutation);
}
