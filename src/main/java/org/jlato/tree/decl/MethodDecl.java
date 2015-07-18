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
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class MethodDecl extends TreeBase<MethodDecl.State, MemberDecl, MethodDecl> implements MemberDecl {

	public final static SKind<MethodDecl.State> kind = new SKind<MethodDecl.State>() {
		public MethodDecl instantiate(SLocation<MethodDecl.State> location) {
			return new MethodDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodDecl(SLocation<MethodDecl.State> location) {
		super(location);
	}

	public static STree<MethodDecl.State> make() {
		return new STree<MethodDecl.State>(kind, new MethodDecl.State());
	}

	public MethodDecl() {
		super(new SLocation<MethodDecl.State>(make()));
	}

	public MethodDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, NodeOption<BlockStmt> body) {
		super(new SLocation<MethodDecl.State>(make()));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Method;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public MethodDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public MethodDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMS);
	}

	public MethodDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMS, typeParams);
	}

	public MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMS, mutation);
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
		return location.safeTraversal(PARAMS);
	}

	public MethodDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMS, params);
	}

	public MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMS, mutation);
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

	private static final STraversal<MethodDecl.State> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<MethodDecl.State> TYPE_PARAMS = SNodeState.childTraversal(1);
	private static final STraversal<MethodDecl.State> TYPE = SNodeState.childTraversal(2);
	private static final STraversal<MethodDecl.State> NAME = SNodeState.childTraversal(3);
	private static final STraversal<MethodDecl.State> PARAMS = SNodeState.childTraversal(4);
	private static final STraversal<MethodDecl.State> DIMS = SNodeState.childTraversal(5);
	private static final STraversal<MethodDecl.State> THROWS_CLAUSE = SNodeState.childTraversal(6);
	private static final STraversal<MethodDecl.State> BODY = SNodeState.childTraversal(7);

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMS, TypeParameter.listShape),
			child(TYPE),
			none().withSpacingAfter(space()),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMS, FormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(DIMS, ArrayDim.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			alternative(childIs(BODY, some()),
					composite(none().withSpacingAfter(space()), child(BODY, element())),
					token(LToken.SemiColon)
			)
	);

	public static class State extends SNodeState<State> {

		State() {
		}

		public STraversal<MethodDecl.State> firstChild() {
			return null;
		}

		public STraversal<MethodDecl.State> lastChild() {
			return null;
		}
	}
}
