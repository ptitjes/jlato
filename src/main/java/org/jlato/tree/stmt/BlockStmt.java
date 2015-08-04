package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface BlockStmt extends Stmt, TreeCombinators<BlockStmt> {

	NodeList<Stmt> stmts();

	BlockStmt withStmts(NodeList<Stmt> stmts);

	BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation);
}
