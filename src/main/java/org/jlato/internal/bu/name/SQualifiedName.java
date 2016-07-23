package org.jlato.internal.bu.name;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.name.TDQualifiedName;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a qualified name.
 */
public class SQualifiedName extends SNode<SQualifiedName> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new qualified name.
	 *
	 * @param qualifier the qualifier child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a qualified name.
	 */
	public static BUTree<SQualifiedName> make(BUTree<SNodeOption> qualifier, BUTree<SName> name) {
		return new BUTree<SQualifiedName>(new SQualifiedName(qualifier, name));
	}

	/**
	 * The qualifier of this qualified name state.
	 */
	public final BUTree<SNodeOption> qualifier;

	/**
	 * The name of this qualified name state.
	 */
	public final BUTree<SName> name;

	/**
	 * Constructs a qualified name state.
	 *
	 * @param qualifier the qualifier child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 */
	public SQualifiedName(BUTree<SNodeOption> qualifier, BUTree<SName> name) {
		this.qualifier = qualifier;
		this.name = name;
	}

	/**
	 * Returns the kind of this qualified name.
	 *
	 * @return the kind of this qualified name.
	 */
	@Override
	public Kind kind() {
		return Kind.QualifiedName;
	}

	/**
	 * Replaces the qualifier of this qualified name state.
	 *
	 * @param qualifier the replacement for the qualifier of this qualified name state.
	 * @return the resulting mutated qualified name state.
	 */
	public SQualifiedName withQualifier(BUTree<SNodeOption> qualifier) {
		return new SQualifiedName(qualifier, name);
	}

	/**
	 * Replaces the name of this qualified name state.
	 *
	 * @param name the replacement for the name of this qualified name state.
	 * @return the resulting mutated qualified name state.
	 */
	public SQualifiedName withName(BUTree<SName> name) {
		return new SQualifiedName(qualifier, name);
	}

	/**
	 * Builds a qualified name facade for the specified qualified name <code>TDLocation</code>.
	 *
	 * @param location the qualified name <code>TDLocation</code>.
	 * @return a qualified name facade for the specified qualified name <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SQualifiedName> location) {
		return new TDQualifiedName(location);
	}

	/**
	 * Returns the shape for this qualified name state.
	 *
	 * @return the shape for this qualified name state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this qualified name state.
	 *
	 * @return the first child traversal for this qualified name state.
	 */
	@Override
	public STraversal firstChild() {
		return QUALIFIER;
	}

	/**
	 * Returns the last child traversal for this qualified name state.
	 *
	 * @return the last child traversal for this qualified name state.
	 */
	@Override
	public STraversal lastChild() {
		return NAME;
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
		SQualifiedName state = (SQualifiedName) o;
		if (!qualifier.equals(state.qualifier))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
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
		result = 37 * result + qualifier.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SQualifiedName, SNodeOption, NodeOption<QualifiedName>> QUALIFIER = new STypeSafeTraversal<SQualifiedName, SNodeOption, NodeOption<QualifiedName>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedName state) {
			return state.qualifier;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, BUTree<SNodeOption> child) {
			return state.withQualifier(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SQualifiedName, SName, Name> NAME = new STypeSafeTraversal<SQualifiedName, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedName state) {
			return state.name;
		}

		@Override
		public SQualifiedName doRebuildParentState(SQualifiedName state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return QUALIFIER;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(QUALIFIER, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
