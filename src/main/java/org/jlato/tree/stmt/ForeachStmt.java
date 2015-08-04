package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

public interface ForeachStmt extends Stmt, TreeCombinators<ForeachStmt> {

	VariableDeclarationExpr var();

	ForeachStmt withVar(VariableDeclarationExpr var);

	ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation);

	Expr iterable();

	ForeachStmt withIterable(Expr iterable);

	ForeachStmt withIterable(Mutation<Expr> mutation);

	Stmt body();

	ForeachStmt withBody(Stmt body);

	ForeachStmt withBody(Mutation<Stmt> mutation);
}
