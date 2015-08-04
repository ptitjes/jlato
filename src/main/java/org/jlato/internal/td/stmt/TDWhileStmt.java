package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.bu.stmt.SWhileStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.WhileStmt;
import org.jlato.util.Mutation;

public class TDWhileStmt extends TDTree<SWhileStmt, Stmt, WhileStmt> implements WhileStmt {

	public Kind kind() {
		return Kind.WhileStmt;
	}

	public TDWhileStmt(SLocation<SWhileStmt> location) {
		super(location);
	}

	public TDWhileStmt(Expr condition, Stmt body) {
		super(new SLocation<SWhileStmt>(SWhileStmt.make(TDTree.<SExpr>treeOf(condition), TDTree.<SStmt>treeOf(body))));
	}

	public Expr condition() {
		return location.safeTraversal(SWhileStmt.CONDITION);
	}

	public WhileStmt withCondition(Expr condition) {
		return location.safeTraversalReplace(SWhileStmt.CONDITION, condition);
	}

	public WhileStmt withCondition(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SWhileStmt.CONDITION, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(SWhileStmt.BODY);
	}

	public WhileStmt withBody(Stmt body) {
		return location.safeTraversalReplace(SWhileStmt.BODY, body);
	}

	public WhileStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SWhileStmt.BODY, mutation);
	}
}
