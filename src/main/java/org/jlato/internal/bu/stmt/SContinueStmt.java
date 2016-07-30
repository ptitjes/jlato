package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDContinueStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'continue' statement.
 */
public class SContinueStmt extends SNode<SContinueStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'continue' statement.
	 *
	 * @param id the identifier child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'continue' statement.
	 */
	public static BUTree<SContinueStmt> make(BUTree<SNodeOption> id) {
		return new BUTree<SContinueStmt>(new SContinueStmt(id));
	}

	/**
	 * The identifier of this 'continue' statement state.
	 */
	public final BUTree<SNodeOption> id;

	/**
	 * Constructs a 'continue' statement state.
	 *
	 * @param id the identifier child <code>BUTree</code>.
	 */
	public SContinueStmt(BUTree<SNodeOption> id) {
		this.id = id;
	}

	/**
	 * Returns the kind of this 'continue' statement.
	 *
	 * @return the kind of this 'continue' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ContinueStmt;
	}

	/**
	 * Replaces the identifier of this 'continue' statement state.
	 *
	 * @param id the replacement for the identifier of this 'continue' statement state.
	 * @return the resulting mutated 'continue' statement state.
	 */
	public SContinueStmt withId(BUTree<SNodeOption> id) {
		return new SContinueStmt(id);
	}

	/**
	 * Builds a 'continue' statement facade for the specified 'continue' statement <code>TDLocation</code>.
	 *
	 * @param location the 'continue' statement <code>TDLocation</code>.
	 * @return a 'continue' statement facade for the specified 'continue' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SContinueStmt> location) {
		return new TDContinueStmt(location);
	}

	/**
	 * Returns the shape for this 'continue' statement state.
	 *
	 * @return the shape for this 'continue' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'continue' statement state.
	 *
	 * @return the first child traversal for this 'continue' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return ID;
	}

	/**
	 * Returns the last child traversal for this 'continue' statement state.
	 *
	 * @return the last child traversal for this 'continue' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return ID;
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
		SContinueStmt state = (SContinueStmt) o;
		if (!id.equals(state.id))
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
		result = 37 * result + id.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SContinueStmt, SNodeOption, NodeOption<Name>> ID = new STypeSafeTraversal<SContinueStmt, SNodeOption, NodeOption<Name>>() {

		@Override
		public BUTree<?> doTraverse(SContinueStmt state) {
			return state.id;
		}

		@Override
		public SContinueStmt doRebuildParentState(SContinueStmt state, BUTree<SNodeOption> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Continue),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
