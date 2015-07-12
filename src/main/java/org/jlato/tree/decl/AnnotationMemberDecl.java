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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class AnnotationMemberDecl extends MemberDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AnnotationMemberDecl instantiate(SLocation location) {
			return new AnnotationMemberDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AnnotationMemberDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> AnnotationMemberDecl(NodeList<EM> modifiers, Type type, Name name, NodeList<ArrayDim> dims, Expr defaultValue) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, type, name, dims, defaultValue)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> AnnotationMemberDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public AnnotationMemberDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public AnnotationMemberDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<ArrayDim> dims() {
		return location.nodeChild(DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.nodeWithChild(DIMS, dims);
	}

	public Expr defaultValue() {
		return location.nodeChild(DEFAULT_VALUE);
	}

	public AnnotationMemberDecl withDefaultValue(Expr defaultValue) {
		return location.nodeWithChild(DEFAULT_VALUE, defaultValue);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int NAME = 2;
	private static final int DIMS = 3;
	private static final int DEFAULT_VALUE = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			nonNullChild(DEFAULT_VALUE, composite(token(LToken.Default), child(DEFAULT_VALUE))),
			token(LToken.SemiColon)
	);
}
