package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDDoStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SDoStmt extends SNode<SDoStmt> implements SStmt {

	public static BUTree<SDoStmt> make(BUTree<? extends SStmt> body, BUTree<? extends SExpr> condition) {
		return new BUTree<SDoStmt>(new SDoStmt(body, condition));
	}

	public final BUTree<? extends SStmt> body;

	public final BUTree<? extends SExpr> condition;

	public SDoStmt(BUTree<? extends SStmt> body, BUTree<? extends SExpr> condition) {
		this.body = body;
		this.condition = condition;
	}

	@Override
	public Kind kind() {
		return Kind.DoStmt;
	}

	public BUTree<? extends SStmt> body() {
		return body;
	}

	public SDoStmt withBody(BUTree<? extends SStmt> body) {
		return new SDoStmt(body, condition);
	}

	public BUTree<? extends SExpr> condition() {
		return condition;
	}

	public SDoStmt withCondition(BUTree<? extends SExpr> condition) {
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
		public BUTree<?> doTraverse(SDoStmt state) {
			return state.body;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CONDITION;
		}
	};

	public static STypeSafeTraversal<SDoStmt, SExpr, Expr> CONDITION = new STypeSafeTraversal<SDoStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SDoStmt state) {
			return state.condition;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return BODY;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
