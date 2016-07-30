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
import org.jlato.internal.td.stmt.TDAssertStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an 'assert' statement.
 */
public class SAssertStmt extends SNode<SAssertStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'assert' statement.
	 *
	 * @param check the check child <code>BUTree</code>.
	 * @param msg   the msg child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an 'assert' statement.
	 */
	public static BUTree<SAssertStmt> make(BUTree<? extends SExpr> check, BUTree<SNodeOption> msg) {
		return new BUTree<SAssertStmt>(new SAssertStmt(check, msg));
	}

	/**
	 * The check of this 'assert' statement state.
	 */
	public final BUTree<? extends SExpr> check;

	/**
	 * The msg of this 'assert' statement state.
	 */
	public final BUTree<SNodeOption> msg;

	/**
	 * Constructs an 'assert' statement state.
	 *
	 * @param check the check child <code>BUTree</code>.
	 * @param msg   the msg child <code>BUTree</code>.
	 */
	public SAssertStmt(BUTree<? extends SExpr> check, BUTree<SNodeOption> msg) {
		this.check = check;
		this.msg = msg;
	}

	/**
	 * Returns the kind of this 'assert' statement.
	 *
	 * @return the kind of this 'assert' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.AssertStmt;
	}

	/**
	 * Replaces the check of this 'assert' statement state.
	 *
	 * @param check the replacement for the check of this 'assert' statement state.
	 * @return the resulting mutated 'assert' statement state.
	 */
	public SAssertStmt withCheck(BUTree<? extends SExpr> check) {
		return new SAssertStmt(check, msg);
	}

	/**
	 * Replaces the msg of this 'assert' statement state.
	 *
	 * @param msg the replacement for the msg of this 'assert' statement state.
	 * @return the resulting mutated 'assert' statement state.
	 */
	public SAssertStmt withMsg(BUTree<SNodeOption> msg) {
		return new SAssertStmt(check, msg);
	}

	/**
	 * Builds an 'assert' statement facade for the specified 'assert' statement <code>TDLocation</code>.
	 *
	 * @param location the 'assert' statement <code>TDLocation</code>.
	 * @return an 'assert' statement facade for the specified 'assert' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SAssertStmt> location) {
		return new TDAssertStmt(location);
	}

	/**
	 * Returns the shape for this 'assert' statement state.
	 *
	 * @return the shape for this 'assert' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'assert' statement state.
	 *
	 * @return the first child traversal for this 'assert' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return CHECK;
	}

	/**
	 * Returns the last child traversal for this 'assert' statement state.
	 *
	 * @return the last child traversal for this 'assert' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return MSG;
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
		SAssertStmt state = (SAssertStmt) o;
		if (check == null ? state.check != null : !check.equals(state.check))
			return false;
		if (!msg.equals(state.msg))
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
		if (check != null) result = 37 * result + check.hashCode();
		result = 37 * result + msg.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAssertStmt, SExpr, Expr> CHECK = new STypeSafeTraversal<SAssertStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SAssertStmt state) {
			return state.check;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, BUTree<SExpr> child) {
			return state.withCheck(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MSG;
		}
	};

	public static STypeSafeTraversal<SAssertStmt, SNodeOption, NodeOption<Expr>> MSG = new STypeSafeTraversal<SAssertStmt, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SAssertStmt state) {
			return state.msg;
		}

		@Override
		public SAssertStmt doRebuildParentState(SAssertStmt state, BUTree<SNodeOption> child) {
			return state.withMsg(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CHECK;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Assert),
			child(CHECK),
			child(MSG, when(some(),
					composite(token(LToken.Colon).withSpacing(space(), space()), element())
			)),
			token(LToken.SemiColon)
	);
}
