package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDUnaryExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SUnaryExpr extends SNodeState<SUnaryExpr> implements SExpr {

	public static STree<SUnaryExpr> make(UnaryOp op, STree<? extends SExpr> expr) {
		return new STree<SUnaryExpr>(new SUnaryExpr(op, expr));
	}

	public final UnaryOp op;

	public final STree<? extends SExpr> expr;

	public SUnaryExpr(UnaryOp op, STree<? extends SExpr> expr) {
		this.op = op;
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.UnaryExpr;
	}

	public UnaryOp op() {
		return op;
	}

	public SUnaryExpr withOp(UnaryOp op) {
		return new SUnaryExpr(op, expr);
	}

	public STree<? extends SExpr> expr() {
		return expr;
	}

	public SUnaryExpr withExpr(STree<? extends SExpr> expr) {
		return new SUnaryExpr(op, expr);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SUnaryExpr> location) {
		return new TDUnaryExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(OP);
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
		SUnaryExpr state = (SUnaryExpr) o;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (op != null) result = 37 * result + op.hashCode();
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SUnaryExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SUnaryExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SUnaryExpr state) {
			return state.expr;
		}

		@Override
		public SUnaryExpr doRebuildParentState(SUnaryExpr state, STree<SExpr> child) {
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

	public static STypeSafeProperty<SUnaryExpr, UnaryOp> OP = new STypeSafeProperty<SUnaryExpr, UnaryOp>() {

		@Override
		public UnaryOp doRetrieve(SUnaryExpr state) {
			return state.op;
		}

		@Override
		public SUnaryExpr doRebuildParentState(SUnaryExpr state, UnaryOp value) {
			return state.withOp(value);
		}
	};

	public static final LexicalShape opShape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			final UnaryOp op = ((SUnaryExpr) tree.state).op;
			switch (op) {
				case Positive:
					return LToken.Plus;
				case Negative:
					return LToken.Minus;
				case PreIncrement:
					return LToken.Increment;
				case PreDecrement:
					return LToken.Decrement;
				case Not:
					return LToken.Not;
				case Inverse:
					return LToken.Inverse;
				case PostIncrement:
					return LToken.Increment;
				case PostDecrement:
					return LToken.Decrement;
				default:
					// Can't happen by definition of enum
					throw new IllegalStateException();
			}
		}
	});

	public static final LexicalShape shape = alternative(new LSCondition() {
		public boolean test(STree tree) {
			final UnaryOp op = ((SUnaryExpr) tree.state).op;
			return op.isPrefix();
		}
	}, composite(opShape, child(EXPR)), composite(child(EXPR), opShape));
}
