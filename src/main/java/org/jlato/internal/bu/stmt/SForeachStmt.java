package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDForeachStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SForeachStmt extends SNode<SForeachStmt> implements SStmt {

	public static BUTree<SForeachStmt> make(BUTree<SVariableDeclarationExpr> var, BUTree<? extends SExpr> iterable, BUTree<? extends SStmt> body) {
		return new BUTree<SForeachStmt>(new SForeachStmt(var, iterable, body));
	}

	public final BUTree<SVariableDeclarationExpr> var;

	public final BUTree<? extends SExpr> iterable;

	public final BUTree<? extends SStmt> body;

	public SForeachStmt(BUTree<SVariableDeclarationExpr> var, BUTree<? extends SExpr> iterable, BUTree<? extends SStmt> body) {
		this.var = var;
		this.iterable = iterable;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ForeachStmt;
	}

	public BUTree<SVariableDeclarationExpr> var() {
		return var;
	}

	public SForeachStmt withVar(BUTree<SVariableDeclarationExpr> var) {
		return new SForeachStmt(var, iterable, body);
	}

	public BUTree<? extends SExpr> iterable() {
		return iterable;
	}

	public SForeachStmt withIterable(BUTree<? extends SExpr> iterable) {
		return new SForeachStmt(var, iterable, body);
	}

	public BUTree<? extends SStmt> body() {
		return body;
	}

	public SForeachStmt withBody(BUTree<? extends SStmt> body) {
		return new SForeachStmt(var, iterable, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SForeachStmt> location) {
		return new TDForeachStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return VAR;
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
		SForeachStmt state = (SForeachStmt) o;
		if (var == null ? state.var != null : !var.equals(state.var))
			return false;
		if (iterable == null ? state.iterable != null : !iterable.equals(state.iterable))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (var != null) result = 37 * result + var.hashCode();
		if (iterable != null) result = 37 * result + iterable.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SForeachStmt, SVariableDeclarationExpr, VariableDeclarationExpr> VAR = new STypeSafeTraversal<SForeachStmt, SVariableDeclarationExpr, VariableDeclarationExpr>() {

		@Override
		public BUTree<?> doTraverse(SForeachStmt state) {
			return state.var;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, BUTree<SVariableDeclarationExpr> child) {
			return state.withVar(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ITERABLE;
		}
	};

	public static STypeSafeTraversal<SForeachStmt, SExpr, Expr> ITERABLE = new STypeSafeTraversal<SForeachStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SForeachStmt state) {
			return state.iterable;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, BUTree<SExpr> child) {
			return state.withIterable(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return VAR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SForeachStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SForeachStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SForeachStmt state) {
			return state.body;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ITERABLE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(VAR),
			token(LToken.Colon).withSpacing(space(), space()),
			child(ITERABLE),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
