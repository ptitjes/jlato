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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ConstructorDecl extends MemberDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConstructorDecl instantiate(SLocation location) {
			return new ConstructorDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ConstructorDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl(NodeList<EM> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, typeParams, name, params, throwsClause, body)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Constructor;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl withModifiers(Rewrite<NodeList<EM>> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public ConstructorDecl withTypeParams(Rewrite<NodeList<TypeParameter>> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public ConstructorDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public ConstructorDecl withName(Rewrite<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<FormalParameter> params() {
		return location.nodeChild(PARAMETERS);
	}

	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.nodeWithChild(PARAMETERS, params);
	}

	public ConstructorDecl withParams(Rewrite<NodeList<FormalParameter>> params) {
		return location.nodeWithChild(PARAMETERS, params);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public ConstructorDecl withThrowsClause(Rewrite<NodeList<QualifiedType>> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public ConstructorDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public ConstructorDecl withBody(Rewrite<BlockStmt> body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int NAME = 2;
	private static final int PARAMETERS = 3;
	private static final int THROWS_CLAUSE = 4;
	private static final int BODY = 5;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(NAME),
			child(PARAMETERS, FormalParameter.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);
}
