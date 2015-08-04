package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDExpressionStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SExpressionStmt extends SNode<SExpressionStmt> implements SStmt {

	public static BUTree<SExpressionStmt> make(BUTree<? extends SExpr> expr) {
		return new BUTree<SExpressionStmt>(new SExpressionStmt(expr));
	}

	public final BUTree<? extends SExpr> expr;

	public SExpressionStmt(BUTree<? extends SExpr> expr) {
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	public BUTree<? extends SExpr> expr() {
		return expr;
	}

	public SExpressionStmt withExpr(BUTree<? extends SExpr> expr) {
		return new SExpressionStmt(expr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SExpressionStmt> location) {
		return new TDExpressionStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	@Override
	public STraversal lastChild() {
		return EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SExpressionStmt state = (SExpressionStmt) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SExpressionStmt, SExpr, Expr> EXPR = new STypeSafeTraversal<SExpressionStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SExpressionStmt state) {
			return state.expr;
		}

		@Override
		public SExpressionStmt doRebuildParentState(SExpressionStmt state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR), token(LToken.SemiColon)
	);
}
