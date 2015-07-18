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
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class AnnotationMemberDecl extends TreeBase<AnnotationMemberDecl.State, MemberDecl, AnnotationMemberDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	private AnnotationMemberDecl(SLocation<AnnotationMemberDecl.State> location) {
		super(location);
	}

	public static STree<AnnotationMemberDecl.State> make(STree<SNodeListState> modifiers, STree<Type.State> type, STree<Name.State> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
		return new STree<AnnotationMemberDecl.State>(new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue));
	}

	public AnnotationMemberDecl(NodeList<ExtendedModifier> modifiers, Type type, Name name, NodeList<ArrayDim> dims, NodeOption<Expr> defaultValue) {
		super(new SLocation<AnnotationMemberDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Type.State>nodeOf(type), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(dims), TreeBase.<SNodeOptionState>nodeOf(defaultValue))));
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

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation) {
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

	private static final STraversal<AnnotationMemberDecl.State> MODIFIERS = new STraversal<AnnotationMemberDecl.State>() {

		public STree<?> traverse(AnnotationMemberDecl.State state) {
			return state.modifiers;
		}

		public AnnotationMemberDecl.State rebuildParentState(AnnotationMemberDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<AnnotationMemberDecl.State> leftSibling(AnnotationMemberDecl.State state) {
			return null;
		}

		public STraversal<AnnotationMemberDecl.State> rightSibling(AnnotationMemberDecl.State state) {
			return TYPE;
		}
	};
	private static final STraversal<AnnotationMemberDecl.State> TYPE = new STraversal<AnnotationMemberDecl.State>() {

		public STree<?> traverse(AnnotationMemberDecl.State state) {
			return state.type;
		}

		public AnnotationMemberDecl.State rebuildParentState(AnnotationMemberDecl.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<AnnotationMemberDecl.State> leftSibling(AnnotationMemberDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<AnnotationMemberDecl.State> rightSibling(AnnotationMemberDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<AnnotationMemberDecl.State> NAME = new STraversal<AnnotationMemberDecl.State>() {

		public STree<?> traverse(AnnotationMemberDecl.State state) {
			return state.name;
		}

		public AnnotationMemberDecl.State rebuildParentState(AnnotationMemberDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<AnnotationMemberDecl.State> leftSibling(AnnotationMemberDecl.State state) {
			return TYPE;
		}

		public STraversal<AnnotationMemberDecl.State> rightSibling(AnnotationMemberDecl.State state) {
			return DIMS;
		}
	};
	private static final STraversal<AnnotationMemberDecl.State> DIMS = new STraversal<AnnotationMemberDecl.State>() {

		public STree<?> traverse(AnnotationMemberDecl.State state) {
			return state.dims;
		}

		public AnnotationMemberDecl.State rebuildParentState(AnnotationMemberDecl.State state, STree<?> child) {
			return state.withDims((STree) child);
		}

		public STraversal<AnnotationMemberDecl.State> leftSibling(AnnotationMemberDecl.State state) {
			return NAME;
		}

		public STraversal<AnnotationMemberDecl.State> rightSibling(AnnotationMemberDecl.State state) {
			return DEFAULT_VALUE;
		}
	};
	private static final STraversal<AnnotationMemberDecl.State> DEFAULT_VALUE = new STraversal<AnnotationMemberDecl.State>() {

		public STree<?> traverse(AnnotationMemberDecl.State state) {
			return state.defaultValue;
		}

		public AnnotationMemberDecl.State rebuildParentState(AnnotationMemberDecl.State state, STree<?> child) {
			return state.withDefaultValue((STree) child);
		}

		public STraversal<AnnotationMemberDecl.State> leftSibling(AnnotationMemberDecl.State state) {
			return DIMS;
		}

		public STraversal<AnnotationMemberDecl.State> rightSibling(AnnotationMemberDecl.State state) {
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

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Type.State> type;

		public final STree<Name.State> name;

		public final STree<SNodeListState> dims;

		public final STree<SNodeOptionState> defaultValue;

		State(STree<SNodeListState> modifiers, STree<Type.State> type, STree<Name.State> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
			this.modifiers = modifiers;
			this.type = type;
			this.name = name;
			this.dims = dims;
			this.defaultValue = defaultValue;
		}

		public AnnotationMemberDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new AnnotationMemberDecl.State(modifiers, type, name, dims, defaultValue);
		}

		public AnnotationMemberDecl.State withType(STree<Type.State> type) {
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

		public STraversal<AnnotationMemberDecl.State> firstChild() {
			return MODIFIERS;
		}

		public STraversal<AnnotationMemberDecl.State> lastChild() {
			return DEFAULT_VALUE;
		}

		public Tree instantiate(SLocation<AnnotationMemberDecl.State> location) {
			return new AnnotationMemberDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.AnnotationMemberDecl;
		}
	}
}
