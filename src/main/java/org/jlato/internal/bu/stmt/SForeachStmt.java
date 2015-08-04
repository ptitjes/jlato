package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDForeachStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SForeachStmt extends SNodeState<SForeachStmt> implements SStmt {

	public static STree<SForeachStmt> make(STree<SVariableDeclarationExpr> var, STree<? extends SExpr> iterable, STree<? extends SStmt> body) {
		return new STree<SForeachStmt>(new SForeachStmt(var, iterable, body));
	}

	public final STree<SVariableDeclarationExpr> var;

	public final STree<? extends SExpr> iterable;

	public final STree<? extends SStmt> body;

	public SForeachStmt(STree<SVariableDeclarationExpr> var, STree<? extends SExpr> iterable, STree<? extends SStmt> body) {
		this.var = var;
		this.iterable = iterable;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ForeachStmt;
	}

	public STree<SVariableDeclarationExpr> var() {
		return var;
	}

	public SForeachStmt withVar(STree<SVariableDeclarationExpr> var) {
		return new SForeachStmt(var, iterable, body);
	}

	public STree<? extends SExpr> iterable() {
		return iterable;
	}

	public SForeachStmt withIterable(STree<? extends SExpr> iterable) {
		return new SForeachStmt(var, iterable, body);
	}

	public STree<? extends SStmt> body() {
		return body;
	}

	public SForeachStmt withBody(STree<? extends SStmt> body) {
		return new SForeachStmt(var, iterable, body);
	}

	@Override
	protected Tree doInstantiate(SLocation<SForeachStmt> location) {
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
		public STree<?> doTraverse(SForeachStmt state) {
			return state.var;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, STree<SVariableDeclarationExpr> child) {
			return state.withVar(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ITERABLE;
		}
	};

	public static STypeSafeTraversal<SForeachStmt, SExpr, Expr> ITERABLE = new STypeSafeTraversal<SForeachStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SForeachStmt state) {
			return state.iterable;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, STree<SExpr> child) {
			return state.withIterable(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return VAR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SForeachStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SForeachStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SForeachStmt state) {
			return state.body;
		}

		@Override
		public SForeachStmt doRebuildParentState(SForeachStmt state, STree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ITERABLE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
