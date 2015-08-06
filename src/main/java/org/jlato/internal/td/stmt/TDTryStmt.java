package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.bu.stmt.STryStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.CatchClause;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.TryStmt;
import org.jlato.util.Mutation;

/**
 * A 'try' statement.
 */
public class TDTryStmt extends TDTree<STryStmt, Stmt, TryStmt> implements TryStmt {

	/**
	 * Returns the kind of this 'try' statement.
	 *
	 * @return the kind of this 'try' statement.
	 */
	public Kind kind() {
		return Kind.TryStmt;
	}

	/**
	 * Creates a 'try' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDTryStmt(TDLocation<STryStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'try' statement with the specified child trees.
	 *
	 * @param resources         the resources child tree.
	 * @param trailingSemiColon the has a trailing semi-colon for its resources child tree.
	 * @param tryBlock          the 'try' block child tree.
	 * @param catchs            the catchs child tree.
	 * @param finallyBlock      the 'finally' block child tree.
	 */
	public TDTryStmt(NodeList<VariableDeclarationExpr> resources, boolean trailingSemiColon, BlockStmt tryBlock, NodeList<CatchClause> catchs, NodeOption<BlockStmt> finallyBlock) {
		super(new TDLocation<STryStmt>(STryStmt.make(TDTree.<SNodeList>treeOf(resources), trailingSemiColon, TDTree.<SBlockStmt>treeOf(tryBlock), TDTree.<SNodeList>treeOf(catchs), TDTree.<SNodeOption>treeOf(finallyBlock))));
	}

	/**
	 * Returns the resources of this 'try' statement.
	 *
	 * @return the resources of this 'try' statement.
	 */
	public NodeList<VariableDeclarationExpr> resources() {
		return location.safeTraversal(STryStmt.RESOURCES);
	}

	/**
	 * Replaces the resources of this 'try' statement.
	 *
	 * @param resources the replacement for the resources of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.safeTraversalReplace(STryStmt.RESOURCES, resources);
	}

	/**
	 * Mutates the resources of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the resources of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation) {
		return location.safeTraversalMutate(STryStmt.RESOURCES, mutation);
	}

	/**
	 * Tests whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @return <code>true</code> if this 'try' statement has a trailing semi-colon for its resources, <code>false</code> otherwise.
	 */
	public boolean trailingSemiColon() {
		return location.safeProperty(STryStmt.TRAILING_SEMI_COLON);
	}

	/**
	 * Sets whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @param trailingSemiColon <code>true</code> if this 'try' statement has a trailing semi-colon for its resources, <code>false</code> otherwise.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withTrailingSemiColon(boolean trailingSemiColon) {
		return location.safePropertyReplace(STryStmt.TRAILING_SEMI_COLON, trailingSemiColon);
	}

	/**
	 * Mutates whether this 'try' statement has a trailing semi-colon for its resources.
	 *
	 * @param mutation the mutation to apply to whether this 'try' statement has a trailing semi-colon for its resources.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withTrailingSemiColon(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(STryStmt.TRAILING_SEMI_COLON, mutation);
	}

	/**
	 * Returns the 'try' block of this 'try' statement.
	 *
	 * @return the 'try' block of this 'try' statement.
	 */
	public BlockStmt tryBlock() {
		return location.safeTraversal(STryStmt.TRY_BLOCK);
	}

	/**
	 * Replaces the 'try' block of this 'try' statement.
	 *
	 * @param tryBlock the replacement for the 'try' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.safeTraversalReplace(STryStmt.TRY_BLOCK, tryBlock);
	}

	/**
	 * Mutates the 'try' block of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the 'try' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withTryBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(STryStmt.TRY_BLOCK, mutation);
	}

	/**
	 * Returns the catchs of this 'try' statement.
	 *
	 * @return the catchs of this 'try' statement.
	 */
	public NodeList<CatchClause> catchs() {
		return location.safeTraversal(STryStmt.CATCHS);
	}

	/**
	 * Replaces the catchs of this 'try' statement.
	 *
	 * @param catchs the replacement for the catchs of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.safeTraversalReplace(STryStmt.CATCHS, catchs);
	}

	/**
	 * Mutates the catchs of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the catchs of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation) {
		return location.safeTraversalMutate(STryStmt.CATCHS, mutation);
	}

	/**
	 * Returns the 'finally' block of this 'try' statement.
	 *
	 * @return the 'finally' block of this 'try' statement.
	 */
	public NodeOption<BlockStmt> finallyBlock() {
		return location.safeTraversal(STryStmt.FINALLY_BLOCK);
	}

	/**
	 * Replaces the 'finally' block of this 'try' statement.
	 *
	 * @param finallyBlock the replacement for the 'finally' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock) {
		return location.safeTraversalReplace(STryStmt.FINALLY_BLOCK, finallyBlock);
	}

	/**
	 * Mutates the 'finally' block of this 'try' statement.
	 *
	 * @param mutation the mutation to apply to the 'finally' block of this 'try' statement.
	 * @return the resulting mutated 'try' statement.
	 */
	public TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(STryStmt.FINALLY_BLOCK, mutation);
	}
}
