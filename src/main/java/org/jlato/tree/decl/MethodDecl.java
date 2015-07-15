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
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class MethodDecl extends TreeBase<SNodeState> implements MemberDecl {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public MethodDecl instantiate(SLocation location) {
			return new MethodDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl() {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(NodeList.empty(), NodeList.empty(), null, null, NodeList.empty(), NodeList.empty(), NodeList.empty(), null)))));
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl(NodeList<EM> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, NodeOption<BlockStmt> body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(modifiers, typeParams, type, name, params, dims, throwsClause, body)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Method;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl withModifiers(NodeList<EM> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl withModifiers(Mutation<NodeList<EM>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMETERS);
	}

	public MethodDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMETERS, typeParams);
	}

	public MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMETERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public MethodDecl withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public MethodDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public MethodDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MethodDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(PARAMETERS);
	}

	public MethodDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMETERS, params);
	}

	public MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMETERS, mutation);
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

	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(THROWS_CLAUSE, throwsClause);
	}

	public ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(THROWS_CLAUSE, mutation);
	}

	public NodeOption<BlockStmt> body() {
		return location.safeTraversal(BODY);
	}

	public MethodDecl withBody(NodeOption<BlockStmt> body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public MethodDecl withBody(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<SNodeState> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE_PARAMETERS = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(3);
	private static final STraversal<SNodeState> PARAMETERS = SNodeState.childTraversal(4);
	private static final STraversal<SNodeState> DIMS = SNodeState.childTraversal(5);
	private static final STraversal<SNodeState> THROWS_CLAUSE = SNodeState.childTraversal(6);
	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(7);

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(TYPE),
			none().withSpacingAfter(space()),
			child(NAME),
			child(PARAMETERS, FormalParameter.listShape),
			child(DIMS, ArrayDim.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			alternative(childIs(BODY, some()),
					composite(none().withSpacingAfter(space()), child(BODY, element())),
					token(LToken.SemiColon)
			)
	);
}
