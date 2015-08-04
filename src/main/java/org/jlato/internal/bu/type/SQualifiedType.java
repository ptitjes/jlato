package org.jlato.internal.bu.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.type.TDQualifiedType;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SQualifiedType extends SNodeState<SQualifiedType> implements SReferenceType {

	public static STree<SQualifiedType> make(STree<SNodeListState> annotations, STree<SNodeOptionState> scope, STree<SName> name, STree<SNodeOptionState> typeArgs) {
		return new STree<SQualifiedType>(new SQualifiedType(annotations, scope, name, typeArgs));
	}

	public final STree<SNodeListState> annotations;

	public final STree<SNodeOptionState> scope;

	public final STree<SName> name;

	public final STree<SNodeOptionState> typeArgs;

	public SQualifiedType(STree<SNodeListState> annotations, STree<SNodeOptionState> scope, STree<SName> name, STree<SNodeOptionState> typeArgs) {
		this.annotations = annotations;
		this.scope = scope;
		this.name = name;
		this.typeArgs = typeArgs;
	}

	@Override
	public Kind kind() {
		return Kind.QualifiedType;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SQualifiedType withAnnotations(STree<SNodeListState> annotations) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public STree<SNodeOptionState> scope() {
		return scope;
	}

	public SQualifiedType withScope(STree<SNodeOptionState> scope) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public STree<SName> name() {
		return name;
	}

	public SQualifiedType withName(STree<SName> name) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	public STree<SNodeOptionState> typeArgs() {
		return typeArgs;
	}

	public SQualifiedType withTypeArgs(STree<SNodeOptionState> typeArgs) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	@Override
	protected Tree doInstantiate(SLocation<SQualifiedType> location) {
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
		public STree<?> doTraverse(SQualifiedType state) {
			return state.annotations;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SQualifiedType state) {
			return state.scope;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, STree<SNodeOptionState> child) {
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
		public STree<?> doTraverse(SQualifiedType state) {
			return state.name;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, STree<SName> child) {
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
		public STree<?> doTraverse(SQualifiedType state) {
			return state.typeArgs;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, STree<SNodeOptionState> child) {
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
