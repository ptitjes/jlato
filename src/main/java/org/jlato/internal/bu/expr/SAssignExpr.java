package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDAssignExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SAssignExpr extends SNode<SAssignExpr> implements SExpr {

	public static BUTree<SAssignExpr> make(BUTree<? extends SExpr> target, AssignOp op, BUTree<? extends SExpr> value) {
		return new BUTree<SAssignExpr>(new SAssignExpr(target, op, value));
	}

	public final BUTree<? extends SExpr> target;

	public final AssignOp op;

	public final BUTree<? extends SExpr> value;

	public SAssignExpr(BUTree<? extends SExpr> target, AssignOp op, BUTree<? extends SExpr> value) {
		this.target = target;
		this.op = op;
		this.value = value;
	}

	@Override
	public Kind kind() {
		return Kind.AssignExpr;
	}

	public SAssignExpr withTarget(BUTree<? extends SExpr> target) {
		return new SAssignExpr(target, op, value);
	}

	public SAssignExpr withOp(AssignOp op) {
		return new SAssignExpr(target, op, value);
	}

	public SAssignExpr withValue(BUTree<? extends SExpr> value) {
		return new SAssignExpr(target, op, value);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SAssignExpr> location) {
		return new TDAssignExpr(location);
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
		return TARGET;
	}

	@Override
	public STraversal lastChild() {
		return VALUE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SAssignExpr state = (SAssignExpr) o;
		if (target == null ? state.target != null : !target.equals(state.target))
			return false;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (value == null ? state.value != null : !value.equals(state.value))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (target != null) result = 37 * result + target.hashCode();
		if (op != null) result = 37 * result + op.hashCode();
		if (value != null) result = 37 * result + value.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAssignExpr, SExpr, Expr> TARGET = new STypeSafeTraversal<SAssignExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SAssignExpr state) {
			return state.target;
		}

		@Override
		public SAssignExpr doRebuildParentState(SAssignExpr state, BUTree<SExpr> child) {
			return state.withTarget(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return VALUE;
		}
	};

	public static STypeSafeTraversal<SAssignExpr, SExpr, Expr> VALUE = new STypeSafeTraversal<SAssignExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SAssignExpr state) {
			return state.value;
		}

		@Override
		public SAssignExpr doRebuildParentState(SAssignExpr state, BUTree<SExpr> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TARGET;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static STypeSafeProperty<SAssignExpr, AssignOp> OP = new STypeSafeProperty<SAssignExpr, AssignOp>() {

		@Override
		public AssignOp doRetrieve(SAssignExpr state) {
			return state.op;
		}

		@Override
		public SAssignExpr doRebuildParentState(SAssignExpr state, AssignOp value) {
			return state.withOp(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(TARGET),
			token(new LSToken.Provider() {
				public LToken tokenFor(BUTree tree) {
					final AssignOp op = ((SAssignExpr) tree.state).op;
					switch (op) {
						case Normal:
							return LToken.Assign;
						case Plus:
							return LToken.AssignPlus;
						case Minus:
							return LToken.AssignMinus;
						case Times:
							return LToken.AssignTimes;
						case Divide:
							return LToken.AssignDivide;
						case And:
							return LToken.AssignAnd;
						case Or:
							return LToken.AssignOr;
						case XOr:
							return LToken.AssignXOr;
						case Remainder:
							return LToken.AssignRemainder;
						case LeftShift:
							return LToken.AssignLShift;
						case RightSignedShift:
							return LToken.AssignRSignedShift;
						case RightUnsignedShift:
							return LToken.AssignRUnsignedShift;
						default:
							// Can't happen by definition of enum
							throw new IllegalStateException();
					}
				}
			}).withSpacing(space(), space()),
			child(VALUE)
	);
}
