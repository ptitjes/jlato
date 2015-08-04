package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDBlockStmt;
import org.jlato.tree.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;

public class SBlockStmt extends SNodeState<SBlockStmt> implements SStmt {

	public static BUTree<SBlockStmt> make(BUTree<SNodeListState> stmts) {
		return new BUTree<SBlockStmt>(new SBlockStmt(stmts));
	}

	public final BUTree<SNodeListState> stmts;

	public SBlockStmt(BUTree<SNodeListState> stmts) {
		this.stmts = stmts;
	}

	@Override
	public Kind kind() {
		return Kind.BlockStmt;
	}

	public BUTree<SNodeListState> stmts() {
		return stmts;
	}

	public SBlockStmt withStmts(BUTree<SNodeListState> stmts) {
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

	public static STypeSafeTraversal<SBlockStmt, SNodeListState, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SBlockStmt, SNodeListState, NodeList<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SBlockStmt state) {
			return state.stmts;
		}

		@Override
		public SBlockStmt doRebuildParentState(SBlockStmt state, BUTree<SNodeListState> child) {
			return state.withStmts(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = alternative(childIs(STMTS, not(empty())),
			composite(
					token(LToken.BraceLeft)
							.withSpacingAfter(newLine())
							.withIndentationAfter(indent(BLOCK)),
					child(STMTS, listShape),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
							.withSpacingBefore(newLine())
			),
			composite(
					token(LToken.BraceLeft)
							.withSpacingAfter(newLine())
							.withIndentationAfter(indent(BLOCK)),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
			)
	);
}
