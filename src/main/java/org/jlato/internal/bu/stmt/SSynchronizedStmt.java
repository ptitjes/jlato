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
import org.jlato.internal.td.stmt.TDSynchronizedStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'synchronized' statement.
 */
public class SSynchronizedStmt extends SNode<SSynchronizedStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'synchronized' statement.
	 *
	 * @param expr  the expression child <code>BUTree</code>.
	 * @param block the block child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'synchronized' statement.
	 */
	public static BUTree<SSynchronizedStmt> make(BUTree<? extends SExpr> expr, BUTree<SBlockStmt> block) {
		return new BUTree<SSynchronizedStmt>(new SSynchronizedStmt(expr, block));
	}

	/**
	 * The expression of this 'synchronized' statement state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * The block of this 'synchronized' statement state.
	 */
	public final BUTree<SBlockStmt> block;

	/**
	 * Constructs a 'synchronized' statement state.
	 *
	 * @param expr  the expression child <code>BUTree</code>.
	 * @param block the block child <code>BUTree</code>.
	 */
	public SSynchronizedStmt(BUTree<? extends SExpr> expr, BUTree<SBlockStmt> block) {
		this.expr = expr;
		this.block = block;
	}

	/**
	 * Returns the kind of this 'synchronized' statement.
	 *
	 * @return the kind of this 'synchronized' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	/**
	 * Replaces the expression of this 'synchronized' statement state.
	 *
	 * @param expr the replacement for the expression of this 'synchronized' statement state.
	 * @return the resulting mutated 'synchronized' statement state.
	 */
	public SSynchronizedStmt withExpr(BUTree<? extends SExpr> expr) {
		return new SSynchronizedStmt(expr, block);
	}

	/**
	 * Replaces the block of this 'synchronized' statement state.
	 *
	 * @param block the replacement for the block of this 'synchronized' statement state.
	 * @return the resulting mutated 'synchronized' statement state.
	 */
	public SSynchronizedStmt withBlock(BUTree<SBlockStmt> block) {
		return new SSynchronizedStmt(expr, block);
	}

	/**
	 * Builds a 'synchronized' statement facade for the specified 'synchronized' statement <code>TDLocation</code>.
	 *
	 * @param location the 'synchronized' statement <code>TDLocation</code>.
	 * @return a 'synchronized' statement facade for the specified 'synchronized' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SSynchronizedStmt> location) {
		return new TDSynchronizedStmt(location);
	}

	/**
	 * Returns the shape for this 'synchronized' statement state.
	 *
	 * @return the shape for this 'synchronized' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'synchronized' statement state.
	 *
	 * @return the first child traversal for this 'synchronized' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this 'synchronized' statement state.
	 *
	 * @return the last child traversal for this 'synchronized' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return BLOCK;
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
		SSynchronizedStmt state = (SSynchronizedStmt) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		if (block == null ? state.block != null : !block.equals(state.block))
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
		if (block != null) result = 37 * result + block.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSynchronizedStmt, SExpr, Expr> EXPR = new STypeSafeTraversal<SSynchronizedStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SSynchronizedStmt state) {
			return state.expr;
		}

		@Override
		public SSynchronizedStmt doRebuildParentState(SSynchronizedStmt state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BLOCK;
		}
	};

	public static STypeSafeTraversal<SSynchronizedStmt, SBlockStmt, BlockStmt> BLOCK = new STypeSafeTraversal<SSynchronizedStmt, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SSynchronizedStmt state) {
			return state.block;
		}

		@Override
		public SSynchronizedStmt doRebuildParentState(SSynchronizedStmt state, BUTree<SBlockStmt> child) {
			return state.withBlock(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Synchronized),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXPR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BLOCK)
	);
}
