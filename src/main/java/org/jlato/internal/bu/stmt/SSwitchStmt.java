package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDSwitchStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.SwitchCase;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SSwitchStmt extends SNodeState<SSwitchStmt> implements SStmt {

	public static STree<SSwitchStmt> make(STree<? extends SExpr> selector, STree<SNodeListState> cases) {
		return new STree<SSwitchStmt>(new SSwitchStmt(selector, cases));
	}

	public final STree<? extends SExpr> selector;

	public final STree<SNodeListState> cases;

	public SSwitchStmt(STree<? extends SExpr> selector, STree<SNodeListState> cases) {
		this.selector = selector;
		this.cases = cases;
	}

	@Override
	public Kind kind() {
		return Kind.SwitchStmt;
	}

	public STree<? extends SExpr> selector() {
		return selector;
	}

	public SSwitchStmt withSelector(STree<? extends SExpr> selector) {
		return new SSwitchStmt(selector, cases);
	}

	public STree<SNodeListState> cases() {
		return cases;
	}

	public SSwitchStmt withCases(STree<SNodeListState> cases) {
		return new SSwitchStmt(selector, cases);
	}

	@Override
	protected Tree doInstantiate(SLocation<SSwitchStmt> location) {
		return new TDSwitchStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return SELECTOR;
	}

	@Override
	public STraversal lastChild() {
		return CASES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SSwitchStmt state = (SSwitchStmt) o;
		if (selector == null ? state.selector != null : !selector.equals(state.selector))
			return false;
		if (!cases.equals(state.cases))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (selector != null) result = 37 * result + selector.hashCode();
		result = 37 * result + cases.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSwitchStmt, SExpr, Expr> SELECTOR = new STypeSafeTraversal<SSwitchStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SSwitchStmt state) {
			return state.selector;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, STree<SExpr> child) {
			return state.withSelector(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CASES;
		}
	};

	public static STypeSafeTraversal<SSwitchStmt, SNodeListState, NodeList<SwitchCase>> CASES = new STypeSafeTraversal<SSwitchStmt, SNodeListState, NodeList<SwitchCase>>() {

		@Override
		public STree<?> doTraverse(SSwitchStmt state) {
			return state.cases;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, STree<SNodeListState> child) {
			return state.withCases(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SELECTOR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			alternative(childIs(CASES, not(empty())),
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							child(CASES, SSwitchCase.listShape),
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
			)
	);
}
