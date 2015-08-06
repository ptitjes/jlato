package org.jlato.tree.stmt;

import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.util.Mutation;

public interface CatchClause extends Node, TreeCombinators<CatchClause> {

	FormalParameter param();

	CatchClause withParam(FormalParameter param);

	CatchClause withParam(Mutation<FormalParameter> mutation);

	BlockStmt catchBlock();

	CatchClause withCatchBlock(BlockStmt catchBlock);

	CatchClause withCatchBlock(Mutation<BlockStmt> mutation);
}
