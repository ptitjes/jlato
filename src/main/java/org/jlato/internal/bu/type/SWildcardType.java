package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDWildcardType;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SWildcardType extends SNodeState<SWildcardType> implements SType {

	public static BUTree<SWildcardType> make(BUTree<SNodeListState> annotations, BUTree<SNodeOptionState> ext, BUTree<SNodeOptionState> sup) {
		return new BUTree<SWildcardType>(new SWildcardType(annotations, ext, sup));
	}

	public final BUTree<SNodeListState> annotations;

	public final BUTree<SNodeOptionState> ext;

	public final BUTree<SNodeOptionState> sup;

	public SWildcardType(BUTree<SNodeListState> annotations, BUTree<SNodeOptionState> ext, BUTree<SNodeOptionState> sup) {
		this.annotations = annotations;
		this.ext = ext;
		this.sup = sup;
	}

	@Override
	public Kind kind() {
		return Kind.WildcardType;
	}

	public BUTree<SNodeListState> annotations() {
		return annotations;
	}

	public SWildcardType withAnnotations(BUTree<SNodeListState> annotations) {
		return new SWildcardType(annotations, ext, sup);
	}

	public BUTree<SNodeOptionState> ext() {
		return ext;
	}

	public SWildcardType withExt(BUTree<SNodeOptionState> ext) {
		return new SWildcardType(annotations, ext, sup);
	}

	public BUTree<SNodeOptionState> sup() {
		return sup;
	}

	public SWildcardType withSup(BUTree<SNodeOptionState> sup) {
		return new SWildcardType(annotations, ext, sup);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SWildcardType> location) {
		return new TDWildcardType(location);
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
		return SUP;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SWildcardType state = (SWildcardType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (!ext.equals(state.ext))
			return false;
		if (!sup.equals(state.sup))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		result = 37 * result + ext.hashCode();
		result = 37 * result + sup.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SWildcardType, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SWildcardType, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.annotations;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXT;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOptionState, NodeOption<ReferenceType>> EXT = new STypeSafeTraversal<SWildcardType, SNodeOptionState, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.ext;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOptionState> child) {
			return state.withExt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return SUP;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOptionState, NodeOption<ReferenceType>> SUP = new STypeSafeTraversal<SWildcardType, SNodeOptionState, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.sup;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOptionState> child) {
			return state.withSup(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShape),
			token(LToken.QuestionMark),
			child(EXT, when(some(), composite(keyword(LToken.Extends), element()))),
			child(SUP, when(some(), composite(keyword(LToken.Super), element())))
	);
}
