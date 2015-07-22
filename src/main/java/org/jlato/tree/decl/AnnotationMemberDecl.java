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

package org.jlato.tree.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class AnnotationMemberDecl extends TreeBase<AnnotationMemberDecl.State, MemberDecl, AnnotationMemberDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	private AnnotationMemberDecl(SLocation<AnnotationMemberDecl.State> location) {
		super(location);
	}

	public static STree<AnnotationMemberDecl.State> make(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<Name.State> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
		return new STree<AnnotationMemberDecl.State>(new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue));
	}

	public AnnotationMemberDecl(NodeList<ExtendedModifier> modifiers, Type type, Name name, NodeList<ArrayDim> dims, NodeOption<Expr> defaultValue) {
		super(new SLocation<AnnotationMemberDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Type.State>treeOf(type), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(dims), TreeBase.<SNodeOptionState>treeOf(defaultValue))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.AnnotationMember;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public AnnotationMemberDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public AnnotationMemberDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public AnnotationMemberDecl withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public AnnotationMemberDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public AnnotationMemberDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public AnnotationMemberDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMS);
	}

	public AnnotationMemberDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public AnnotationMemberDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMS, mutation);
	}

	public NodeOption<Expr> defaultValue() {
		return location.safeTraversal(DEFAULT_VALUE);
	}

	public AnnotationMemberDecl withDefaultValue(NodeOption<Expr> defaultValue) {
		return location.safeTraversalReplace(DEFAULT_VALUE, defaultValue);
	}

	public AnnotationMemberDecl withDefaultValue(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(DEFAULT_VALUE, mutation);
	}

	public static class State extends SNodeState<State> implements MemberDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<? extends Type.State> type;

		public final STree<Name.State> name;

		public final STree<SNodeListState> dims;

		public final STree<SNodeOptionState> defaultValue;

		State(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<Name.State> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
			this.modifiers = modifiers;
			this.type = type;
			this.name = name;
			this.dims = dims;
			this.defaultValue = defaultValue;
		}

		public AnnotationMemberDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		public AnnotationMemberDecl.State withType(STree<? extends Type.State> type) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		public AnnotationMemberDecl.State withName(STree<Name.State> name) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		public AnnotationMemberDecl.State withDims(STree<SNodeListState> dims) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		public AnnotationMemberDecl.State withDefaultValue(STree<SNodeOptionState> defaultValue) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		@Override
		public Kind kind() {
			return Kind.AnnotationMemberDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<AnnotationMemberDecl.State> location) {
			return new AnnotationMemberDecl(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return MODIFIERS;
		}

		@Override
		public STraversal lastChild() {
			return DEFAULT_VALUE;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			AnnotationMemberDecl.State state = (AnnotationMemberDecl.State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!dims.equals(state.dims))
				return false;
			if (!defaultValue.equals(state.defaultValue))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			if (type != null) result = 37 * result + type.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + dims.hashCode();
			result = 37 * result + defaultValue.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<AnnotationMemberDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<AnnotationMemberDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		protected STree<?> doTraverse(AnnotationMemberDecl.State state) {
			return state.modifiers;
		}

		@Override
		protected AnnotationMemberDecl.State doRebuildParentState(AnnotationMemberDecl.State state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	private static STypeSafeTraversal<AnnotationMemberDecl.State, Type.State, Type> TYPE = new STypeSafeTraversal<AnnotationMemberDecl.State, Type.State, Type>() {

		@Override
		protected STree<?> doTraverse(AnnotationMemberDecl.State state) {
			return state.type;
		}

		@Override
		protected AnnotationMemberDecl.State doRebuildParentState(AnnotationMemberDecl.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<AnnotationMemberDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<AnnotationMemberDecl.State, Name.State, Name>() {

		@Override
		protected STree<?> doTraverse(AnnotationMemberDecl.State state) {
			return state.name;
		}

		@Override
		protected AnnotationMemberDecl.State doRebuildParentState(AnnotationMemberDecl.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	private static STypeSafeTraversal<AnnotationMemberDecl.State, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<AnnotationMemberDecl.State, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		protected STree<?> doTraverse(AnnotationMemberDecl.State state) {
			return state.dims;
		}

		@Override
		protected AnnotationMemberDecl.State doRebuildParentState(AnnotationMemberDecl.State state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DEFAULT_VALUE;
		}
	};

	private static STypeSafeTraversal<AnnotationMemberDecl.State, SNodeOptionState, NodeOption<Expr>> DEFAULT_VALUE = new STypeSafeTraversal<AnnotationMemberDecl.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		protected STree<?> doTraverse(AnnotationMemberDecl.State state) {
			return state.defaultValue;
		}

		@Override
		protected AnnotationMemberDecl.State doRebuildParentState(AnnotationMemberDecl.State state, STree<SNodeOptionState> child) {
			return state.withDefaultValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape defaultValShape = composite(token(LToken.Default), element());

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			child(DEFAULT_VALUE, when(some(), defaultValShape)),
			token(LToken.SemiColon)
	);
}
