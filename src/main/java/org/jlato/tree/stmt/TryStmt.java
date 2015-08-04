package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.util.Mutation;

public interface TryStmt extends Stmt, TreeCombinators<TryStmt> {

	NodeList<VariableDeclarationExpr> resources();

	TryStmt withResources(NodeList<VariableDeclarationExpr> resources);

	TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation);

	boolean trailingSemiColon();

	TryStmt withTrailingSemiColon(boolean trailingSemiColon);

	TryStmt withTrailingSemiColon(Mutation<Boolean> mutation);

	BlockStmt tryBlock();

	TryStmt withTryBlock(BlockStmt tryBlock);

	TryStmt withTryBlock(Mutation<BlockStmt> mutation);

	NodeList<CatchClause> catchs();

	TryStmt withCatchs(NodeList<CatchClause> catchs);

	TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation);

	NodeOption<BlockStmt> finallyBlock();

	TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock);

	TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation);
}
