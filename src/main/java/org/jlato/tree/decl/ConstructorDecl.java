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
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ConstructorDecl extends TreeBase<ConstructorDecl.State, MemberDecl, ConstructorDecl> implements MemberDecl {

	public final static SKind<ConstructorDecl.State> kind = new SKind<ConstructorDecl.State>() {
		public ConstructorDecl instantiate(SLocation<ConstructorDecl.State> location) {
			return new ConstructorDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ConstructorDecl(SLocation<ConstructorDecl.State> location) {
		super(location);
	}

	public static STree<ConstructorDecl.State> make(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		return new STree<ConstructorDecl.State>(kind, new ConstructorDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(params), TreeBase.<SNodeListState>nodeOf(throwsClause), TreeBase.<BlockStmt.State>nodeOf(body)));
	}

	public ConstructorDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation<ConstructorDecl.State>(make(modifiers, typeParams, name, params, throwsClause, body)));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Constructor;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
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

	private static final STraversal<ConstructorDecl.State> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<ConstructorDecl.State> TYPE_PARAMETERS = SNodeState.childTraversal(1);
	private static final STraversal<ConstructorDecl.State> NAME = SNodeState.childTraversal(2);
	private static final STraversal<ConstructorDecl.State> PARAMETERS = SNodeState.childTraversal(3);
	private static final STraversal<ConstructorDecl.State> THROWS_CLAUSE = SNodeState.childTraversal(4);
	private static final STraversal<ConstructorDecl.State> BODY = SNodeState.childTraversal(5);

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMETERS, FormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<SNodeListState> typeParams;

		public final STree<Name.State> name;

		public final STree<SNodeListState> params;

		public final STree<SNodeListState> throwsClause;

		public final STree<BlockStmt.State> body;

		State(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<Name.State> name, STree<SNodeListState> params, STree<SNodeListState> throwsClause, STree<BlockStmt.State> body) {
			this.modifiers = modifiers;
			this.typeParams = typeParams;
			this.name = name;
			this.params = params;
			this.throwsClause = throwsClause;
			this.body = body;
		}

		public ConstructorDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withTypeParams(STree<SNodeListState> typeParams) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withName(STree<Name.State> name) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withParams(STree<SNodeListState> params) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withThrowsClause(STree<SNodeListState> throwsClause) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withBody(STree<BlockStmt.State> body) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public STraversal<ConstructorDecl.State> firstChild() {
			return null;
		}

		public STraversal<ConstructorDecl.State> lastChild() {
			return null;
		}
	}
}
