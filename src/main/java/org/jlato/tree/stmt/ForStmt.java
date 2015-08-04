package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface ForStmt extends Stmt, TreeCombinators<ForStmt> {

	NodeList<Expr> init();

	ForStmt withInit(NodeList<Expr> init);

	ForStmt withInit(Mutation<NodeList<Expr>> mutation);

	Expr compare();

	ForStmt withCompare(Expr compare);

	ForStmt withCompare(Mutation<Expr> mutation);

	NodeList<Expr> update();

	ForStmt withUpdate(NodeList<Expr> update);

	ForStmt withUpdate(Mutation<NodeList<Expr>> mutation);

	Stmt body();

	ForStmt withBody(Stmt body);

	ForStmt withBody(Mutation<Stmt> mutation);
}
