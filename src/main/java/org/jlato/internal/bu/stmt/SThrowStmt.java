package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDThrowStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'throw' statement.
 */
public class SThrowStmt extends SNode<SThrowStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'throw' statement.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'throw' statement.
	 */
	public static BUTree<SThrowStmt> make(BUTree<? extends SExpr> expr) {
		return new BUTree<SThrowStmt>(new SThrowStmt(expr));
	}

	/**
	 * The expression of this 'throw' statement state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * Constructs a 'throw' statement state.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 */
	public SThrowStmt(BUTree<? extends SExpr> expr) {
		this.expr = expr;
	}

	/**
	 * Returns the kind of this 'throw' statement.
	 *
	 * @return the kind of this 'throw' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ThrowStmt;
	}

	/**
	 * Replaces the expression of this 'throw' statement state.
	 *
	 * @param expr the replacement for the expression of this 'throw' statement state.
	 * @return the resulting mutated 'throw' statement state.
	 */
	public SThrowStmt withExpr(BUTree<? extends SExpr> expr) {
		return new SThrowStmt(expr);
	}

	/**
	 * Builds a 'throw' statement facade for the specified 'throw' statement <code>TDLocation</code>.
	 *
	 * @param location the 'throw' statement <code>TDLocation</code>.
	 * @return a 'throw' statement facade for the specified 'throw' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SThrowStmt> location) {
		return new TDThrowStmt(location);
	}

	/**
	 * Returns the shape for this 'throw' statement state.
	 *
	 * @return the shape for this 'throw' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'throw' statement state.
	 *
	 * @return the first child traversal for this 'throw' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this 'throw' statement state.
	 *
	 * @return the last child traversal for this 'throw' statement state.
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
		SThrowStmt state = (SThrowStmt) o;
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
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SThrowStmt, SExpr, Expr> EXPR = new STypeSafeTraversal<SThrowStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SThrowStmt state) {
			return state.expr;
		}

		@Override
		public SThrowStmt doRebuildParentState(SThrowStmt state, BUTree<SExpr> child) {
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

	public static final LexicalShape shape = composite(
			keyword(LToken.Throw), child(EXPR), token(LToken.SemiColon)
	);
}
