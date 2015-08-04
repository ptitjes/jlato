package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
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

public class TDTryStmt extends TDTree<STryStmt, Stmt, TryStmt> implements TryStmt {

	public Kind kind() {
		return Kind.TryStmt;
	}

	public TDTryStmt(TDLocation<STryStmt> location) {
		super(location);
	}

	public TDTryStmt(NodeList<VariableDeclarationExpr> resources, boolean trailingSemiColon, BlockStmt tryBlock, NodeList<CatchClause> catchs, NodeOption<BlockStmt> finallyBlock) {
		super(new TDLocation<STryStmt>(STryStmt.make(TDTree.<SNodeListState>treeOf(resources), trailingSemiColon, TDTree.<SBlockStmt>treeOf(tryBlock), TDTree.<SNodeListState>treeOf(catchs), TDTree.<SNodeOptionState>treeOf(finallyBlock))));
	}

	public NodeList<VariableDeclarationExpr> resources() {
		return location.safeTraversal(STryStmt.RESOURCES);
	}

	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.safeTraversalReplace(STryStmt.RESOURCES, resources);
	}

	public TryStmt withResources(Mutation<NodeList<VariableDeclarationExpr>> mutation) {
		return location.safeTraversalMutate(STryStmt.RESOURCES, mutation);
	}

	public boolean trailingSemiColon() {
		return location.safeProperty(STryStmt.TRAILING_SEMI_COLON);
	}

	public TryStmt withTrailingSemiColon(boolean trailingSemiColon) {
		return location.safePropertyReplace(STryStmt.TRAILING_SEMI_COLON, trailingSemiColon);
	}

	public TryStmt withTrailingSemiColon(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(STryStmt.TRAILING_SEMI_COLON, mutation);
	}

	public BlockStmt tryBlock() {
		return location.safeTraversal(STryStmt.TRY_BLOCK);
	}

	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.safeTraversalReplace(STryStmt.TRY_BLOCK, tryBlock);
	}

	public TryStmt withTryBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(STryStmt.TRY_BLOCK, mutation);
	}

	public NodeList<CatchClause> catchs() {
		return location.safeTraversal(STryStmt.CATCHS);
	}

	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.safeTraversalReplace(STryStmt.CATCHS, catchs);
	}

	public TryStmt withCatchs(Mutation<NodeList<CatchClause>> mutation) {
		return location.safeTraversalMutate(STryStmt.CATCHS, mutation);
	}

	public NodeOption<BlockStmt> finallyBlock() {
		return location.safeTraversal(STryStmt.FINALLY_BLOCK);
	}

	public TryStmt withFinallyBlock(NodeOption<BlockStmt> finallyBlock) {
		return location.safeTraversalReplace(STryStmt.FINALLY_BLOCK, finallyBlock);
	}

	public TryStmt withFinallyBlock(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(STryStmt.FINALLY_BLOCK, mutation);
	}
}
