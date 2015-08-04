package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDReturnStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SReturnStmt extends SNodeState<SReturnStmt> implements SStmt {

	public static STree<SReturnStmt> make(STree<SNodeOptionState> expr) {
		return new STree<SReturnStmt>(new SReturnStmt(expr));
	}

	public final STree<SNodeOptionState> expr;

	public SReturnStmt(STree<SNodeOptionState> expr) {
		this.expr = expr;
	}

	@Override
	public Kind kind() {
		return Kind.ReturnStmt;
	}

	public STree<SNodeOptionState> expr() {
		return expr;
	}

	public SReturnStmt withExpr(STree<SNodeOptionState> expr) {
		return new SReturnStmt(expr);
	}

	@Override
	protected Tree doInstantiate(SLocation<SReturnStmt> location) {
		return new TDReturnStmt(location);
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
		return EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SReturnStmt state = (SReturnStmt) o;
		if (!expr.equals(state.expr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SReturnStmt, SNodeOptionState, NodeOption<Expr>> EXPR = new STypeSafeTraversal<SReturnStmt, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SReturnStmt state) {
			return state.expr;
		}

		@Override
		public SReturnStmt doRebuildParentState(SReturnStmt state, STree<SNodeOptionState> child) {
			return state.withExpr(child);
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
			token(LToken.Return),
			child(EXPR, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
