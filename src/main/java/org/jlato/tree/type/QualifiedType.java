/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class QualifiedType extends TreeBase<QualifiedType.State, ReferenceType, QualifiedType> implements ReferenceType {

	public Kind kind() {
		return Kind.QualifiedType;
	}

	private QualifiedType(SLocation<QualifiedType.State> location) {
		super(location);
	}

	public static STree<QualifiedType.State> make(STree<SNodeListState> annotations, STree<SNodeOptionState> scope, STree<Name.State> name, STree<SNodeOptionState> typeArgs) {
		return new STree<QualifiedType.State>(new QualifiedType.State(annotations, scope, name, typeArgs));
	}

	public QualifiedType(NodeList<AnnotationExpr> annotations, NodeOption<QualifiedType> scope, Name name, NodeOption<NodeList<Type>> typeArgs) {
		super(new SLocation<QualifiedType.State>(make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeOptionState>treeOf(typeArgs))));
	}

	public NodeOption<QualifiedType> scope() {
		return location.safeTraversal(SCOPE);
	}

	public QualifiedType withScope(NodeOption<QualifiedType> scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public QualifiedType withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public QualifiedType withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeOption<NodeList<Type>> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public static class State extends SNodeState<State> implements ReferenceType.State {

		public final STree<SNodeListState> annotations;

		public final STree<SNodeOptionState> scope;

		public final STree<Name.State> name;

		public final STree<SNodeOptionState> typeArgs;

		State(STree<SNodeListState> annotations, STree<SNodeOptionState> scope, STree<Name.State> name, STree<SNodeOptionState> typeArgs) {
			this.annotations = annotations;
			this.scope = scope;
			this.name = name;
			this.typeArgs = typeArgs;
		}

		public QualifiedType.State withAnnotations(STree<SNodeListState> annotations) {
			return new QualifiedType.State(annotations, scope, name, typeArgs);
		}

		public QualifiedType.State withScope(STree<SNodeOptionState> scope) {
			return new QualifiedType.State(annotations, scope, name, typeArgs);
		}

		public QualifiedType.State withName(STree<Name.State> name) {
			return new QualifiedType.State(annotations, scope, name, typeArgs);
		}

		public QualifiedType.State withTypeArgs(STree<SNodeOptionState> typeArgs) {
			return new QualifiedType.State(annotations, scope, name, typeArgs);
		}

		@Override
		public Kind kind() {
			return Kind.QualifiedType;
		}

		@Override
		protected Tree doInstantiate(SLocation<QualifiedType.State> location) {
			return new QualifiedType(location);
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
			State state = (State) o;
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
	}

	private static STypeSafeTraversal<QualifiedType.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<QualifiedType.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(QualifiedType.State state) {
			return state.annotations;
		}

		@Override
		public QualifiedType.State doRebuildParentState(QualifiedType.State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<QualifiedType.State, SNodeOptionState, NodeOption<QualifiedType>> SCOPE = new STypeSafeTraversal<QualifiedType.State, SNodeOptionState, NodeOption<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(QualifiedType.State state) {
			return state.scope;
		}

		@Override
		public QualifiedType.State doRebuildParentState(QualifiedType.State state, STree<SNodeOptionState> child) {
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

	private static STypeSafeTraversal<QualifiedType.State, Name.State, Name> NAME = new STypeSafeTraversal<QualifiedType.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(QualifiedType.State state) {
			return state.name;
		}

		@Override
		public QualifiedType.State doRebuildParentState(QualifiedType.State state, STree<Name.State> child) {
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

	private static STypeSafeTraversal<QualifiedType.State, SNodeOptionState, NodeOption<NodeList<Type>>> TYPE_ARGS = new STypeSafeTraversal<QualifiedType.State, SNodeOptionState, NodeOption<NodeList<Type>>>() {

		@Override
		public STree<?> doTraverse(QualifiedType.State state) {
			return state.typeArgs;
		}

		@Override
		public QualifiedType.State doRebuildParentState(QualifiedType.State state, STree<SNodeOptionState> child) {
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

	public final static LexicalShape shape = composite(
			child(SCOPE, when(some(), scopeShape)),
			child(ANNOTATIONS, AnnotationExpr.singleLineAnnotationsShape),
			child(NAME),
			child(TYPE_ARGS, when(some(), element(Type.typeArgumentsOrDiamondShape)))
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
