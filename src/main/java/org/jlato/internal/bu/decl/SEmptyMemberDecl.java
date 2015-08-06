package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEmptyMemberDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an empty member declaration.
 */
public class SEmptyMemberDecl extends SNode<SEmptyMemberDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new empty member declaration.
	 *
	 * @return the new <code>BUTree</code> with an empty member declaration.
	 */
	public static BUTree<SEmptyMemberDecl> make() {
		return new BUTree<SEmptyMemberDecl>(new SEmptyMemberDecl());
	}

	/**
	 * Constructs an empty member declaration state.
	 */
	public SEmptyMemberDecl() {
	}

	/**
	 * Returns the kind of this empty member declaration.
	 *
	 * @return the kind of this empty member declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.EmptyMemberDecl;
	}

	/**
	 * Builds an empty member declaration facade for the specified empty member declaration <code>TDLocation</code>.
	 *
	 * @param location the empty member declaration <code>TDLocation</code>.
	 * @return an empty member declaration facade for the specified empty member declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SEmptyMemberDecl> location) {
		return new TDEmptyMemberDecl(location);
	}

	/**
	 * Returns the shape for this empty member declaration state.
	 *
	 * @return the shape for this empty member declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this empty member declaration state.
	 *
	 * @return the first child traversal for this empty member declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this empty member declaration state.
	 *
	 * @return the last child traversal for this empty member declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return null;
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
		return result;
	}

	public static final LexicalShape shape = token(LToken.SemiColon);
}
