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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ConstructorDecl extends TreeBase<ConstructorDecl.State, MemberDecl, ConstructorDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	private ConstructorDecl(SLocation<ConstructorDecl.State> location) {
		super(location);
	}

	public static STree<ConstructorDecl.State> make(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<Name.State> name, STree<SNodeListState> params, STree<SNodeListState> throwsClause, STree<BlockStmt.State> body) {
		return new STree<ConstructorDecl.State>(new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body));
	}

	public ConstructorDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation<ConstructorDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(params), TreeBase.<SNodeListState>nodeOf(throwsClause), TreeBase.<BlockStmt.State>nodeOf(body))));
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
		return location.safeTraversal(TYPE_PARAMS);
	}

	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMS, typeParams);
	}

	public ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMS, mutation);
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
		return location.safeTraversal(PARAMS);
	}

	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMS, params);
	}

	public ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMS, mutation);
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

	public static class State extends SNodeState<State> implements MemberDecl.State {

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

		@Override
		public Kind kind() {
			return Kind.ConstructorDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<ConstructorDecl.State> location) {
			return new ConstructorDecl(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return MODIFIERS;
		}

		@Override
		public STraversal lastChild() {
			return BODY;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ConstructorDecl.State state = (ConstructorDecl.State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (!typeParams.equals(state.typeParams))
				return false;
			if (!name.equals(state.name))
				return false;
			if (!params.equals(state.params))
				return false;
			if (!throwsClause.equals(state.throwsClause))
				return false;
			if (!body.equals(state.body))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			result = 37 * result + typeParams.hashCode();
			result = 37 * result + name.hashCode();
			result = 37 * result + params.hashCode();
			result = 37 * result + throwsClause.hashCode();
			result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.modifiers;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_PARAMS;
		}
	};

	private static STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.typeParams;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<ConstructorDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<ConstructorDecl.State, Name.State, Name>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.name;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PARAMS;
		}
	};

	private static STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.params;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THROWS_CLAUSE;
		}
	};

	private static STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<ConstructorDecl.State, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.throwsClause;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<SNodeListState> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<ConstructorDecl.State, BlockStmt.State, BlockStmt> BODY = new STypeSafeTraversal<ConstructorDecl.State, BlockStmt.State, BlockStmt>() {

		@Override
		protected STree<?> doTraverse(ConstructorDecl.State state) {
			return state.body;
		}

		@Override
		protected ConstructorDecl.State doRebuildParentState(ConstructorDecl.State state, STree<BlockStmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return THROWS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMS, TypeParameter.listShape),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMS, FormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);
}
