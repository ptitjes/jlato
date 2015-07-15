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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ConstructorDecl extends TreeBase<SNodeState> implements MemberDecl {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public ConstructorDecl instantiate(SLocation location) {
			return new ConstructorDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ConstructorDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl(NodeList<EM> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(modifiers, typeParams, name, params, throwsClause, body)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Constructor;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl withModifiers(NodeList<EM> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> ConstructorDecl withModifiers(Mutation<NodeList<EM>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMETERS);
	}

	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMETERS, typeParams);
	}

	public ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMETERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public ConstructorDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ConstructorDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(PARAMETERS);
	}

	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMETERS, params);
	}

	public ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMETERS, mutation);
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

	public BlockStmt body() {
		return location.safeTraversal(BODY);
	}

	public ConstructorDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ConstructorDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<SNodeState> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE_PARAMETERS = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> PARAMETERS = SNodeState.childTraversal(3);
	private static final STraversal<SNodeState> THROWS_CLAUSE = SNodeState.childTraversal(4);
	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(5);

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(NAME),
			child(PARAMETERS, FormalParameter.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);
}
