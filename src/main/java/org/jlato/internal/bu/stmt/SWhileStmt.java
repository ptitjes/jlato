package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDWhileStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
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
 * A state object for a 'while' statement.
 */
public class SWhileStmt extends SNode<SWhileStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'while' statement.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'while' statement.
	 */
	public static BUTree<SWhileStmt> make(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> body) {
		return new BUTree<SWhileStmt>(new SWhileStmt(condition, body));
	}

	/**
	 * The condition of this 'while' statement state.
	 */
	public final BUTree<? extends SExpr> condition;

	/**
	 * The body of this 'while' statement state.
	 */
	public final BUTree<? extends SStmt> body;

	/**
	 * Constructs a 'while' statement state.
	 *
	 * @param condition the condition child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 */
	public SWhileStmt(BUTree<? extends SExpr> condition, BUTree<? extends SStmt> body) {
		this.condition = condition;
		this.body = body;
	}

	/**
	 * Returns the kind of this 'while' statement.
	 *
	 * @return the kind of this 'while' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.WhileStmt;
	}

	/**
	 * Replaces the condition of this 'while' statement state.
	 *
	 * @param condition the replacement for the condition of this 'while' statement state.
	 * @return the resulting mutated 'while' statement state.
	 */
	public SWhileStmt withCondition(BUTree<? extends SExpr> condition) {
		return new SWhileStmt(condition, body);
	}

	/**
	 * Replaces the body of this 'while' statement state.
	 *
	 * @param body the replacement for the body of this 'while' statement state.
	 * @return the resulting mutated 'while' statement state.
	 */
	public SWhileStmt withBody(BUTree<? extends SStmt> body) {
		return new SWhileStmt(condition, body);
	}

	/**
	 * Builds a 'while' statement facade for the specified 'while' statement <code>TDLocation</code>.
	 *
	 * @param location the 'while' statement <code>TDLocation</code>.
	 * @return a 'while' statement facade for the specified 'while' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SWhileStmt> location) {
		return new TDWhileStmt(location);
	}

	/**
	 * Returns the shape for this 'while' statement state.
	 *
	 * @return the shape for this 'while' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'while' statement state.
	 *
	 * @return the first child traversal for this 'while' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return CONDITION;
	}

	/**
	 * Returns the last child traversal for this 'while' statement state.
	 *
	 * @return the last child traversal for this 'while' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
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
		SWhileStmt state = (SWhileStmt) o;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
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
