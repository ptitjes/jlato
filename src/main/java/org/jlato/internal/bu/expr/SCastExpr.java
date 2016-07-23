package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDCastExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a cast expression.
 */
public class SCastExpr extends SNode<SCastExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new cast expression.
	 *
	 * @param type the type child <code>BUTree</code>.
	 * @param expr the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a cast expression.
	 */
	public static BUTree<SCastExpr> make(BUTree<? extends SType> type, BUTree<? extends SExpr> expr) {
		return new BUTree<SCastExpr>(new SCastExpr(type, expr));
	}

	/**
	 * The type of this cast expression state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The expression of this cast expression state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * Constructs a cast expression state.
	 *
	 * @param type the type child <code>BUTree</code>.
	 * @param expr the expression child <code>BUTree</code>.
	 */
	public SCastExpr(BUTree<? extends SType> type, BUTree<? extends SExpr> expr) {
		this.type = type;
		this.expr = expr;
	}

	/**
	 * Returns the kind of this cast expression.
	 *
	 * @return the kind of this cast expression.
	 */
	@Override
	public Kind kind() {
		return Kind.CastExpr;
	}

	/**
	 * Replaces the type of this cast expression state.
	 *
	 * @param type the replacement for the type of this cast expression state.
	 * @return the resulting mutated cast expression state.
	 */
	public SCastExpr withType(BUTree<? extends SType> type) {
		return new SCastExpr(type, expr);
	}

	/**
	 * Replaces the expression of this cast expression state.
	 *
	 * @param expr the replacement for the expression of this cast expression state.
	 * @return the resulting mutated cast expression state.
	 */
	public SCastExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SCastExpr(type, expr);
	}

	/**
	 * Builds a cast expression facade for the specified cast expression <code>TDLocation</code>.
	 *
	 * @param location the cast expression <code>TDLocation</code>.
	 * @return a cast expression facade for the specified cast expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SCastExpr> location) {
		return new TDCastExpr(location);
	}

	/**
	 * Returns the shape for this cast expression state.
	 *
	 * @return the shape for this cast expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this cast expression state.
	 *
	 * @return the first child traversal for this cast expression state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	/**
	 * Returns the last child traversal for this cast expression state.
	 *
	 * @return the last child traversal for this cast expression state.
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
		SCastExpr state = (SCastExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
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
