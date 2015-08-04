package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SIfStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.IfStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

public class TDIfStmt extends TreeBase<SIfStmt, Stmt, IfStmt> implements IfStmt {

	public Kind kind() {
		return Kind.IfStmt;
	}

	public TDIfStmt(SLocation<SIfStmt> location) {
		super(location);
	}

	public TDIfStmt(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		super(new SLocation<SIfStmt>(SIfStmt.make(TreeBase.<SExpr>treeOf(condition), TreeBase.<SStmt>treeOf(thenStmt), TreeBase.<SNodeOptionState>treeOf(elseStmt))));
	}

	public Expr condition() {
		return location.safeTraversal(SIfStmt.CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SIfStmt.CONDITION, condition);
	}

	public IfStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SIfStmt.CONDITION, mutation);
	}

	public Stmt thenStmt() {
		return location.safeTraversal(SIfStmt.THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.safeTraversalReplace(SIfStmt.THEN_STMT, thenStmt);
	}

	public IfStmt withThenStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SIfStmt.THEN_STMT, mutation);
	}

	public NodeOption<Stmt> elseStmt() {
		return location.safeTraversal(SIfStmt.ELSE_STMT);
	}

	public IfStmt withElseStmt(NodeOption<Stmt> elseStmt) {
		return location.safeTraversalReplace(SIfStmt.ELSE_STMT, elseStmt);
	}

	public IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation) {
		return location.safeTraversalMutate(SIfStmt.ELSE_STMT, mutation);
	}
}
