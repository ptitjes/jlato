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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class InterfaceDecl extends TreeBase<InterfaceDecl.State, TypeDecl, InterfaceDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.InterfaceDecl;
	}

	protected InterfaceDecl(SLocation<InterfaceDecl.State> location) {
		super(location);
	}

	public static STree<InterfaceDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> typeParams, STree<SNodeListState> extendsClause, STree<SNodeListState> members) {
		return new STree<InterfaceDecl.State>(new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members));
	}

	public InterfaceDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		super(new SLocation<InterfaceDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<SNodeListState>nodeOf(extendsClause), TreeBase.<SNodeListState>nodeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Type;
	}

	public TypeKind typeKind() {
		return TypeKind.Interface;
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public InterfaceDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public InterfaceDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMS);
	}

	public InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMS, typeParams);
	}

	public InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMS, mutation);
	}

	public NodeList<QualifiedType> extendsClause() {
		return location.safeTraversal(EXTENDS_CLAUSE);
	}

	public InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(EXTENDS_CLAUSE, extendsClause);
	}

	public InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(EXTENDS_CLAUSE, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(MEMBERS);
	}

	public InterfaceDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(MEMBERS, members);
	}

	public InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(MEMBERS, mutation);
	}

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Interface),
			child(NAME),
			child(TYPE_PARAMS, TypeParameter.listShape),
			child(EXTENDS_CLAUSE, QualifiedType.extendsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
