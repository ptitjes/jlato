package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDParenthesizedExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SParenthesizedExpr extends SNodeState<SParenthesizedExpr> implements SExpr {

	public static STree<SParenthesizedExpr> make(STree<? extends SExpr> inner) {
		return new STree<SParenthesizedExpr>(new SParenthesizedExpr(inner));
	}

	public final STree<? extends SExpr> inner;

	public SParenthesizedExpr(STree<? extends SExpr> inner) {
		this.inner = inner;
	}

	@Override
	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	public STree<? extends SExpr> inner() {
		return inner;
	}

	public SParenthesizedExpr withInner(STree<? extends SExpr> inner) {
		return new SParenthesizedExpr(inner);
	}

	@Override
	protected Tree doInstantiate(SLocation<SParenthesizedExpr> location) {
		return new TDParenthesizedExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return INNER;
	}

	@Override
	public STraversal lastChild() {
		return INNER;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SParenthesizedExpr state = (SParenthesizedExpr) o;
		if (inner == null ? state.inner != null : !inner.equals(state.inner))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (inner != null) result = 37 * result + inner.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SParenthesizedExpr, SExpr, Expr> INNER = new STypeSafeTraversal<SParenthesizedExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SParenthesizedExpr state) {
			return state.inner;
		}

		@Override
		public SParenthesizedExpr doRebuildParentState(SParenthesizedExpr state, STree<SExpr> child) {
			return state.withInner(child);
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

	public static final LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(INNER), token(LToken.ParenthesisRight)
	);
}
