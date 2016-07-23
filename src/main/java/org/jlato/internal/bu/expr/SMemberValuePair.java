package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMemberValuePair;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an annotation member value pair.
 */
public class SMemberValuePair extends SNode<SMemberValuePair> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new annotation member value pair.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param value the value child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an annotation member value pair.
	 */
	public static BUTree<SMemberValuePair> make(BUTree<SName> name, BUTree<? extends SExpr> value) {
		return new BUTree<SMemberValuePair>(new SMemberValuePair(name, value));
	}

	/**
	 * The name of this annotation member value pair state.
	 */
	public final BUTree<SName> name;

	/**
	 * The value of this annotation member value pair state.
	 */
	public final BUTree<? extends SExpr> value;

	/**
	 * Constructs an annotation member value pair state.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param value the value child <code>BUTree</code>.
	 */
	public SMemberValuePair(BUTree<SName> name, BUTree<? extends SExpr> value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns the kind of this annotation member value pair.
	 *
	 * @return the kind of this annotation member value pair.
	 */
	@Override
	public Kind kind() {
		return Kind.MemberValuePair;
	}

	/**
	 * Replaces the name of this annotation member value pair state.
	 *
	 * @param name the replacement for the name of this annotation member value pair state.
	 * @return the resulting mutated annotation member value pair state.
	 */
	public SMemberValuePair withName(BUTree<SName> name) {
		return new SMemberValuePair(name, value);
	}

	/**
	 * Replaces the value of this annotation member value pair state.
	 *
	 * @param value the replacement for the value of this annotation member value pair state.
	 * @return the resulting mutated annotation member value pair state.
	 */
	public SMemberValuePair withValue(BUTree<? extends SExpr> value) {
		return new SMemberValuePair(name, value);
	}

	/**
	 * Builds an annotation member value pair facade for the specified annotation member value pair <code>TDLocation</code>.
	 *
	 * @param location the annotation member value pair <code>TDLocation</code>.
	 * @return an annotation member value pair facade for the specified annotation member value pair <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SMemberValuePair> location) {
		return new TDMemberValuePair(location);
	}

	/**
	 * Returns the shape for this annotation member value pair state.
	 *
	 * @return the shape for this annotation member value pair state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this annotation member value pair state.
	 *
	 * @return the first child traversal for this annotation member value pair state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this annotation member value pair state.
	 *
	 * @return the last child traversal for this annotation member value pair state.
	 */
	@Override
	public STraversal lastChild() {
		return VALUE;
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
		SMemberValuePair state = (SMemberValuePair) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (value == null ? state.value != null : !value.equals(state.value))
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
		if (name != null) result = 37 * result + name.hashCode();
		if (value != null) result = 37 * result + value.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMemberValuePair, SName, Name> NAME = new STypeSafeTraversal<SMemberValuePair, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SMemberValuePair state) {
			return state.name;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return VALUE;
		}
	};

	public static STypeSafeTraversal<SMemberValuePair, SExpr, Expr> VALUE = new STypeSafeTraversal<SMemberValuePair, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SMemberValuePair state) {
			return state.value;
		}

		@Override
		public SMemberValuePair doRebuildParentState(SMemberValuePair state, BUTree<SExpr> child) {
			return state.withValue(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			token(LToken.Assign).withSpacing(space(), space()),
			child(VALUE)
	);
}
