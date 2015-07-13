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
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class MethodDecl extends MemberDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MethodDecl instantiate(SLocation location) {
			return new MethodDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl() {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(NodeList.empty(), NodeList.empty(), null, null, NodeList.empty(), NodeList.empty(), NodeList.empty(), null)))));
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl(NodeList<EM> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, typeParams, type, name, params, dims, throwsClause, body)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Method;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl withModifiers(Mutation<NodeList<EM>> modifiers) {
		return location.nodeMutateChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public MethodDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParams);
	}

	public MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> typeParams) {
		return location.nodeMutateChild(TYPE_PARAMETERS, typeParams);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public MethodDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public MethodDecl withType(Mutation<Type> type) {
		return location.nodeMutateChild(TYPE, type);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public MethodDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public MethodDecl withName(Mutation<Name> name) {
		return location.nodeMutateChild(NAME, name);
	}

	public NodeList<FormalParameter> params() {
		return location.nodeChild(PARAMETERS);
	}

	public MethodDecl withParams(NodeList<FormalParameter> params) {
		return location.nodeWithChild(PARAMETERS, params);
	}

	public MethodDecl withParams(Mutation<NodeList<FormalParameter>> params) {
		return location.nodeMutateChild(PARAMETERS, params);
	}

	public NodeList<ArrayDim> dims() {
		return location.nodeChild(DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.nodeWithChild(DIMS, dims);
	}

	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> dims) {
		return location.nodeMutateChild(DIMS, dims);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> throwsClause) {
		return location.nodeMutateChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public MethodDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public MethodDecl withBody(Mutation<BlockStmt> body) {
		return location.nodeMutateChild(BODY, body);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int TYPE = 2;
	private static final int NAME = 3;
	private static final int PARAMETERS = 4;
	private static final int DIMS = 5;
	private static final int THROWS_CLAUSE = 6;
	private static final int BODY = 7;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(TYPE),
			none().withSpacingAfter(space()),
			child(NAME),
			child(PARAMETERS, FormalParameter.listShape),
			child(DIMS, ArrayDim.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			nonNullChild(BODY,
					composite(none().withSpacingAfter(space()), child(BODY)),
					token(LToken.SemiColon)
			)
	);
}
