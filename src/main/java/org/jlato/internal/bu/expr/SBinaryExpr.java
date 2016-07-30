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
import org.jlato.internal.td.expr.TDBinaryExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.BinaryOp;
import org.jlato.tree.expr.Expr;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a binary expression.
 */
public class SBinaryExpr extends SNode<SBinaryExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new binary expression.
	 *
	 * @param left  the left child <code>BUTree</code>.
	 * @param op    the op child <code>BUTree</code>.
	 * @param right the right child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a binary expression.
	 */
	public static BUTree<SBinaryExpr> make(BUTree<? extends SExpr> left, BinaryOp op, BUTree<? extends SExpr> right) {
		return new BUTree<SBinaryExpr>(new SBinaryExpr(left, op, right));
	}

	/**
	 * The left of this binary expression state.
	 */
	public final BUTree<? extends SExpr> left;

	/**
	 * The op of this binary expression state.
	 */
	public final BinaryOp op;

	/**
	 * The right of this binary expression state.
	 */
	public final BUTree<? extends SExpr> right;

	/**
	 * Constructs a binary expression state.
	 *
	 * @param left  the left child <code>BUTree</code>.
	 * @param op    the op child <code>BUTree</code>.
	 * @param right the right child <code>BUTree</code>.
	 */
	public SBinaryExpr(BUTree<? extends SExpr> left, BinaryOp op, BUTree<? extends SExpr> right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	/**
	 * Returns the kind of this binary expression.
	 *
	 * @return the kind of this binary expression.
	 */
	@Override
	public Kind kind() {
		return Kind.BinaryExpr;
	}

	/**
	 * Replaces the left of this binary expression state.
	 *
	 * @param left the replacement for the left of this binary expression state.
	 * @return the resulting mutated binary expression state.
	 */
	public SBinaryExpr withLeft(BUTree<? extends SExpr> left) {
		return new SBinaryExpr(left, op, right);
	}

	/**
	 * Replaces the op of this binary expression state.
	 *
	 * @param op the replacement for the op of this binary expression state.
	 * @return the resulting mutated binary expression state.
	 */
	public SBinaryExpr withOp(BinaryOp op) {
		return new SBinaryExpr(left, op, right);
	}

	/**
	 * Replaces the right of this binary expression state.
	 *
	 * @param right the replacement for the right of this binary expression state.
	 * @return the resulting mutated binary expression state.
	 */
	public SBinaryExpr withRight(BUTree<? extends SExpr> right) {
		return new SBinaryExpr(left, op, right);
	}

	/**
	 * Builds a binary expression facade for the specified binary expression <code>TDLocation</code>.
	 *
	 * @param location the binary expression <code>TDLocation</code>.
	 * @return a binary expression facade for the specified binary expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SBinaryExpr> location) {
		return new TDBinaryExpr(location);
	}

	/**
	 * Returns the shape for this binary expression state.
	 *
	 * @return the shape for this binary expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this binary expression state.
	 *
	 * @return the properties for this binary expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(OP);
	}

	/**
	 * Returns the first child traversal for this binary expression state.
	 *
	 * @return the first child traversal for this binary expression state.
	 */
	@Override
	public STraversal firstChild() {
		return LEFT;
	}

	/**
	 * Returns the last child traversal for this binary expression state.
	 *
	 * @return the last child traversal for this binary expression state.
	 */
	@Override
	public STraversal lastChild() {
		return RIGHT;
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
		SBinaryExpr state = (SBinaryExpr) o;
		if (left == null ? state.left != null : !left.equals(state.left))
			return false;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (right == null ? state.right != null : !right.equals(state.right))
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
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
		public STraversal leftSibling(STree state) {
			return LEFT;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
