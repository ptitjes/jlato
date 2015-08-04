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

public class STypeParameter extends SNode<STypeParameter> implements STree {

	public static BUTree<STypeParameter> make(BUTree<SNodeList> annotations, BUTree<SName> name, BUTree<SNodeList> bounds) {
		return new BUTree<STypeParameter>(new STypeParameter(annotations, name, bounds));
	}

	public final BUTree<SNodeList> annotations;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> bounds;

	public STypeParameter(BUTree<SNodeList> annotations, BUTree<SName> name, BUTree<SNodeList> bounds) {
		this.annotations = annotations;
		this.name = name;
		this.bounds = bounds;
	}

	@Override
	public Kind kind() {
		return Kind.TypeParameter;
	}

	public BUTree<SNodeList> annotations() {
		return annotations;
	}

	public STypeParameter withAnnotations(BUTree<SNodeList> annotations) {
		return new STypeParameter(annotations, name, bounds);
	}

	public BUTree<SName> name() {
		return name;
	}

	public STypeParameter withName(BUTree<SName> name) {
		return new STypeParameter(annotations, name, bounds);
	}

	public BUTree<SNodeList> bounds() {
		return bounds;
	}

	public STypeParameter withBounds(BUTree<SNodeList> bounds) {
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
