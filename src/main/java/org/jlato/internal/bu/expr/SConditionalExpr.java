package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDConditionalExpr;
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

public class SConditionalExpr extends SNodeState<SConditionalExpr> implements SExpr {

	public static STree<SConditionalExpr> make(STree<? extends SExpr> condition, STree<? extends SExpr> thenExpr, STree<? extends SExpr> elseExpr) {
		return new STree<SConditionalExpr>(new SConditionalExpr(condition, thenExpr, elseExpr));
	}

	public final STree<? extends SExpr> condition;

	public final STree<? extends SExpr> thenExpr;

	public final STree<? extends SExpr> elseExpr;

	public SConditionalExpr(STree<? extends SExpr> condition, STree<? extends SExpr> thenExpr, STree<? extends SExpr> elseExpr) {
		this.condition = condition;
		this.thenExpr = thenExpr;
		this.elseExpr = elseExpr;
	}

	@Override
	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	public STree<? extends SExpr> condition() {
		return condition;
	}

	public SConditionalExpr withCondition(STree<? extends SExpr> condition) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	public STree<? extends SExpr> thenExpr() {
		return thenExpr;
	}

	public SConditionalExpr withThenExpr(STree<? extends SExpr> thenExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	public STree<? extends SExpr> elseExpr() {
		return elseExpr;
	}

	public SConditionalExpr withElseExpr(STree<? extends SExpr> elseExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	@Override
	protected Tree doInstantiate(SLocation<SConditionalExpr> location) {
		return new TDConditionalExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return CONDITION;
	}

	@Override
	public STraversal lastChild() {
		return ELSE_EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SConditionalExpr state = (SConditionalExpr) o;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		if (thenExpr == null ? state.thenExpr != null : !thenExpr.equals(state.thenExpr))
			return false;
		if (elseExpr == null ? state.elseExpr != null : !elseExpr.equals(state.elseExpr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (condition != null) result = 37 * result + condition.hashCode();
		if (thenExpr != null) result = 37 * result + thenExpr.hashCode();
		if (elseExpr != null) result = 37 * result + elseExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> CONDITION = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SConditionalExpr state) {
			return state.condition;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, STree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THEN_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> THEN_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SConditionalExpr state) {
			return state.thenExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, STree<SExpr> child) {
			return state.withThenExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ELSE_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> ELSE_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SConditionalExpr state) {
			return state.elseExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, STree<SExpr> child) {
			return state.withElseExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return THEN_EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(CONDITION),
			token(LToken.QuestionMark).withSpacing(space(), space()),
			child(THEN_EXPR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ELSE_EXPR)
	);
}
