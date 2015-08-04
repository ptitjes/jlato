package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDArrayDim;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayDim extends SNodeState<SArrayDim> implements STreeState {

	public static STree<SArrayDim> make(STree<SNodeListState> annotations) {
		return new STree<SArrayDim>(new SArrayDim(annotations));
	}

	public final STree<SNodeListState> annotations;

	public SArrayDim(STree<SNodeListState> annotations) {
		this.annotations = annotations;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayDim;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SArrayDim withAnnotations(STree<SNodeListState> annotations) {
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

	public static STypeSafeTraversal<SArrayDim, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDim, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(SArrayDim state) {
			return state.annotations;
		}

		@Override
		public SArrayDim doRebuildParentState(SArrayDim state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
