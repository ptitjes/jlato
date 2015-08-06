package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDBlockStmt;
import org.jlato.tree.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.space;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;

public class SBlockStmt extends SNode<SBlockStmt> implements SStmt {

	public static BUTree<SBlockStmt> make(BUTree<SNodeList> stmts) {
		return new BUTree<SBlockStmt>(new SBlockStmt(stmts));
	}

	public final BUTree<SNodeList> stmts;

	public SBlockStmt(BUTree<SNodeList> stmts) {
		this.stmts = stmts;
	}

	@Override
	public Kind kind() {
		return Kind.BlockStmt;
	}

	public BUTree<SNodeList> stmts() {
		return stmts;
	}

	public SBlockStmt withStmts(BUTree<SNodeList> stmts) {
		return new SBlockStmt(stmts);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SBlockStmt> location) {
		return new TDBlockStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return STMTS;
	}

	@Override
	public STraversal lastChild() {
		return STMTS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SBlockStmt state = (SBlockStmt) o;
		if (!stmts.equals(state.stmts))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + stmts.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SBlockStmt, SNodeList, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SBlockStmt, SNodeList, NodeList<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SBlockStmt state) {
			return state.stmts;
		}

		@Override
		public SBlockStmt doRebuildParentState(SBlockStmt state, BUTree<SNodeList> child) {
			return state.withStmts(child);
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
			token(LToken.BraceLeft)
					.withSpacingAfter(newLine())
					.withIndentationAfter(indent(BLOCK)),
			child(STMTS, listShape),
			alternative(childIs(STMTS, not(empty())),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
							.withSpacingBefore(newLine()),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
			)
	);
}
