package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDIfStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LSCondition.withKind;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class SIfStmt extends SNode<SIfStmt> implements SStmt {

	public static BUTree<SIfStmt> make(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> thenStmt, BUTree<SNodeOption> elseStmt) {
		return new BUTree<SIfStmt>(new SIfStmt(condition, thenStmt, elseStmt));
	}

	public final BUTree<? extends SExpr> condition;

	public final BUTree<? extends SStmt> thenStmt;

	public final BUTree<SNodeOption> elseStmt;

	public SIfStmt(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> thenStmt, BUTree<SNodeOption> elseStmt) {
		this.condition = condition;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	@Override
	public Kind kind() {
		return Kind.IfStmt;
	}

	public BUTree<? extends SExpr> condition() {
		return condition;
	}

	public SIfStmt withCondition(BUTree<? extends SExpr> condition) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	public BUTree<? extends SStmt> thenStmt() {
		return thenStmt;
	}

	public SIfStmt withThenStmt(BUTree<? extends SStmt> thenStmt) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	public BUTree<SNodeOption> elseStmt() {
		return elseStmt;
	}

	public SIfStmt withElseStmt(BUTree<SNodeOption> elseStmt) {
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
		public BUTree<?> doTraverse(SIfStmt state) {
			return state.condition;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return THEN_STMT;
		}
	};

	public static STypeSafeTraversal<SIfStmt, SStmt, Stmt> THEN_STMT = new STypeSafeTraversal<SIfStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SIfStmt state) {
			return state.thenStmt;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, BUTree<SStmt> child) {
			return state.withThenStmt(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CONDITION;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ELSE_STMT;
		}
	};

	public static STypeSafeTraversal<SIfStmt, SNodeOption, NodeOption<Stmt>> ELSE_STMT = new STypeSafeTraversal<SIfStmt, SNodeOption, NodeOption<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SIfStmt state) {
			return state.elseStmt;
		}

		@Override
		public SIfStmt doRebuildParentState(SIfStmt state, BUTree<SNodeOption> child) {
			return state.withElseStmt(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return THEN_STMT;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
