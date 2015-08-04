package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDConditionalExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SConditionalExpr extends SNode<SConditionalExpr> implements SExpr {

	public static BUTree<SConditionalExpr> make(BUTree<? extends SExpr> condition, BUTree<? extends SExpr> thenExpr, BUTree<? extends SExpr> elseExpr) {
		return new BUTree<SConditionalExpr>(new SConditionalExpr(condition, thenExpr, elseExpr));
	}

	public final BUTree<? extends SExpr> condition;

	public final BUTree<? extends SExpr> thenExpr;

	public final BUTree<? extends SExpr> elseExpr;

	public SConditionalExpr(BUTree<? extends SExpr> condition, BUTree<? extends SExpr> thenExpr, BUTree<? extends SExpr> elseExpr) {
		this.condition = condition;
		this.thenExpr = thenExpr;
		this.elseExpr = elseExpr;
	}

	@Override
	public Kind kind() {
		return Kind.ConditionalExpr;
	}

	public BUTree<? extends SExpr> condition() {
		return condition;
	}

	public SConditionalExpr withCondition(BUTree<? extends SExpr> condition) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	public BUTree<? extends SExpr> thenExpr() {
		return thenExpr;
	}

	public SConditionalExpr withThenExpr(BUTree<? extends SExpr> thenExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	public BUTree<? extends SExpr> elseExpr() {
		return elseExpr;
	}

	public SConditionalExpr withElseExpr(BUTree<? extends SExpr> elseExpr) {
		return new SConditionalExpr(condition, thenExpr, elseExpr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SConditionalExpr> location) {
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
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.condition;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return THEN_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> THEN_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.thenExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withThenExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ELSE_EXPR;
		}
	};

	public static STypeSafeTraversal<SConditionalExpr, SExpr, Expr> ELSE_EXPR = new STypeSafeTraversal<SConditionalExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SConditionalExpr state) {
			return state.elseExpr;
		}

		@Override
		public SConditionalExpr doRebuildParentState(SConditionalExpr state, BUTree<SExpr> child) {
			return state.withElseExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return THEN_EXPR;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
