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
import static org.jlato.printer.SpacingConstraint.space;

public class SCastExpr extends SNodeState<SCastExpr> implements SExpr {

	public static STree<SCastExpr> make(STree<? extends SType> type, STree<? extends SExpr> expr) {
		return new STree<SCastExpr>(new SCastExpr(type, expr));
	}

	public final STree<? extends SType> type;

	public final STree<? extends SExpr> expr;

	public SCastExpr(STree<? extends SType> type, STree<? extends SExpr> expr) {
		this.type = type;
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.CastExpr;
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SCastExpr withType(STree<? extends SType> type) {
		return new SCastExpr(type, expr);
	}

	public STree<? extends SExpr> expr() {
		return expr;
	}

	public SCastExpr withExpr(STree<? extends SExpr> expr) {
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
		public STree<?> doTraverse(SCastExpr state) {
			return state.type;
		}

		@Override
		public SCastExpr doRebuildParentState(SCastExpr state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SCastExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SCastExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SCastExpr state) {
			return state.expr;
		}

		@Override
		public SCastExpr doRebuildParentState(SCastExpr state, STree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);
}
