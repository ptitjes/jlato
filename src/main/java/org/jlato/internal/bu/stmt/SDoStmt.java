package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDDoStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SDoStmt extends SNodeState<SDoStmt> implements SStmt {

	public static STree<SDoStmt> make(STree<? extends SStmt> body, STree<? extends SExpr> condition) {
		return new STree<SDoStmt>(new SDoStmt(body, condition));
	}

	public final STree<? extends SStmt> body;

	public final STree<? extends SExpr> condition;

	public SDoStmt(STree<? extends SStmt> body, STree<? extends SExpr> condition) {
		this.body = body;
		this.condition = condition;
	}

	@Override
	public Kind kind() {
		return Kind.DoStmt;
	}

	public STree<? extends SStmt> body() {
		return body;
	}

	public SDoStmt withBody(STree<? extends SStmt> body) {
		return new SDoStmt(body, condition);
	}

	public STree<? extends SExpr> condition() {
		return condition;
	}

	public SDoStmt withCondition(STree<? extends SExpr> condition) {
		return new SDoStmt(body, condition);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SDoStmt> location) {
		return new TDDoStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return BODY;
	}

	@Override
	public STraversal lastChild() {
		return CONDITION;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SDoStmt state = (SDoStmt) o;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (body != null) result = 37 * result + body.hashCode();
		if (condition != null) result = 37 * result + condition.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SDoStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SDoStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SDoStmt state) {
			return state.body;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, STree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CONDITION;
		}
	};

	public static STypeSafeTraversal<SDoStmt, SExpr, Expr> CONDITION = new STypeSafeTraversal<SDoStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SDoStmt state) {
			return state.condition;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, STree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return BODY;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Do),
			child(BODY),
			keyword(LToken.While),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);
}
