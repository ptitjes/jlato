package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDSynchronizedStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SSynchronizedStmt extends SNodeState<SSynchronizedStmt> implements SStmt {

	public static STree<SSynchronizedStmt> make(STree<? extends SExpr> expr, STree<SBlockStmt> block) {
		return new STree<SSynchronizedStmt>(new SSynchronizedStmt(expr, block));
	}

	public final STree<? extends SExpr> expr;

	public final STree<SBlockStmt> block;

	public SSynchronizedStmt(STree<? extends SExpr> expr, STree<SBlockStmt> block) {
		this.expr = expr;
		this.block = block;
	}

	@Override
	public Kind kind() {
		return Kind.SynchronizedStmt;
	}

	public STree<? extends SExpr> expr() {
		return expr;
	}

	public SSynchronizedStmt withExpr(STree<? extends SExpr> expr) {
		return new SSynchronizedStmt(expr, block);
	}

	public STree<SBlockStmt> block() {
		return block;
	}

	public SSynchronizedStmt withBlock(STree<SBlockStmt> block) {
		return new SSynchronizedStmt(expr, block);
	}

	@Override
	protected Tree doInstantiate(SLocation<SSynchronizedStmt> location) {
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
		public STree<?> doTraverse(SSynchronizedStmt state) {
			return state.expr;
		}

		@Override
		public SSynchronizedStmt doRebuildParentState(SSynchronizedStmt state, STree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BLOCK;
		}
	};

	public static STypeSafeTraversal<SSynchronizedStmt, SBlockStmt, BlockStmt> BLOCK = new STypeSafeTraversal<SSynchronizedStmt, SBlockStmt, BlockStmt>() {

		@Override
		public STree<?> doTraverse(SSynchronizedStmt state) {
			return state.block;
		}

		@Override
		public SSynchronizedStmt doRebuildParentState(SSynchronizedStmt state, STree<SBlockStmt> child) {
			return state.withBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
