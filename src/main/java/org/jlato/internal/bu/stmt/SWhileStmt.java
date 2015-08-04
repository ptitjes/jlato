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
import org.jlato.internal.td.stmt.TDWhileStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SWhileStmt extends SNodeState<SWhileStmt> implements SStmt {

	public static STree<SWhileStmt> make(STree<? extends SExpr> condition, STree<? extends SStmt> body) {
		return new STree<SWhileStmt>(new SWhileStmt(condition, body));
	}

	public final STree<? extends SExpr> condition;

	public final STree<? extends SStmt> body;

	public SWhileStmt(STree<? extends SExpr> condition, STree<? extends SStmt> body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.WhileStmt;
	}

	public STree<? extends SExpr> condition() {
		return condition;
	}

	public SWhileStmt withCondition(STree<? extends SExpr> condition) {
		return new SWhileStmt(condition, body);
	}

	public STree<? extends SStmt> body() {
		return body;
	}

	public SWhileStmt withBody(STree<? extends SStmt> body) {
		return new SWhileStmt(condition, body);
	}

	@Override
	protected Tree doInstantiate(SLocation<SWhileStmt> location) {
		return new TDWhileStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return CONDITION;
	}

	@Override
	public STraversal lastChild() {
		return BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SWhileStmt state = (SWhileStmt) o;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (condition != null) result = 37 * result + condition.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SWhileStmt, SExpr, Expr> CONDITION = new STypeSafeTraversal<SWhileStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SWhileStmt state) {
			return state.condition;
		}

		@Override
		public SWhileStmt doRebuildParentState(SWhileStmt state, STree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SWhileStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SWhileStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SWhileStmt state) {
			return state.body;
		}

		@Override
		public SWhileStmt doRebuildParentState(SWhileStmt state, STree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.While),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
