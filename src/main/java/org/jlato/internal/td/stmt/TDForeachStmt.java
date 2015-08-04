package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.bu.stmt.SForeachStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.ForeachStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDForeachStmt extends TDTree<SForeachStmt, Stmt, ForeachStmt> implements ForeachStmt {

	public Kind kind() {
		return Kind.ForeachStmt;
	}

	public TDForeachStmt(TDLocation<SForeachStmt> location) {
		super(location);
	}

	public TDForeachStmt(VariableDeclarationExpr var, Expr iterable, Stmt body) {
		super(new TDLocation<SForeachStmt>(SForeachStmt.make(TDTree.<SVariableDeclarationExpr>treeOf(var), TDTree.<SExpr>treeOf(iterable), TDTree.<SStmt>treeOf(body))));
	}

	public VariableDeclarationExpr var() {
		return location.safeTraversal(SForeachStmt.VAR);
	}

	public ForeachStmt withVar(VariableDeclarationExpr var) {
		return location.safeTraversalReplace(SForeachStmt.VAR, var);
	}

	public ForeachStmt withVar(Mutation<VariableDeclarationExpr> mutation) {
		return location.safeTraversalMutate(SForeachStmt.VAR, mutation);
	}

	public Expr iterable() {
		return location.safeTraversal(SForeachStmt.ITERABLE);
	}

	public ForeachStmt withIterable(Expr iterable) {
		return location.safeTraversalReplace(SForeachStmt.ITERABLE, iterable);
	}

	public ForeachStmt withIterable(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SForeachStmt.ITERABLE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(SForeachStmt.BODY);
	}

	public ForeachStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SForeachStmt.BODY, body);
	}

	public ForeachStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SForeachStmt.BODY, mutation);
	}
}
