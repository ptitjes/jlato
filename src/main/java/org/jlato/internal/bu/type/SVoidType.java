package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDVoidType;
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
 * A state object for a void type.
 */
public class SVoidType extends SNode<SVoidType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new void type.
	 *
	 * @return the new <code>BUTree</code> with a void type.
	 */
	public static BUTree<SVoidType> make() {
		return new BUTree<SVoidType>(new SVoidType());
	}

	/**
	 * Constructs a void type state.
	 */
	public SVoidType() {
	}

	/**
	 * Returns the kind of this void type.
	 *
	 * @return the kind of this void type.
	 */
	@Override
	public Kind kind() {
		return Kind.VoidType;
	}

	/**
	 * Builds a void type facade for the specified void type <code>TDLocation</code>.
	 *
	 * @param location the void type <code>TDLocation</code>.
	 * @return a void type facade for the specified void type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SVoidType> location) {
		return new TDVoidType(location);
	}

	/**
	 * Returns the shape for this void type state.
	 *
	 * @return the shape for this void type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this void type state.
	 *
	 * @return the first child traversal for this void type state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this void type state.
	 *
	 * @return the last child traversal for this void type state.
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
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

	public static final LexicalShape shape = token(LToken.Void);
}
