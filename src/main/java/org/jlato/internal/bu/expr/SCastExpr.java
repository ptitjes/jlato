package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDCastExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SCastExpr extends SNode<SCastExpr> implements SExpr {

	public static BUTree<SCastExpr> make(BUTree<? extends SType> type, BUTree<? extends SExpr> expr) {
		return new BUTree<SCastExpr>(new SCastExpr(type, expr));
	}

	public final BUTree<? extends SType> type;

	public final BUTree<? extends SExpr> expr;

	public SCastExpr(BUTree<? extends SType> type, BUTree<? extends SExpr> expr) {
		this.type = type;
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.CastExpr;
	}

	public SCastExpr withType(BUTree<? extends SType> type) {
		return new SCastExpr(type, expr);
	}

	public SCastExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SCastExpr(type, expr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SCastExpr> location) {
		return new TDCastExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return TYPE;
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
		SCastExpr state = (SCastExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (type != null) result = 37 * result + type.hashCode();
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCastExpr, SType, Type> TYPE = new STypeSafeTraversal<SCastExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SCastExpr state) {
			return state.type;
		}

		@Override
		public SCastExpr doRebuildParentState(SCastExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SCastExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SCastExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SCastExpr state) {
			return state.expr;
		}

		@Override
		public SCastExpr doRebuildParentState(SCastExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);
}
