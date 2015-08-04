package org.jlato.tree.stmt;

import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface ReturnStmt extends Stmt, TreeCombinators<ReturnStmt> {

	NodeOption<Expr> expr();

	ReturnStmt withExpr(NodeOption<Expr> expr);

	ReturnStmt withExpr(Mutation<NodeOption<Expr>> mutation);
}
