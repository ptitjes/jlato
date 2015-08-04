package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDInstanceOfExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SInstanceOfExpr extends SNodeState<SInstanceOfExpr> implements SExpr {

	public static BUTree<SInstanceOfExpr> make(BUTree<? extends SExpr> expr, BUTree<? extends SType> type) {
		return new BUTree<SInstanceOfExpr>(new SInstanceOfExpr(expr, type));
	}

	public final BUTree<? extends SExpr> expr;

	public final BUTree<? extends SType> type;

	public SInstanceOfExpr(BUTree<? extends SExpr> expr, BUTree<? extends SType> type) {
		this.expr = expr;
		this.type = type;
	}

	@Override
	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	public BUTree<? extends SExpr> expr() {
		return expr;
	}

	public SInstanceOfExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SInstanceOfExpr(expr, type);
	}

	public BUTree<? extends SType> type() {
		return type;
	}

	public SInstanceOfExpr withType(BUTree<? extends SType> type) {
		return new SInstanceOfExpr(expr, type);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SInstanceOfExpr> location) {
		return new TDInstanceOfExpr(location);
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
		return TYPE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SInstanceOfExpr state = (SInstanceOfExpr) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (expr != null) result = 37 * result + expr.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SInstanceOfExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SInstanceOfExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SInstanceOfExpr state) {
			return state.expr;
		}

		@Override
		public SInstanceOfExpr doRebuildParentState(SInstanceOfExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SInstanceOfExpr, SType, Type> TYPE = new STypeSafeTraversal<SInstanceOfExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SInstanceOfExpr state) {
			return state.type;
		}

		@Override
		public SInstanceOfExpr doRebuildParentState(SInstanceOfExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR),
			keyword(LToken.InstanceOf),
			child(TYPE)
	);
}
