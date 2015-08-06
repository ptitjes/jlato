package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDSwitchStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.SwitchStmt_AfterSwitchKeyword;

public class SSwitchStmt extends SNode<SSwitchStmt> implements SStmt {

	public static BUTree<SSwitchStmt> make(BUTree<? extends SExpr> selector, BUTree<SNodeList> cases) {
		return new BUTree<SSwitchStmt>(new SSwitchStmt(selector, cases));
	}

	public final BUTree<? extends SExpr> selector;

	public final BUTree<SNodeList> cases;

	public SSwitchStmt(BUTree<? extends SExpr> selector, BUTree<SNodeList> cases) {
		this.selector = selector;
		this.cases = cases;
	}

	@Override
	public Kind kind() {
		return Kind.SwitchStmt;
	}

	public BUTree<? extends SExpr> selector() {
		return selector;
	}

	public SSwitchStmt withSelector(BUTree<? extends SExpr> selector) {
		return new SSwitchStmt(selector, cases);
	}

	public BUTree<SNodeList> cases() {
		return cases;
	}

	public SSwitchStmt withCases(BUTree<SNodeList> cases) {
		return new SSwitchStmt(selector, cases);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SSwitchStmt> location) {
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
		public BUTree<?> doTraverse(SSwitchStmt state) {
			return state.selector;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, BUTree<SExpr> child) {
			return state.withSelector(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CASES;
		}
	};

	public static STypeSafeTraversal<SSwitchStmt, SNodeList, NodeList<SwitchCase>> CASES = new STypeSafeTraversal<SSwitchStmt, SNodeList, NodeList<SwitchCase>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchStmt state) {
			return state.cases;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, BUTree<SNodeList> child) {
			return state.withCases(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SELECTOR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			token(LToken.BraceLeft)
					.withSpacingAfter(newLine())
					.withIndentationAfter(indent(BLOCK)),
			child(CASES, SSwitchCase.listShape),
			alternative(childIs(CASES, not(empty())),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
							.withSpacingBefore(newLine()),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(BLOCK))
			)
	);
}
