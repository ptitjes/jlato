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
import org.jlato.tree.Mutator;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;

public class InterfaceDecl extends TypeDecl {

	public final static Kind kind = new Kind() {
		public InterfaceDecl instantiate(SLocation location) {
			return new InterfaceDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected InterfaceDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> InterfaceDecl(NodeList<EM> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, name, typeParams, extendsClause, members)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> InterfaceDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> InterfaceDecl withModifiers(Mutator<NodeList<EM>> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return TypeKind.Interface;
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public InterfaceDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public InterfaceDecl withName(Mutator<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public InterfaceDecl withTypeParams(Mutator<NodeList<TypeParameter>> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public NodeList<QualifiedType> extendsClause() {
		return location.nodeChild(EXTENDS_CLAUSE);
	}

	public InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public InterfaceDecl withExtendsClause(Mutator<NodeList<QualifiedType>> extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public NodeList<MemberDecl> members() {
		return location.nodeChild(MEMBERS);
	}

	public InterfaceDecl withMembers(NodeList<MemberDecl> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	public InterfaceDecl withMembers(Mutator<NodeList<MemberDecl>> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int EXTENDS_CLAUSE = 3;
	private static final int MEMBERS = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Interface),
			child(NAME),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(EXTENDS_CLAUSE, QualifiedType.extendsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
