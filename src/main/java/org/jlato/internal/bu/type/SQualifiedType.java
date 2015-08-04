package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDQualifiedType;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SQualifiedType extends SNodeState<SQualifiedType> implements SReferenceType {

	public static BUTree<SQualifiedType> make(BUTree<SNodeListState> annotations, BUTree<SNodeOptionState> scope, BUTree<SName> name, BUTree<SNodeOptionState> typeArgs) {
		return new BUTree<SQualifiedType>(new SQualifiedType(annotations, scope, name, typeArgs));
	}

	public final BUTree<SNodeListState> annotations;

	public final BUTree<SNodeOptionState> scope;

	public final BUTree<SName> name;

	public final BUTree<SNodeOptionState> typeArgs;

	public SQualifiedType(BUTree<SNodeListState> annotations, BUTree<SNodeOptionState> scope, BUTree<SName> name, BUTree<SNodeOptionState> typeArgs) {
		this.annotations = annotations;
		this.scope = scope;
		this.name = name;
		this.typeArgs = typeArgs;
	}

	@Override
	public Kind kind() {
		return Kind.QualifiedType;
	}

	public BUTree<SNodeListState> annotations() {
		return annotations;
	}

	public SQualifiedType withAnnotations(BUTree<SNodeListState> annotations) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public BUTree<SNodeOptionState> scope() {
		return scope;
	}

	public SQualifiedType withScope(BUTree<SNodeOptionState> scope) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SQualifiedType withName(BUTree<SName> name) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public BUTree<SNodeOptionState> typeArgs() {
		return typeArgs;
	}

	public SQualifiedType withTypeArgs(BUTree<SNodeOptionState> typeArgs) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SQualifiedType> location) {
		return new TDQualifiedType(location);
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
		return TYPE_ARGS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SQualifiedType state = (SQualifiedType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (!scope.equals(state.scope))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		result = 37 * result + scope.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + typeArgs.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SQualifiedType, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SQualifiedType, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.annotations;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return SCOPE;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SNodeOptionState, NodeOption<QualifiedType>> SCOPE = new STypeSafeTraversal<SQualifiedType, SNodeOptionState, NodeOption<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.scope;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeOptionState> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SName, Name> NAME = new STypeSafeTraversal<SQualifiedType, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.name;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SNodeOptionState, NodeOption<NodeList<Type>>> TYPE_ARGS = new STypeSafeTraversal<SQualifiedType, SNodeOptionState, NodeOption<NodeList<Type>>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.typeArgs;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeOptionState> child) {
			return state.withTypeArgs(child);
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

	public static final LexicalShape scopeShape = composite(element(), token(LToken.Dot));

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), scopeShape)),
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShape),
			child(NAME),
			child(TYPE_ARGS, when(some(), element(org.jlato.internal.bu.type.SType.typeArgumentsOrDiamondShape)))
	);

	public static final LexicalShape extendsClauseShape = list(
			keyword(LToken.Extends),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape implementsClauseShape = list(
			keyword(LToken.Implements),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape throwsClauseShape = list(
			keyword(LToken.Throws),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);
}
