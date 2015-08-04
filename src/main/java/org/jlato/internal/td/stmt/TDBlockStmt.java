package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDBlockStmt extends TDTree<SBlockStmt, Stmt, BlockStmt> implements BlockStmt {

	public Kind kind() {
		return Kind.BlockStmt;
	}

	public TDBlockStmt(TDLocation<SBlockStmt> location) {
		super(location);
	}

	public TDBlockStmt(NodeList<Stmt> stmts) {
		super(new TDLocation<SBlockStmt>(SBlockStmt.make(TDTree.<SNodeList>treeOf(stmts))));
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(SBlockStmt.STMTS);
	}

	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(SBlockStmt.STMTS, stmts);
	}

	public BlockStmt withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(SBlockStmt.STMTS, mutation);
	}
}