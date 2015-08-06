package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.bu.stmt.SCatchClause;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.stmt.*;
import org.jlato.util.Mutation;

public class TDCatchClause extends TDTree<SCatchClause, Node, CatchClause> implements CatchClause {

	public Kind kind() {
		return Kind.CatchClause;
	}

	public TDCatchClause(TDLocation<SCatchClause> location) {
		super(location);
	}

	public TDCatchClause(FormalParameter param, BlockStmt catchBlock) {
		super(new TDLocation<SCatchClause>(SCatchClause.make(TDTree.<SFormalParameter>treeOf(param), TDTree.<SBlockStmt>treeOf(catchBlock))));
	}

	public FormalParameter param() {
		return location.safeTraversal(SCatchClause.PARAM);
	}

	public CatchClause withParam(FormalParameter param) {
		return location.safeTraversalReplace(SCatchClause.PARAM, param);
	}

	public CatchClause withParam(Mutation<FormalParameter> mutation) {
		return location.safeTraversalMutate(SCatchClause.PARAM, mutation);
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
