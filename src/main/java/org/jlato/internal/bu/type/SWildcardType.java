package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDWildcardType;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SWildcardType extends SNode<SWildcardType> implements SType {

	public static BUTree<SWildcardType> make(BUTree<SNodeList> annotations, BUTree<SNodeOption> ext, BUTree<SNodeOption> sup) {
		return new BUTree<SWildcardType>(new SWildcardType(annotations, ext, sup));
	}

	public final BUTree<SNodeList> annotations;

	public final BUTree<SNodeOption> ext;

	public final BUTree<SNodeOption> sup;

	public SWildcardType(BUTree<SNodeList> annotations, BUTree<SNodeOption> ext, BUTree<SNodeOption> sup) {
		this.annotations = annotations;
		this.ext = ext;
		this.sup = sup;
	}

	@Override
	public Kind kind() {
		return Kind.WildcardType;
	}

	public SWildcardType withAnnotations(BUTree<SNodeList> annotations) {
		return new SWildcardType(annotations, ext, sup);
	}

	public SWildcardType withExt(BUTree<SNodeOption> ext) {
		return new SWildcardType(annotations, ext, sup);
	}

	public SWildcardType withSup(BUTree<SNodeOption> sup) {
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

	public static STypeSafeTraversal<SWildcardType, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SWildcardType, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.annotations;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXT;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>> EXT = new STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.ext;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOption> child) {
			return state.withExt(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return SUP;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>> SUP = new STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.sup;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOption> child) {
			return state.withSup(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXT;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
