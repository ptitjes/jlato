package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDBinaryExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SBinaryExpr extends SNodeState<SBinaryExpr> implements SExpr {

	public static BUTree<SBinaryExpr> make(BUTree<? extends SExpr> left, BinaryOp op, BUTree<? extends SExpr> right) {
		return new BUTree<SBinaryExpr>(new SBinaryExpr(left, op, right));
	}

	public final BUTree<? extends SExpr> left;

	public final BinaryOp op;

	public final BUTree<? extends SExpr> right;

	public SBinaryExpr(BUTree<? extends SExpr> left, BinaryOp op, BUTree<? extends SExpr> right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public Kind kind() {
		return Kind.BinaryExpr;
	}

	public BUTree<? extends SExpr> left() {
		return left;
	}

	public SBinaryExpr withLeft(BUTree<? extends SExpr> left) {
		return new SBinaryExpr(left, op, right);
	}

	public BinaryOp op() {
		return op;
	}

	public SBinaryExpr withOp(BinaryOp op) {
		return new SBinaryExpr(left, op, right);
	}

	public BUTree<? extends SExpr> right() {
		return right;
	}

	public SBinaryExpr withRight(BUTree<? extends SExpr> right) {
		return new SBinaryExpr(left, op, right);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SBinaryExpr> location) {
		return new TDBinaryExpr(location);
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
		return LEFT;
	}

	@Override
	public STraversal lastChild() {
		return RIGHT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SBinaryExpr state = (SBinaryExpr) o;
		if (left == null ? state.left != null : !left.equals(state.left))
			return false;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (right == null ? state.right != null : !right.equals(state.right))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (left != null) result = 37 * result + left.hashCode();
		if (op != null) result = 37 * result + op.hashCode();
		if (right != null) result = 37 * result + right.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SBinaryExpr, SExpr, Expr> LEFT = new STypeSafeTraversal<SBinaryExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SBinaryExpr state) {
			return state.left;
		}

		@Override
		public SBinaryExpr doRebuildParentState(SBinaryExpr state, BUTree<SExpr> child) {
			return state.withLeft(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return RIGHT;
		}
	};

	public static STypeSafeTraversal<SBinaryExpr, SExpr, Expr> RIGHT = new STypeSafeTraversal<SBinaryExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SBinaryExpr state) {
			return state.right;
		}

		@Override
		public SBinaryExpr doRebuildParentState(SBinaryExpr state, BUTree<SExpr> child) {
			return state.withRight(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return LEFT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static STypeSafeProperty<SBinaryExpr, BinaryOp> OP = new STypeSafeProperty<SBinaryExpr, BinaryOp>() {

		@Override
		public BinaryOp doRetrieve(SBinaryExpr state) {
			return state.op;
		}

		@Override
		public SBinaryExpr doRebuildParentState(SBinaryExpr state, BinaryOp value) {
			return state.withOp(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(LEFT),
			token(new LSToken.Provider() {
				public LToken tokenFor(BUTree tree) {
					final BinaryOp op = ((SBinaryExpr) tree.state).op;
					switch (op) {
						case Or:
							return LToken.Or;
						case And:
							return LToken.And;
						case BinOr:
							return LToken.BinOr;
						case BinAnd:
							return LToken.BinAnd;
						case XOr:
							return LToken.XOr;
						case Equal:
							return LToken.Equal;
						case NotEqual:
							return LToken.NotEqual;
						case Less:
							return LToken.Less;
						case Greater:
							return LToken.Greater;
						case LessOrEqual:
							return LToken.LessOrEqual;
						case GreaterOrEqual:
							return LToken.GreaterOrEqual;
						case LeftShift:
							return LToken.LShift;
						case RightSignedShift:
							return LToken.RSignedShift;
						case RightUnsignedShift:
							return LToken.RUnsignedShift;
						case Plus:
							return LToken.Plus;
						case Minus:
							return LToken.Minus;
						case Times:
							return LToken.Times;
						case Divide:
							return LToken.Divide;
						case Remainder:
							return LToken.Remainder;
						default:
							// Can't happen by definition of enum
							throw new IllegalStateException();
					}
				}
			}).withSpacing(space(), space()),
			child(RIGHT)
	);
}
