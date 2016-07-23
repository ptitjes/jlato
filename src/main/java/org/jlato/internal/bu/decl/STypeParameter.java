package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDTypeParameter;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a type parameter.
 */
public class STypeParameter extends SNode<STypeParameter> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new type parameter.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 * @param bounds      the bounds child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a type parameter.
	 */
	public static BUTree<STypeParameter> make(BUTree<SNodeList> annotations, BUTree<SName> name, BUTree<SNodeList> bounds) {
		return new BUTree<STypeParameter>(new STypeParameter(annotations, name, bounds));
	}

	/**
	 * The annotations of this type parameter state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The name of this type parameter state.
	 */
	public final BUTree<SName> name;

	/**
	 * The bounds of this type parameter state.
	 */
	public final BUTree<SNodeList> bounds;

	/**
	 * Constructs a type parameter state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 * @param bounds      the bounds child <code>BUTree</code>.
	 */
	public STypeParameter(BUTree<SNodeList> annotations, BUTree<SName> name, BUTree<SNodeList> bounds) {
		this.annotations = annotations;
		this.name = name;
		this.bounds = bounds;
	}

	/**
	 * Returns the kind of this type parameter.
	 *
	 * @return the kind of this type parameter.
	 */
	@Override
	public Kind kind() {
		return Kind.TypeParameter;
	}

	/**
	 * Replaces the annotations of this type parameter state.
	 *
	 * @param annotations the replacement for the annotations of this type parameter state.
	 * @return the resulting mutated type parameter state.
	 */
	public STypeParameter withAnnotations(BUTree<SNodeList> annotations) {
		return new STypeParameter(annotations, name, bounds);
	}

	/**
	 * Replaces the name of this type parameter state.
	 *
	 * @param name the replacement for the name of this type parameter state.
	 * @return the resulting mutated type parameter state.
	 */
	public STypeParameter withName(BUTree<SName> name) {
		return new STypeParameter(annotations, name, bounds);
	}

	/**
	 * Replaces the bounds of this type parameter state.
	 *
	 * @param bounds the replacement for the bounds of this type parameter state.
	 * @return the resulting mutated type parameter state.
	 */
	public STypeParameter withBounds(BUTree<SNodeList> bounds) {
		return new STypeParameter(annotations, name, bounds);
	}

	/**
	 * Builds a type parameter facade for the specified type parameter <code>TDLocation</code>.
	 *
	 * @param location the type parameter <code>TDLocation</code>.
	 * @return a type parameter facade for the specified type parameter <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<STypeParameter> location) {
		return new TDTypeParameter(location);
	}

	/**
	 * Returns the shape for this type parameter state.
	 *
	 * @return the shape for this type parameter state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this type parameter state.
	 *
	 * @return the first child traversal for this type parameter state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this type parameter state.
	 *
	 * @return the last child traversal for this type parameter state.
	 */
	@Override
	public STraversal lastChild() {
		return BOUNDS;
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
		STypeParameter state = (STypeParameter) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!bounds.equals(state.bounds))
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
		result = 37 * result + annotations.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + bounds.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeParameter, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<STypeParameter, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(STypeParameter state) {
			return state.annotations;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
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

	public static STypeSafeTraversal<STypeParameter, SName, Name> NAME = new STypeSafeTraversal<STypeParameter, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(STypeParameter state) {
			return state.name;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BOUNDS;
		}
	};

	public static STypeSafeTraversal<STypeParameter, SNodeList, NodeList<Type>> BOUNDS = new STypeSafeTraversal<STypeParameter, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(STypeParameter state) {
			return state.bounds;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, BUTree<SNodeList> child) {
			return state.withBounds(child);
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

	public static final LexicalShape boundsShape = list(
			keyword(LToken.Extends),
			token(LToken.BinAnd).withSpacing(space(), space()),
			none()
	);

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			child(NAME),
			child(BOUNDS, boundsShape)
	);

	public static final LexicalShape listShape = list(
			token(LToken.Less),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.Greater).withSpacingAfter(space())
	);
}
