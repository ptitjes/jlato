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
import org.jlato.tree.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class MethodDecl extends TreeBase<MethodDecl.State, MemberDecl, MethodDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.MethodDecl;
	}

	private MethodDecl(SLocation<MethodDecl.State> location) {
		super(location);
	}

	public static STree<MethodDecl.State> make(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<? extends Type.State> type, STree<Name.State> name, STree<SNodeListState> params, STree<SNodeListState> dims, STree<SNodeListState> throwsClause, STree<SNodeOptionState> body) {
		return new STree<MethodDecl.State>(new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body));
	}

	public MethodDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, NodeOption<BlockStmt> body) {
		super(new SLocation<MethodDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<SNodeListState>treeOf(typeParams), TreeBase.<Type.State>treeOf(type), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(params), TreeBase.<SNodeListState>treeOf(dims), TreeBase.<SNodeListState>treeOf(throwsClause), TreeBase.<SNodeOptionState>treeOf(body))));
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

	public MethodDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public MethodDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMS, mutation);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(THROWS_CLAUSE);
	}

	public MethodDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(THROWS_CLAUSE, throwsClause);
	}

	public MethodDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
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

	public static class State extends SNodeState<State> implements MemberDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<SNodeListState> typeParams;

		public final STree<? extends Type.State> type;

		public final STree<Name.State> name;

		public final STree<SNodeListState> params;

		public final STree<SNodeListState> dims;

		public final STree<SNodeListState> throwsClause;

		public final STree<SNodeOptionState> body;

		State(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<? extends Type.State> type, STree<Name.State> name, STree<SNodeListState> params, STree<SNodeListState> dims, STree<SNodeListState> throwsClause, STree<SNodeOptionState> body) {
			this.modifiers = modifiers;
			this.typeParams = typeParams;
			this.type = type;
			this.name = name;
			this.params = params;
			this.dims = dims;
			this.throwsClause = throwsClause;
			this.body = body;
		}

		public MethodDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withTypeParams(STree<SNodeListState> typeParams) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withType(STree<? extends Type.State> type) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withName(STree<Name.State> name) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withParams(STree<SNodeListState> params) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withDims(STree<SNodeListState> dims) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withThrowsClause(STree<SNodeListState> throwsClause) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		public MethodDecl.State withBody(STree<SNodeOptionState> body) {
			return new MethodDecl.State(modifiers, typeParams, type, name, params, dims, throwsClause, body);
		}

		@Override
		public Kind kind() {
			return Kind.MethodDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<MethodDecl.State> location) {
			return new MethodDecl(location);
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
			State state = (State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (!typeParams.equals(state.typeParams))
				return false;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!params.equals(state.params))
				return false;
			if (!dims.equals(state.dims))
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
			if (type != null) result = 37 * result + type.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + params.hashCode();
			result = 37 * result + dims.hashCode();
			result = 37 * result + throwsClause.hashCode();
			result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.modifiers;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.typeParams;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, Type.State, Type> TYPE = new STypeSafeTraversal<MethodDecl.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.type;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<MethodDecl.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.name;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PARAMS;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.params;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.dims;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THROWS_CLAUSE;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<MethodDecl.State, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.throwsClause;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeListState> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<MethodDecl.State, SNodeOptionState, NodeOption<BlockStmt>> BODY = new STypeSafeTraversal<MethodDecl.State, SNodeOptionState, NodeOption<BlockStmt>>() {

		@Override
		public STree<?> doTraverse(MethodDecl.State state) {
			return state.body;
		}

		@Override
		public MethodDecl.State doRebuildParentState(MethodDecl.State state, STree<SNodeOptionState> child) {
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

	public static final LexicalShape shape = composite(
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
			child(BODY, alternative(some(),
					element().withSpacingBefore(space()),
					token(LToken.SemiColon)
			))
	);
}
