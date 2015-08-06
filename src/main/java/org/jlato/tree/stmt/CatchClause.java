package org.jlato.tree.stmt;

import org.jlato.tree.Node;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.util.Mutation;

/**
 * A 'catch' clause.
 */
public interface CatchClause extends Node, TreeCombinators<CatchClause> {

	/**
	 * Returns the parameter of this 'catch' clause.
	 *
	 * @return the parameter of this 'catch' clause.
	 */
	FormalParameter param();

	/**
	 * Replaces the parameter of this 'catch' clause.
	 *
	 * @param param the replacement for the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withParam(FormalParameter param);

	/**
	 * Mutates the parameter of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withParam(Mutation<FormalParameter> mutation);

	/**
	 * Returns the 'catch' block of this 'catch' clause.
	 *
	 * @return the 'catch' block of this 'catch' clause.
	 */
	BlockStmt catchBlock();

	/**
	 * Replaces the 'catch' block of this 'catch' clause.
	 *
	 * @param catchBlock the replacement for the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withCatchBlock(BlockStmt catchBlock);

	/**
	 * Mutates the 'catch' block of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	CatchClause withCatchBlock(Mutation<BlockStmt> mutation);
}
