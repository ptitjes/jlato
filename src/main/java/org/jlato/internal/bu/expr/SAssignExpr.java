package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDAssignExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AssignOp;
import org.jlato.tree.expr.Expr;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an assignment expression.
 */
public class SAssignExpr extends SNode<SAssignExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new assignment expression.
	 *
	 * @param target the target child <code>BUTree</code>.
	 * @param op     the op child <code>BUTree</code>.
	 * @param value  the value child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an assignment expression.
	 */
	public static BUTree<SAssignExpr> make(BUTree<? extends SExpr> target, AssignOp op, BUTree<? extends SExpr> value) {
		return new BUTree<SAssignExpr>(new SAssignExpr(target, op, value));
	}

	/**
	 * The target of this assignment expression state.
	 */
	public final BUTree<? extends SExpr> target;

	/**
	 * The op of this assignment expression state.
	 */
	public final AssignOp op;

	/**
	 * The value of this assignment expression state.
	 */
	public final BUTree<? extends SExpr> value;

	/**
	 * Constructs an assignment expression state.
	 *
	 * @param target the target child <code>BUTree</code>.
	 * @param op     the op child <code>BUTree</code>.
	 * @param value  the value child <code>BUTree</code>.
	 */
	public SAssignExpr(BUTree<? extends SExpr> target, AssignOp op, BUTree<? extends SExpr> value) {
		this.target = target;
		this.op = op;
		this.value = value;
	}

	/**
	 * Returns the kind of this assignment expression.
	 *
	 * @return the kind of this assignment expression.
	 */
	@Override
	public Kind kind() {
		return Kind.AssignExpr;
	}

	/**
	 * Replaces the target of this assignment expression state.
	 *
	 * @param target the replacement for the target of this assignment expression state.
	 * @return the resulting mutated assignment expression state.
	 */
	public SAssignExpr withTarget(BUTree<? extends SExpr> target) {
		return new SAssignExpr(target, op, value);
	}

	/**
	 * Replaces the op of this assignment expression state.
	 *
	 * @param op the replacement for the op of this assignment expression state.
	 * @return the resulting mutated assignment expression state.
	 */
	public SAssignExpr withOp(AssignOp op) {
		return new SAssignExpr(target, op, value);
	}

	/**
	 * Replaces the value of this assignment expression state.
	 *
	 * @param value the replacement for the value of this assignment expression state.
	 * @return the resulting mutated assignment expression state.
	 */
	public SAssignExpr withValue(BUTree<? extends SExpr> value) {
		return new SAssignExpr(target, op, value);
	}

	/**
	 * Builds an assignment expression facade for the specified assignment expression <code>TDLocation</code>.
	 *
	 * @param location the assignment expression <code>TDLocation</code>.
	 * @return an assignment expression facade for the specified assignment expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SAssignExpr> location) {
		return new TDAssignExpr(location);
	}

	/**
	 * Returns the shape for this assignment expression state.
	 *
	 * @return the shape for this assignment expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this assignment expression state.
	 *
	 * @return the properties for this assignment expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(OP);
	}

	/**
	 * Returns the first child traversal for this assignment expression state.
	 *
	 * @return the first child traversal for this assignment expression state.
	 */
	@Override
	public STraversal firstChild() {
		return TARGET;
	}

	/**
	 * Returns the last child traversal for this assignment expression state.
	 *
	 * @return the last child traversal for this assignment expression state.
	 */
	@Override
	public STraversal lastChild() {
		return VALUE;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SAssignExpr state = (SAssignExpr) o;
		if (target == null ? state.target != null : !target.equals(state.target))
			return false;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (value == null ? state.value != null : !value.equals(state.value))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
