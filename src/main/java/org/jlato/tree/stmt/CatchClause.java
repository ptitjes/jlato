package org.jlato.tree.stmt;

import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.util.Mutation;

public interface CatchClause extends Tree, TreeCombinators<CatchClause> {

	FormalParameter except();

	CatchClause withExcept(FormalParameter except);

	CatchClause withExcept(Mutation<FormalParameter> mutation);

	BlockStmt catchBlock();

	CatchClause withCatchBlock(BlockStmt catchBlock);

	CatchClause withCatchBlock(Mutation<BlockStmt> mutation);
}
