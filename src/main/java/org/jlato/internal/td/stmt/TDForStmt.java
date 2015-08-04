package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SForStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ForStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDForStmt extends TDTree<SForStmt, Stmt, ForStmt> implements ForStmt {

	public Kind kind() {
		return Kind.ForStmt;
	}

	public TDForStmt(TDLocation<SForStmt> location) {
		super(location);
	}

	public TDForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new TDLocation<SForStmt>(SForStmt.make(TDTree.<SNodeListState>treeOf(init), TDTree.<SExpr>treeOf(compare), TDTree.<SNodeListState>treeOf(update), TDTree.<SStmt>treeOf(body))));
	}

	public NodeList<Expr> init() {
		return location.safeTraversal(SForStmt.INIT);
	}

	public ForStmt withInit(NodeList<Expr> init) {
		return location.safeTraversalReplace(SForStmt.INIT, init);
	}

	public ForStmt withInit(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SForStmt.INIT, mutation);
	}

	public Expr compare() {
		return location.safeTraversal(SForStmt.COMPARE);
	}

	public ForStmt withCompare(Expr compare) {
		return location.safeTraversalReplace(SForStmt.COMPARE, compare);
	}

	public ForStmt withCompare(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SForStmt.COMPARE, mutation);
	}

	public NodeList<Expr> update() {
		return location.safeTraversal(SForStmt.UPDATE);
	}

	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.safeTraversalReplace(SForStmt.UPDATE, update);
	}

	public ForStmt withUpdate(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SForStmt.UPDATE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(SForStmt.BODY);
	}

	public ForStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SForStmt.BODY, body);
	}

	public ForStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SForStmt.BODY, mutation);
	}
}
