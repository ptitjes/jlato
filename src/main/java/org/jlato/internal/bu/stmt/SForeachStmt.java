package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SVariableDeclarationExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDForeachStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a "enhanced" 'for' statement.
 */
public class SForeachStmt extends SNode<SForeachStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new "enhanced" 'for' statement.
	 *
	 * @param var      the var child <code>BUTree</code>.
	 * @param iterable the iterable child <code>BUTree</code>.
	 * @param body     the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a "enhanced" 'for' statement.
	 */
	public static BUTree<SForeachStmt> make(BUTree<SVariableDeclarationExpr> var, BUTree<? extends SExpr> iterable, BUTree<? extends SStmt> body) {
		return new BUTree<SForeachStmt>(new SForeachStmt(var, iterable, body));
	}

	/**
	 * The var of this "enhanced" 'for' statement state.
	 */
	public final BUTree<SVariableDeclarationExpr> var;

	/**
	 * The iterable of this "enhanced" 'for' statement state.
	 */
	public final BUTree<? extends SExpr> iterable;

	/**
	 * The body of this "enhanced" 'for' statement state.
	 */
	public final BUTree<? extends SStmt> body;

	/**
	 * Constructs a "enhanced" 'for' statement state.
	 *
	 * @param var      the var child <code>BUTree</code>.
	 * @param iterable the iterable child <code>BUTree</code>.
	 * @param body     the body child <code>BUTree</code>.
	 */
	public SForeachStmt(BUTree<SVariableDeclarationExpr> var, BUTree<? extends SExpr> iterable, BUTree<? extends SStmt> body) {
		this.var = var;
		this.iterable = iterable;
		this.body = body;
	}

	/**
	 * Returns the kind of this "enhanced" 'for' statement.
	 *
	 * @return the kind of this "enhanced" 'for' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ForeachStmt;
	}

	/**
	 * Replaces the var of this "enhanced" 'for' statement state.
	 *
	 * @param var the replacement for the var of this "enhanced" 'for' statement state.
	 * @return the resulting mutated "enhanced" 'for' statement state.
	 */
	public SForeachStmt withVar(BUTree<SVariableDeclarationExpr> var) {
		return new SForeachStmt(var, iterable, body);
	}

	/**
	 * Replaces the iterable of this "enhanced" 'for' statement state.
	 *
	 * @param iterable the replacement for the iterable of this "enhanced" 'for' statement state.
	 * @return the resulting mutated "enhanced" 'for' statement state.
	 */
	public SForeachStmt withIterable(BUTree<? extends SExpr> iterable) {
		return new SForeachStmt(var, iterable, body);
	}

	/**
	 * Replaces the body of this "enhanced" 'for' statement state.
	 *
	 * @param body the replacement for the body of this "enhanced" 'for' statement state.
	 * @return the resulting mutated "enhanced" 'for' statement state.
	 */
	public SForeachStmt withBody(BUTree<? extends SStmt> body) {
		return new SForeachStmt(var, iterable, body);
	}

	/**
	 * Builds a "enhanced" 'for' statement facade for the specified "enhanced" 'for' statement <code>TDLocation</code>.
	 *
	 * @param location the "enhanced" 'for' statement <code>TDLocation</code>.
	 * @return a "enhanced" 'for' statement facade for the specified "enhanced" 'for' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SForeachStmt> location) {
		return new TDForeachStmt(location);
	}

	/**
	 * Returns the shape for this "enhanced" 'for' statement state.
	 *
	 * @return the shape for this "enhanced" 'for' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this "enhanced" 'for' statement state.
	 *
	 * @return the first child traversal for this "enhanced" 'for' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return VAR;
	}

	/**
	 * Returns the last child traversal for this "enhanced" 'for' statement state.
	 *
	 * @return the last child traversal for this "enhanced" 'for' statement state.
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SForeachStmt state = (SForeachStmt) o;
		if (var == null ? state.var != null : !var.equals(state.var))
			return false;
		if (iterable == null ? state.iterable != null : !iterable.equals(state.iterable))
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
