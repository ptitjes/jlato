package org.jlato.internal.bu.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.type.TDWildcardType;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.ReferenceType;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SWildcardType extends SNodeState<SWildcardType> implements SType {

	public static STree<SWildcardType> make(STree<SNodeListState> annotations, STree<SNodeOptionState> ext, STree<SNodeOptionState> sup) {
		return new STree<SWildcardType>(new SWildcardType(annotations, ext, sup));
	}

	public final STree<SNodeListState> annotations;

	public final STree<SNodeOptionState> ext;

	public final STree<SNodeOptionState> sup;

	public SWildcardType(STree<SNodeListState> annotations, STree<SNodeOptionState> ext, STree<SNodeOptionState> sup) {
		this.annotations = annotations;
		this.ext = ext;
		this.sup = sup;
	}

	@Override
	public Kind kind() {
		return Kind.WildcardType;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SWildcardType withAnnotations(STree<SNodeListState> annotations) {
		return new SWildcardType(annotations, ext, sup);
	}

	public STree<SNodeOptionState> ext() {
		return ext;
	}

	public SWildcardType withExt(STree<SNodeOptionState> ext) {
		return new SWildcardType(annotations, ext, sup);
	}

	public STree<SNodeOptionState> sup() {
		return sup;
	}

	public SWildcardType withSup(STree<SNodeOptionState> sup) {
		return new SWildcardType(annotations, ext, sup);
	}

	@Override
	protected Tree doInstantiate(SLocation<SWildcardType> location) {
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
		public STree<?> doTraverse(SWildcardType state) {
			return state.annotations;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SWildcardType state) {
			return state.ext;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, STree<SNodeOptionState> child) {
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
		public STree<?> doTraverse(SWildcardType state) {
			return state.sup;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, STree<SNodeOptionState> child) {
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
