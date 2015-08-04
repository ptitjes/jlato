package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDSynchronizedStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SSynchronizedStmt extends SNode<SSynchronizedStmt> implements SStmt {

	public static BUTree<SSynchronizedStmt> make(BUTree<? extends SExpr> expr, BUTree<SBlockStmt> block) {
		return new BUTree<SSynchronizedStmt>(new SSynchronizedStmt(expr, block));
	}

	public final BUTree<? extends SExpr> expr;

	public final BUTree<SBlockStmt> block;

	public SSynchronizedStmt(BUTree<? extends SExpr> expr, BUTree<SBlockStmt> block) {
		this.expr = expr;
		this.block = block;
	}

	@Override
	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	public BUTree<? extends SExpr> expr() {
		return expr;
	}

	public SSynchronizedStmt withExpr(BUTree<? extends SExpr> expr) {
		return new SSynchronizedStmt(expr, block);
	}

	public BUTree<SBlockStmt> block() {
		return block;
	}

	public SSynchronizedStmt withBlock(BUTree<SBlockStmt> block) {
		return new SSynchronizedStmt(expr, block);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SSynchronizedStmt> location) {
		return new TDSynchronizedStmt(location);
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
		return BLOCK;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SSynchronizedStmt state = (SSynchronizedStmt) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		if (block == null ? state.block != null : !block.equals(state.block))
			return false;
		return true;
	}

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
