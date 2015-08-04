package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDExpressionStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SExpressionStmt extends SNodeState<SExpressionStmt> implements SStmt {

	public static STree<SExpressionStmt> make(STree<? extends SExpr> expr) {
		return new STree<SExpressionStmt>(new SExpressionStmt(expr));
	}

	public final STree<? extends SExpr> expr;

	public SExpressionStmt(STree<? extends SExpr> expr) {
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ExpressionStmt;
	}

	public STree<? extends SExpr> expr() {
		return expr;
	}

	public SExpressionStmt withExpr(STree<? extends SExpr> expr) {
		return new SExpressionStmt(expr);
	}

	@Override
	protected Tree doInstantiate(SLocation<SExpressionStmt> location) {
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
		public STree<?> doTraverse(SExpressionStmt state) {
			return state.expr;
		}

		@Override
		public SExpressionStmt doRebuildParentState(SExpressionStmt state, STree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR), token(LToken.SemiColon)
	);
}
