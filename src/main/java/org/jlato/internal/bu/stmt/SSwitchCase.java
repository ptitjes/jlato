package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDSwitchCase;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
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
 * A state object for a 'switch' case.
 */
public class SSwitchCase extends SNode<SSwitchCase> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new 'switch' case.
	 *
	 * @param label the label child <code>BUTree</code>.
	 * @param stmts the statements child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'switch' case.
	 */
	public static BUTree<SSwitchCase> make(BUTree<SNodeOption> label, BUTree<SNodeList> stmts) {
		return new BUTree<SSwitchCase>(new SSwitchCase(label, stmts));
	}

	/**
	 * The label of this 'switch' case state.
	 */
	public final BUTree<SNodeOption> label;

	/**
	 * The statements of this 'switch' case state.
	 */
	public final BUTree<SNodeList> stmts;

	/**
	 * Constructs a 'switch' case state.
	 *
	 * @param label the label child <code>BUTree</code>.
	 * @param stmts the statements child <code>BUTree</code>.
	 */
	public SSwitchCase(BUTree<SNodeOption> label, BUTree<SNodeList> stmts) {
		this.label = label;
		this.stmts = stmts;
	}

	/**
	 * Returns the kind of this 'switch' case.
	 *
	 * @return the kind of this 'switch' case.
	 */
	@Override
	public Kind kind() {
		return Kind.SwitchCase;
	}

	/**
	 * Replaces the label of this 'switch' case state.
	 *
	 * @param label the replacement for the label of this 'switch' case state.
	 * @return the resulting mutated 'switch' case state.
	 */
	public SSwitchCase withLabel(BUTree<SNodeOption> label) {
		return new SSwitchCase(label, stmts);
	}

	/**
	 * Replaces the statements of this 'switch' case state.
	 *
	 * @param stmts the replacement for the statements of this 'switch' case state.
	 * @return the resulting mutated 'switch' case state.
	 */
	public SSwitchCase withStmts(BUTree<SNodeList> stmts) {
		return new SSwitchCase(label, stmts);
	}

	/**
	 * Builds a 'switch' case facade for the specified 'switch' case <code>TDLocation</code>.
	 *
	 * @param location the 'switch' case <code>TDLocation</code>.
	 * @return a 'switch' case facade for the specified 'switch' case <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SSwitchCase> location) {
		return new TDSwitchCase(location);
	}

	/**
	 * Returns the shape for this 'switch' case state.
	 *
	 * @return the shape for this 'switch' case state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'switch' case state.
	 *
	 * @return the first child traversal for this 'switch' case state.
	 */
	@Override
	public STraversal firstChild() {
		return LABEL;
	}

	/**
	 * Returns the last child traversal for this 'switch' case state.
	 *
	 * @return the last child traversal for this 'switch' case state.
	 */
	@Override
	public STraversal lastChild() {
		return STMTS;
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
		SSwitchCase state = (SSwitchCase) o;
		if (!label.equals(state.label))
			return false;
		if (!stmts.equals(state.stmts))
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
		result = 37 * result + label.hashCode();
		result = 37 * result + stmts.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSwitchCase, SNodeOption, NodeOption<Expr>> LABEL = new STypeSafeTraversal<SSwitchCase, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchCase state) {
			return state.label;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, BUTree<SNodeOption> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return STMTS;
		}
	};

	public static STypeSafeTraversal<SSwitchCase, SNodeList, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SSwitchCase, SNodeList, NodeList<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchCase state) {
			return state.stmts;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, BUTree<SNodeList> child) {
			return state.withStmts(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(LABEL, alternative(some(),
					composite(keyword(LToken.Case), element()),
					token(LToken.Default)
			)),
			token(LToken.Colon).withSpacingAfter(newLine()),
			none().withIndentationAfter(indent(IndentationContext.SwitchCase)),
			child(STMTS, SStmt.listShape),
			none().withIndentationBefore(unIndent(IndentationContext.SwitchCase))
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
