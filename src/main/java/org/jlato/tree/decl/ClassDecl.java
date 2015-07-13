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
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ClassDecl extends TypeDecl {

	public final static Kind kind = new Kind() {
		public ClassDecl instantiate(SLocation location) {
			return new ClassDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected ClassDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> ClassDecl(NodeList<EM> modifiers, Name name, NodeList<TypeParameter> typeParams, QualifiedType extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, name, typeParams, extendsClause, implementsClause, members)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> ClassDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> ClassDecl withModifiers(Rewrite<NodeList<EM>> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return TypeKind.Class;
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public ClassDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public ClassDecl withName(Rewrite<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ClassDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public ClassDecl withTypeParams(Rewrite<NodeList<TypeParameter>> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public QualifiedType extendsClause() {
		return location.nodeChild(EXTENDS_CLAUSE);
	}

	public ClassDecl withExtendsClause(QualifiedType extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public ClassDecl withExtendsClause(Rewrite<QualifiedType> extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.nodeChild(IMPLEMENTS_CLAUSE);
	}

	public ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public ClassDecl withImplementsClause(Rewrite<NodeList<QualifiedType>> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public NodeList<MemberDecl> members() {
		return location.nodeChild(MEMBERS);
	}

	public ClassDecl withMembers(NodeList<MemberDecl> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	public ClassDecl withMembers(Rewrite<NodeList<MemberDecl>> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int EXTENDS_CLAUSE = 3;
	private static final int IMPLEMENTS_CLAUSE = 4;
	private static final int MEMBERS = 5;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Class),
			child(NAME),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(EXTENDS_CLAUSE, composite(
					token(LToken.Extends).withSpacing(space(), space()),
					defaultShape()
			)),
			child(IMPLEMENTS_CLAUSE, QualifiedType.implementsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
