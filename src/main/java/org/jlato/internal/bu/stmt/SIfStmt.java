package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDIfStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SIfStmt extends SNodeState<SIfStmt> implements SStmt {

	public static STree<SIfStmt> make(STree<? extends SExpr> condition, STree<? extends SStmt> thenStmt, STree<SNodeOptionState> elseStmt) {
		return new STree<SIfStmt>(new SIfStmt(condition, thenStmt, elseStmt));
	}

	public final STree<? extends SExpr> condition;

	public final STree<? extends SStmt> thenStmt;

	public final STree<SNodeOptionState> elseStmt;

	public SIfStmt(STree<? extends SExpr> condition, STree<? extends SStmt> thenStmt, STree<SNodeOptionState> elseStmt) {
		this.condition = condition;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	@Override
	public Kind kind() {
		return Kind.IfStmt;
	}

	public STree<? extends SExpr> condition() {
		return condition;
	}

	public SIfStmt withCondition(STree<? extends SExpr> condition) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	public STree<? extends SStmt> thenStmt() {
		return thenStmt;
	}

	public SIfStmt withThenStmt(STree<? extends SStmt> thenStmt) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	public STree<SNodeOptionState> elseStmt() {
		return elseStmt;
	}

	public SIfStmt withElseStmt(STree<SNodeOptionState> elseStmt) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SIfStmt> location) {
		return new TDIfStmt(location);
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
		return ELSE_STMT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SIfStmt state = (SIfStmt) o;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		if (thenStmt == null ? state.thenStmt != null : !thenStmt.equals(state.thenStmt))
			return false;
		if (!elseStmt.equals(state.elseStmt))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (condition != null) result = 37 * result + condition.hashCode();
		if (thenStmt != null) result = 37 * result + thenStmt.hashCode();
		result = 37 * result + elseStmt.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SIfStmt, SExpr, Expr> CONDITION = new STypeSafeTraversal<SIfStmt, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SIfStmt state) {
			return state.condition;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, STree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THEN_STMT;
		}
	};

	public static STypeSafeTraversal<SIfStmt, SStmt, Stmt> THEN_STMT = new STypeSafeTraversal<SIfStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SIfStmt state) {
			return state.thenStmt;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, STree<SStmt> child) {
			return state.withThenStmt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ELSE_STMT;
		}
	};

	public static STypeSafeTraversal<SIfStmt, SNodeOptionState, NodeOption<Stmt>> ELSE_STMT = new STypeSafeTraversal<SIfStmt, SNodeOptionState, NodeOption<Stmt>>() {

		@Override
		public STree<?> doTraverse(SIfStmt state) {
			return state.elseStmt;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, STree<SNodeOptionState> child) {
			return state.withElseStmt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return THEN_STMT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.If),
			token(LToken.ParenthesisLeft),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			child(THEN_STMT,
					alternative(withKind(Kind.BlockStmt),
							defaultShape().withSpacingBefore(space()),
							alternative(withKind(Kind.ExpressionStmt),
									defaultShape()
											.withSpacingBefore(spacing(IfStmt_ThenExpressionStmt))
											.withIndentationBefore(indent(BLOCK))
											.withIndentationAfter(unIndent(BLOCK))
											.withSpacingAfter(newLine()),
									defaultShape()
											.withSpacingBefore(spacing(IfStmt_ThenOtherStmt))
											.withIndentationBefore(indent(BLOCK))
											.withIndentationAfter(unIndent(BLOCK))
											.withSpacingAfter(newLine())
							)
					)
			),
			child(ELSE_STMT, when(some(), composite(
					keyword(LToken.Else),
					element(alternative(withKind(Kind.BlockStmt),
							defaultShape().withSpacingBefore(space()),
							alternative(withKind(Kind.IfStmt),
									defaultShape().withSpacingBefore(spacing(IfStmt_ElseIfStmt)),
									alternative(withKind(Kind.ExpressionStmt),
											defaultShape()
													.withSpacingBefore(spacing(IfStmt_ElseExpressionStmt))
													.withIndentationBefore(indent(BLOCK))
													.withIndentationAfter(unIndent(BLOCK)),
											defaultShape()
													.withSpacingBefore(spacing(IfStmt_ElseOtherStmt))
													.withIndentationBefore(indent(BLOCK))
													.withIndentationAfter(unIndent(BLOCK))
													.withSpacingAfter(newLine())
									)
							)
					))
			)))
	);
}
