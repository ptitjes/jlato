package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.bu.stmt.SSynchronizedStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.SynchronizedStmt;
import org.jlato.util.Mutation;

public class TDSynchronizedStmt extends TDTree<SSynchronizedStmt, Stmt, SynchronizedStmt> implements SynchronizedStmt {

	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	public TDSynchronizedStmt(TDLocation<SSynchronizedStmt> location) {
		super(location);
	}

	public TDSynchronizedStmt(Expr expr, BlockStmt block) {
		super(new TDLocation<SSynchronizedStmt>(SSynchronizedStmt.make(TDTree.<SExpr>treeOf(expr), TDTree.<SBlockStmt>treeOf(block))));
	}

	public Expr expr() {
		return location.safeTraversal(SSynchronizedStmt.EXPR);
	}

	public SynchronizedStmt withExpr(Expr expr) {
		return location.safeTraversalReplace(SSynchronizedStmt.EXPR, expr);
	}

	public SynchronizedStmt withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSynchronizedStmt.EXPR, mutation);
	}

	public BlockStmt block() {
		return location.safeTraversal(SSynchronizedStmt.BLOCK);
	}

	public SynchronizedStmt withBlock(BlockStmt block) {
		return location.safeTraversalReplace(SSynchronizedStmt.BLOCK, block);
	}

	public SynchronizedStmt withBlock(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SSynchronizedStmt.BLOCK, mutation);
	}
}
