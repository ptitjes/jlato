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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class InterfaceDecl extends TreeBase<InterfaceDecl.State, TypeDecl, InterfaceDecl> implements TypeDecl {

	public final static SKind<InterfaceDecl.State> kind = new SKind<InterfaceDecl.State>() {
		public InterfaceDecl instantiate(SLocation<InterfaceDecl.State> location) {
			return new InterfaceDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected InterfaceDecl(SLocation<InterfaceDecl.State> location) {
		super(location);
	}

	public static STree<InterfaceDecl.State> make(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		return new STree<InterfaceDecl.State>(kind, new InterfaceDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<SNodeListState>nodeOf(extendsClause), TreeBase.<SNodeListState>nodeOf(members)));
	}

	public InterfaceDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		super(new SLocation<InterfaceDecl.State>(make(modifiers, name, typeParams, extendsClause, members)));
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
		return location.safeTraversal(TYPE_PARAMETERS);
	}

	public InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMETERS, typeParams);
	}

	public InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMETERS, mutation);
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

	private static final STraversal<InterfaceDecl.State> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<InterfaceDecl.State> NAME = SNodeState.childTraversal(1);
	private static final STraversal<InterfaceDecl.State> TYPE_PARAMETERS = SNodeState.childTraversal(2);
	private static final STraversal<InterfaceDecl.State> EXTENDS_CLAUSE = SNodeState.childTraversal(3);
	private static final STraversal<InterfaceDecl.State> MEMBERS = SNodeState.childTraversal(4);

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Interface),
			child(NAME),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(EXTENDS_CLAUSE, QualifiedType.extendsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Name.State> name;

		public final STree<SNodeListState> typeParams;

		public final STree<SNodeListState> extendsClause;

		public final STree<SNodeListState> members;

		State(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> typeParams, STree<SNodeListState> extendsClause, STree<SNodeListState> members) {
			this.modifiers = modifiers;
			this.name = name;
			this.typeParams = typeParams;
			this.extendsClause = extendsClause;
			this.members = members;
		}

		public InterfaceDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members);
		}

		public InterfaceDecl.State withName(STree<Name.State> name) {
			return new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members);
		}

		public InterfaceDecl.State withTypeParams(STree<SNodeListState> typeParams) {
			return new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members);
		}

		public InterfaceDecl.State withExtendsClause(STree<SNodeListState> extendsClause) {
			return new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members);
		}

		public InterfaceDecl.State withMembers(STree<SNodeListState> members) {
			return new InterfaceDecl.State(modifiers, name, typeParams, extendsClause, members);
		}

		public STraversal<InterfaceDecl.State> firstChild() {
			return null;
		}

		public STraversal<InterfaceDecl.State> lastChild() {
			return null;
		}
	}
}
