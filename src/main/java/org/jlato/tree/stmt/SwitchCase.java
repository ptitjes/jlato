package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface SwitchCase extends Tree, TreeCombinators<SwitchCase> {

	NodeOption<Expr> label();

	SwitchCase withLabel(NodeOption<Expr> label);

	SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation);

	NodeList<Stmt> stmts();

	SwitchCase withStmts(NodeList<Stmt> stmts);

	SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation);
}
