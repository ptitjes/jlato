package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDWhileStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SWhileStmt extends SNode<SWhileStmt> implements SStmt {

	public static BUTree<SWhileStmt> make(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> body) {
		return new BUTree<SWhileStmt>(new SWhileStmt(condition, body));
	}

	public final BUTree<? extends SExpr> condition;

	public final BUTree<? extends SStmt> body;

	public SWhileStmt(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.WhileStmt;
	}

	public SWhileStmt withCondition(BUTree<? extends SExpr> condition) {
		return new SWhileStmt(condition, body);
	}

	public SWhileStmt withBody(BUTree<? extends SStmt> body) {
		return new SWhileStmt(condition, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SWhileStmt> location) {
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
		public BUTree<?> doTraverse(SWhileStmt state) {
			return state.condition;
		}

		@Override
		public SWhileStmt doRebuildParentState(SWhileStmt state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SWhileStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SWhileStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SWhileStmt state) {
			return state.body;
		}

		@Override
		public SWhileStmt doRebuildParentState(SWhileStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
