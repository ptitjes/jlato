package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDIfStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an 'if' statement.
 */
public class SIfStmt extends SNode<SIfStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'if' statement.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param thenStmt  the then statement child <code>BUTree</code>.
	 * @param elseStmt  the else statement child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an 'if' statement.
	 */
	public static BUTree<SIfStmt> make(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> thenStmt, BUTree<SNodeOption> elseStmt) {
		return new BUTree<SIfStmt>(new SIfStmt(condition, thenStmt, elseStmt));
	}

	/**
	 * The condition of this 'if' statement state.
	 */
	public final BUTree<? extends SExpr> condition;

	/**
	 * The then statement of this 'if' statement state.
	 */
	public final BUTree<? extends SStmt> thenStmt;

	/**
	 * The else statement of this 'if' statement state.
	 */
	public final BUTree<SNodeOption> elseStmt;

	/**
	 * Constructs an 'if' statement state.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param thenStmt  the then statement child <code>BUTree</code>.
	 * @param elseStmt  the else statement child <code>BUTree</code>.
	 */
	public SIfStmt(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> thenStmt, BUTree<SNodeOption> elseStmt) {
		this.condition = condition;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	/**
	 * Returns the kind of this 'if' statement.
	 *
	 * @return the kind of this 'if' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.IfStmt;
	}

	/**
	 * Replaces the condition of this 'if' statement state.
	 *
	 * @param condition the replacement for the condition of this 'if' statement state.
	 * @return the resulting mutated 'if' statement state.
	 */
	public SIfStmt withCondition(BUTree<? extends SExpr> condition) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	/**
	 * Replaces the then statement of this 'if' statement state.
	 *
	 * @param thenStmt the replacement for the then statement of this 'if' statement state.
	 * @return the resulting mutated 'if' statement state.
	 */
	public SIfStmt withThenStmt(BUTree<? extends SStmt> thenStmt) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	/**
	 * Replaces the else statement of this 'if' statement state.
	 *
	 * @param elseStmt the replacement for the else statement of this 'if' statement state.
	 * @return the resulting mutated 'if' statement state.
	 */
	public SIfStmt withElseStmt(BUTree<SNodeOption> elseStmt) {
		return new SIfStmt(condition, thenStmt, elseStmt);
	}

	/**
	 * Builds an 'if' statement facade for the specified 'if' statement <code>TDLocation</code>.
	 *
	 * @param location the 'if' statement <code>TDLocation</code>.
	 * @return an 'if' statement facade for the specified 'if' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SIfStmt> location) {
		return new TDIfStmt(location);
	}

	/**
	 * Returns the shape for this 'if' statement state.
	 *
	 * @return the shape for this 'if' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'if' statement state.
	 *
	 * @return the first child traversal for this 'if' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return CONDITION;
	}

	/**
	 * Returns the last child traversal for this 'if' statement state.
	 *
	 * @return the last child traversal for this 'if' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return ELSE_STMT;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
