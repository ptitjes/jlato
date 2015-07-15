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
import org.jlato.internal.td.SKind;
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

public class AnnotationMemberDecl extends TreeBase<SNodeState> implements MemberDecl {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public AnnotationMemberDecl instantiate(SLocation<SNodeState> location) {
			return new AnnotationMemberDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AnnotationMemberDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public AnnotationMemberDecl(NodeList<ExtendedModifier> modifiers, Type type, Name name, NodeList<ArrayDim> dims, NodeOption<Expr> defaultValue) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(modifiers, type, name, dims, defaultValue)))));
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

	private static final STraversal<SNodeState> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> DIMS = SNodeState.childTraversal(3);
	private static final STraversal<SNodeState> DEFAULT_VALUE = SNodeState.childTraversal(4);

	public static final LexicalShape defaultValShape = composite(token(LToken.Default), element());

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			child(DEFAULT_VALUE, when(some(), defaultValShape)),
			token(LToken.SemiColon)
	);
}
