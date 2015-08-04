package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDTypeParameter;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class STypeParameter extends SNodeState<STypeParameter> implements STreeState {

	public static STree<STypeParameter> make(STree<SNodeListState> annotations, STree<SName> name, STree<SNodeListState> bounds) {
		return new STree<STypeParameter>(new STypeParameter(annotations, name, bounds));
	}

	public final STree<SNodeListState> annotations;

	public final STree<SName> name;

	public final STree<SNodeListState> bounds;

	public STypeParameter(STree<SNodeListState> annotations, STree<SName> name, STree<SNodeListState> bounds) {
		this.annotations = annotations;
		this.name = name;
		this.bounds = bounds;
	}

	@Override
	public Kind kind() {
		return Kind.TypeParameter;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public STypeParameter withAnnotations(STree<SNodeListState> annotations) {
		return new STypeParameter(annotations, name, bounds);
	}

	public STree<SName> name() {
		return name;
	}

	public STypeParameter withName(STree<SName> name) {
		return new STypeParameter(annotations, name, bounds);
	}

	public STree<SNodeListState> bounds() {
		return bounds;
	}

	public STypeParameter withBounds(STree<SNodeListState> bounds) {
		return new STypeParameter(annotations, name, bounds);
	}

	@Override
	protected Tree doInstantiate(TDLocation<STypeParameter> location) {
		return new TDTypeParameter(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	@Override
	public STraversal lastChild() {
		return BOUNDS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		STypeParameter state = (STypeParameter) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!bounds.equals(state.bounds))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + bounds.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeParameter, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<STypeParameter, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(STypeParameter state) {
			return state.annotations;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<STypeParameter, SName, Name> NAME = new STypeSafeTraversal<STypeParameter, SName, Name>() {

		@Override
		public STree<?> doTraverse(STypeParameter state) {
			return state.name;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BOUNDS;
		}
	};

	public static STypeSafeTraversal<STypeParameter, SNodeListState, NodeList<Type>> BOUNDS = new STypeSafeTraversal<STypeParameter, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(STypeParameter state) {
			return state.bounds;
		}

		@Override
		public STypeParameter doRebuildParentState(STypeParameter state, STree<SNodeListState> child) {
			return state.withBounds(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
