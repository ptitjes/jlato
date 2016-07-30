package org.jlato.internal.bu.name;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.name.TDName;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a name.
 */
public class SName extends SNode<SName> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new name.
	 *
	 * @param id the identifier child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a name.
	 */
	public static BUTree<SName> make(String id) {
		return new BUTree<SName>(new SName(id));
	}

	/**
	 * The identifier of this name state.
	 */
	public final String id;

	/**
	 * Constructs a name state.
	 *
	 * @param id the identifier child <code>BUTree</code>.
	 */
	public SName(String id) {
		this.id = id;
	}

	/**
	 * Returns the kind of this name.
	 *
	 * @return the kind of this name.
	 */
	@Override
	public Kind kind() {
		return Kind.Name;
	}

	/**
	 * Replaces the identifier of this name state.
	 *
	 * @param id the replacement for the identifier of this name state.
	 * @return the resulting mutated name state.
	 */
	public SName withId(String id) {
		return new SName(id);
	}

	/**
	 * Builds a name facade for the specified name <code>TDLocation</code>.
	 *
	 * @param location the name <code>TDLocation</code>.
	 * @return a name facade for the specified name <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SName> location) {
		return new TDName(location);
	}

	/**
	 * Returns the shape for this name state.
	 *
	 * @return the shape for this name state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this name state.
	 *
	 * @return the properties for this name state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(ID);
	}

	/**
	 * Returns the first child traversal for this name state.
	 *
	 * @return the first child traversal for this name state.
	 */
	@Override
	public STraversal firstChild() {
		return null;
	}

	/**
	 * Returns the last child traversal for this name state.
	 *
	 * @return the last child traversal for this name state.
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
		SName state = (SName) o;
		if (id == null ? state.id != null : !id.equals(state.id))
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
		if (id != null) result = 37 * result + id.hashCode();
		return result;
	}

	public static STypeSafeProperty<SName, String> ID = new STypeSafeProperty<SName, String>() {

		@Override
		public String doRetrieve(SName state) {
			return state.id;
		}

		@Override
		public SName doRebuildParentState(SName state, String value) {
			return state.withId(value);
		}
	};

	public static final LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(BUTree tree) {
			return new LToken(TokenType.IDENTIFIER, ((SName) tree.state).id);
		}
	});
}
