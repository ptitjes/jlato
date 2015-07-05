package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.VariableDeclarationExpr;

public class TryStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TryStmt instantiate(SLocation location) {
			return new TryStmt(location);
		}
	};

	private TryStmt(SLocation location) {
		super(location);
	}

	public TryStmt(NodeList<VariableDeclarationExpr> resources, BlockStmt tryBlock, NodeList<CatchClause> catchs, BlockStmt finallyBlock) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(resources, tryBlock, catchs, finallyBlock)))));
	}

	public NodeList<VariableDeclarationExpr> resources() {
		return location.nodeChild(RESOURCES);
	}

	public TryStmt withResources(NodeList<VariableDeclarationExpr> resources) {
		return location.nodeWithChild(RESOURCES, resources);
	}

	public BlockStmt tryBlock() {
		return location.nodeChild(TRY_BLOCK);
	}

	public TryStmt withTryBlock(BlockStmt tryBlock) {
		return location.nodeWithChild(TRY_BLOCK, tryBlock);
	}

	public NodeList<CatchClause> catchs() {
		return location.nodeChild(CATCHS);
	}

	public TryStmt withCatchs(NodeList<CatchClause> catchs) {
		return location.nodeWithChild(CATCHS, catchs);
	}

	public BlockStmt finallyBlock() {
		return location.nodeChild(FINALLY_BLOCK);
	}

	public TryStmt withFinallyBlock(BlockStmt finallyBlock) {
		return location.nodeWithChild(FINALLY_BLOCK, finallyBlock);
	}

	private static final int RESOURCES = 0;
	private static final int TRY_BLOCK = 1;
	private static final int CATCHS = 2;
	private static final int FINALLY_BLOCK = 3;
}
