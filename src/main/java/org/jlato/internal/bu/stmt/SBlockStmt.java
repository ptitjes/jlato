package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDBlockStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SBlockStmt extends SNodeState<SBlockStmt> implements SStmt {

	public static STree<SBlockStmt> make(STree<SNodeListState> stmts) {
		return new STree<SBlockStmt>(new SBlockStmt(stmts));
	}

	public final STree<SNodeListState> stmts;

	public SBlockStmt(STree<SNodeListState> stmts) {
		this.stmts = stmts;
	}

	@Override
	public Kind kind() {
		return Kind.BlockStmt;
	}

	public STree<SNodeListState> stmts() {
		return stmts;
	}

	public SBlockStmt withStmts(STree<SNodeListState> stmts) {
		return new SBlockStmt(stmts);
	}

	@Override
	protected Tree doInstantiate(SLocation<SBlockStmt> location) {
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
		public STree<?> doTraverse(SBlockStmt state) {
			return state.stmts;
		}

		@Override
		public SBlockStmt doRebuildParentState(SBlockStmt state, STree<SNodeListState> child) {
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
