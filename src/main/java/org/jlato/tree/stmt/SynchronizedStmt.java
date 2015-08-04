package org.jlato.tree.stmt;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface SynchronizedStmt extends Stmt, TreeCombinators<SynchronizedStmt> {

	Expr expr();

	SynchronizedStmt withExpr(Expr expr);

	SynchronizedStmt withExpr(Mutation<Expr> mutation);

	BlockStmt block();

	SynchronizedStmt withBlock(BlockStmt block);

	SynchronizedStmt withBlock(Mutation<BlockStmt> mutation);
}
