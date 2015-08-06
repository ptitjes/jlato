package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.bu.stmt.SCatchClause;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.CatchClause;
import org.jlato.util.Mutation;

/**
 * A 'catch' clause.
 */
public class TDCatchClause extends TDTree<SCatchClause, Node, CatchClause> implements CatchClause {

	/**
	 * Returns the kind of this 'catch' clause.
	 *
	 * @return the kind of this 'catch' clause.
	 */
	public Kind kind() {
		return Kind.CatchClause;
	}

	/**
	 * Creates a 'catch' clause for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDCatchClause(TDLocation<SCatchClause> location) {
		super(location);
	}

	/**
	 * Creates a 'catch' clause with the specified child trees.
	 *
	 * @param param      the parameter child tree.
	 * @param catchBlock the 'catch' block child tree.
	 */
	public TDCatchClause(FormalParameter param, BlockStmt catchBlock) {
		super(new TDLocation<SCatchClause>(SCatchClause.make(TDTree.<SFormalParameter>treeOf(param), TDTree.<SBlockStmt>treeOf(catchBlock))));
	}

	/**
	 * Returns the parameter of this 'catch' clause.
	 *
	 * @return the parameter of this 'catch' clause.
	 */
	public FormalParameter param() {
		return location.safeTraversal(SCatchClause.PARAM);
	}

	/**
	 * Replaces the parameter of this 'catch' clause.
	 *
	 * @param param the replacement for the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	public CatchClause withParam(FormalParameter param) {
		return location.safeTraversalReplace(SCatchClause.PARAM, param);
	}

	/**
	 * Mutates the parameter of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the parameter of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	public CatchClause withParam(Mutation<FormalParameter> mutation) {
		return location.safeTraversalMutate(SCatchClause.PARAM, mutation);
	}

	/**
	 * Returns the 'catch' block of this 'catch' clause.
	 *
	 * @return the 'catch' block of this 'catch' clause.
	 */
	public BlockStmt catchBlock() {
		return location.safeTraversal(SCatchClause.CATCH_BLOCK);
	}

	/**
	 * Replaces the 'catch' block of this 'catch' clause.
	 *
	 * @param catchBlock the replacement for the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.safeTraversalReplace(SCatchClause.CATCH_BLOCK, catchBlock);
	}

	/**
	 * Mutates the 'catch' block of this 'catch' clause.
	 *
	 * @param mutation the mutation to apply to the 'catch' block of this 'catch' clause.
	 * @return the resulting mutated 'catch' clause.
	 */
	public CatchClause withCatchBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SCatchClause.CATCH_BLOCK, mutation);
	}
}
