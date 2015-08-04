package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDArrayDim;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayDim extends SNode<SArrayDim> implements STree {

	public static BUTree<SArrayDim> make(BUTree<SNodeList> annotations) {
		return new BUTree<SArrayDim>(new SArrayDim(annotations));
	}

	public final BUTree<SNodeList> annotations;

	public SArrayDim(BUTree<SNodeList> annotations) {
		this.annotations = annotations;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayDim;
	}

	public BUTree<SNodeList> annotations() {
		return annotations;
	}

	public SArrayDim withAnnotations(BUTree<SNodeList> annotations) {
		return new SArrayDim(annotations);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayDim> location) {
		return new TDArrayDim(location);
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
		return ANNOTATIONS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SArrayDim state = (SArrayDim) o;
		if (!annotations.equals(state.annotations))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayDim, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDim, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayDim state) {
			return state.annotations;
		}

		@Override
		public SArrayDim doRebuildParentState(SArrayDim state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
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
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}