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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

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
		super(new SLocation<QualifiedType.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<SNodeOptionState>nodeOf(scope), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeOptionState>nodeOf(typeArgs))));
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

	public Type withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public Type withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
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

	private static final STraversal<QualifiedType.State> ANNOTATIONS = new STraversal<QualifiedType.State>() {

		public STree<?> traverse(QualifiedType.State state) {
			return state.annotations;
		}

		public QualifiedType.State rebuildParentState(QualifiedType.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal<QualifiedType.State> leftSibling(QualifiedType.State state) {
			return null;
		}

		public STraversal<QualifiedType.State> rightSibling(QualifiedType.State state) {
			return SCOPE;
		}
	};
	private static final STraversal<QualifiedType.State> SCOPE = new STraversal<QualifiedType.State>() {

		public STree<?> traverse(QualifiedType.State state) {
			return state.scope;
		}

		public QualifiedType.State rebuildParentState(QualifiedType.State state, STree<?> child) {
			return state.withScope((STree) child);
		}

		public STraversal<QualifiedType.State> leftSibling(QualifiedType.State state) {
			return ANNOTATIONS;
		}

		public STraversal<QualifiedType.State> rightSibling(QualifiedType.State state) {
			return NAME;
		}
	};
	private static final STraversal<QualifiedType.State> NAME = new STraversal<QualifiedType.State>() {

		public STree<?> traverse(QualifiedType.State state) {
			return state.name;
		}

		public QualifiedType.State rebuildParentState(QualifiedType.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<QualifiedType.State> leftSibling(QualifiedType.State state) {
			return SCOPE;
		}

		public STraversal<QualifiedType.State> rightSibling(QualifiedType.State state) {
			return TYPE_ARGS;
		}
	};
	private static final STraversal<QualifiedType.State> TYPE_ARGS = new STraversal<QualifiedType.State>() {

		public STree<?> traverse(QualifiedType.State state) {
			return state.typeArgs;
		}

		public QualifiedType.State rebuildParentState(QualifiedType.State state, STree<?> child) {
			return state.withTypeArgs((STree) child);
		}

		public STraversal<QualifiedType.State> leftSibling(QualifiedType.State state) {
			return NAME;
		}

		public STraversal<QualifiedType.State> rightSibling(QualifiedType.State state) {
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
			token(LToken.Extends),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape implementsClauseShape = list(
			token(LToken.Implements),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape throwsClauseShape = list(
			token(LToken.Throws).withSpacingBefore(space()),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<QualifiedType.State> firstChild() {
			return ANNOTATIONS;
		}

		public STraversal<QualifiedType.State> lastChild() {
			return TYPE_ARGS;
		}

		public Tree instantiate(SLocation<QualifiedType.State> location) {
			return new QualifiedType(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.QualifiedType;
		}
	}
}
