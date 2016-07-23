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
import org.jlato.internal.td.expr.TDUnaryExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.UnaryOp;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an unary expression.
 */
public class SUnaryExpr extends SNode<SUnaryExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new unary expression.
	 *
	 * @param op   the op child <code>BUTree</code>.
	 * @param expr the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an unary expression.
	 */
	public static BUTree<SUnaryExpr> make(UnaryOp op, BUTree<? extends SExpr> expr) {
		return new BUTree<SUnaryExpr>(new SUnaryExpr(op, expr));
	}

	/**
	 * The op of this unary expression state.
	 */
	public final UnaryOp op;

	/**
	 * The expression of this unary expression state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * Constructs an unary expression state.
	 *
	 * @param op   the op child <code>BUTree</code>.
	 * @param expr the expression child <code>BUTree</code>.
	 */
	public SUnaryExpr(UnaryOp op, BUTree<? extends SExpr> expr) {
		this.op = op;
		this.expr = expr;
	}

	/**
	 * Returns the kind of this unary expression.
	 *
	 * @return the kind of this unary expression.
	 */
	@Override
	public Kind kind() {
		return Kind.UnaryExpr;
	}

	/**
	 * Replaces the op of this unary expression state.
	 *
	 * @param op the replacement for the op of this unary expression state.
	 * @return the resulting mutated unary expression state.
	 */
	public SUnaryExpr withOp(UnaryOp op) {
		return new SUnaryExpr(op, expr);
	}

	/**
	 * Replaces the expression of this unary expression state.
	 *
	 * @param expr the replacement for the expression of this unary expression state.
	 * @return the resulting mutated unary expression state.
	 */
	public SUnaryExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SUnaryExpr(op, expr);
	}

	/**
	 * Builds an unary expression facade for the specified unary expression <code>TDLocation</code>.
	 *
	 * @param location the unary expression <code>TDLocation</code>.
	 * @return an unary expression facade for the specified unary expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SUnaryExpr> location) {
		return new TDUnaryExpr(location);
	}

	/**
	 * Returns the shape for this unary expression state.
	 *
	 * @return the shape for this unary expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this unary expression state.
	 *
	 * @return the properties for this unary expression state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(OP);
	}

	/**
	 * Returns the first child traversal for this unary expression state.
	 *
	 * @return the first child traversal for this unary expression state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this unary expression state.
	 *
	 * @return the last child traversal for this unary expression state.
	 */
	@Override
	public STraversal lastChild() {
		return EXPR;
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
		SUnaryExpr state = (SUnaryExpr) o;
		if (op == null ? state.op != null : !op.equals(state.op))
			return false;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
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
		if (op != null) result = 37 * result + op.hashCode();
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SUnaryExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SUnaryExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SUnaryExpr state) {
			return state.expr;
		}

		@Override
		public SUnaryExpr doRebuildParentState(SUnaryExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
		public LToken tokenFor(BUTree tree) {
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
		public boolean test(BUTree tree) {
			final UnaryOp op = ((SUnaryExpr) tree.state).op;
			return op.isPrefix();
		}
	}, composite(opShape, child(EXPR)), composite(child(EXPR), opShape));
}
