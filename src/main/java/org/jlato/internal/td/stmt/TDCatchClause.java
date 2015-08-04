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

public class TDCatchClause extends TDTree<SCatchClause, Node, CatchClause> implements CatchClause {

	public Kind kind() {
		return Kind.CatchClause;
	}

	public TDCatchClause(TDLocation<SCatchClause> location) {
		super(location);
	}

	public TDCatchClause(FormalParameter except, BlockStmt catchBlock) {
		super(new TDLocation<SCatchClause>(SCatchClause.make(TDTree.<SFormalParameter>treeOf(except), TDTree.<SBlockStmt>treeOf(catchBlock))));
	}

	public FormalParameter except() {
		return location.safeTraversal(SCatchClause.EXCEPT);
	}

	public CatchClause withExcept(FormalParameter except) {
		return location.safeTraversalReplace(SCatchClause.EXCEPT, except);
	}

	public CatchClause withExcept(Mutation<FormalParameter> mutation) {
		return location.safeTraversalMutate(SCatchClause.EXCEPT, mutation);
	}

	public BlockStmt catchBlock() {
		return location.safeTraversal(SCatchClause.CATCH_BLOCK);
	}

	public CatchClause withCatchBlock(BlockStmt catchBlock) {
		return location.safeTraversalReplace(SCatchClause.CATCH_BLOCK, catchBlock);
	}

	public CatchClause withCatchBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SCatchClause.CATCH_BLOCK, mutation);
	}
}
