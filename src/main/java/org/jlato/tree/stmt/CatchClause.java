package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.Parameter;

public class CatchClause extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CatchClause instantiate(SLocation location) {
			return new CatchClause(location);
		}
	};

	private CatchClause(SLocation location) {
		super(location);
	}

	public CatchClause(Parameter except, BlockStmt catchBlock) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(except, catchBlock)))));
	}

	public Parameter except() {
		return location.nodeChild(EXCEPT);
	}

	public CatchClause withExcept(Parameter except) {
		return location.nodeWithChild(EXCEPT, except);
	}

	public BlockStmt catchBlock() {
		return location.nodeChild(CATCH_BLOCK);
	}

	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.nodeWithChild(CATCH_BLOCK, catchBlock);
	}

	private static final int EXCEPT = 0;
	private static final int CATCH_BLOCK = 1;
}
