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
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LSCondition.not;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class FormalParameter extends TreeBase<FormalParameter.State, Tree, FormalParameter> implements Tree {

	public Kind kind() {
		return Kind.FormalParameter;
	}

	private FormalParameter(SLocation<FormalParameter.State> location) {
		super(location);
	}

	public static STree<FormalParameter.State> make(STree<SNodeListState> modifiers, STree<? extends Type.State> type, boolean isVarArgs, STree<VariableDeclaratorId.State> id) {
		return new STree<FormalParameter.State>(new FormalParameter.State(modifiers, type, isVarArgs, id));
	}

	public FormalParameter(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new SLocation<FormalParameter.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Type.State>treeOf(type), isVarArgs, TreeBase.<VariableDeclaratorId.State>treeOf(id))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public FormalParameter withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public FormalParameter withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public boolean isVarArgs() {
		return location.safeProperty(VAR_ARGS);
	}

	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.safePropertyReplace(VAR_ARGS, (Boolean) isVarArgs);
	}

	public FormalParameter setVarArgs(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(VAR_ARGS, mutation);
	}

	public VariableDeclaratorId id() {
		return location.safeTraversal(ID);
	}

	public FormalParameter withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(ID, id);
	}

	public FormalParameter withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeListState> modifiers;

		public final STree<? extends Type.State> type;

		public final boolean isVarArgs;

		public final STree<VariableDeclaratorId.State> id;

		State(STree<SNodeListState> modifiers, STree<? extends Type.State> type, boolean isVarArgs, STree<VariableDeclaratorId.State> id) {
			this.modifiers = modifiers;
			this.type = type;
			this.isVarArgs = isVarArgs;
			this.id = id;
		}

		public FormalParameter.State withModifiers(STree<SNodeListState> modifiers) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State withType(STree<? extends Type.State> type) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State setVarArgs(boolean isVarArgs) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State withId(STree<VariableDeclaratorId.State> id) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		@Override
		public Kind kind() {
			return Kind.FormalParameter;
		}

		@Override
		protected Tree doInstantiate(SLocation<FormalParameter.State> location) {
			return new FormalParameter(location);
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
			return ID;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			FormalParameter.State state = (FormalParameter.State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (isVarArgs != state.isVarArgs)
				return false;
			if (id == null ? state.id != null : !id.equals(state.id))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			if (type != null) result = 37 * result + type.hashCode();
			result = 37 * result + (isVarArgs ? 1 : 0);
			if (id != null) result = 37 * result + id.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<FormalParameter.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<FormalParameter.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.modifiers;
		}

		@Override
		public FormalParameter.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	private static STypeSafeTraversal<FormalParameter.State, Type.State, Type> TYPE = new STypeSafeTraversal<FormalParameter.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.type;
		}

		@Override
		public FormalParameter.State doRebuildParentState(State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ID;
		}
	};

	private static STypeSafeTraversal<FormalParameter.State, VariableDeclaratorId.State, VariableDeclaratorId> ID = new STypeSafeTraversal<FormalParameter.State, VariableDeclaratorId.State, VariableDeclaratorId>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.id;
		}

		@Override
		public FormalParameter.State doRebuildParentState(State state, STree<VariableDeclaratorId.State> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<FormalParameter.State, Boolean> VAR_ARGS = new STypeSafeProperty<FormalParameter.State, Boolean>() {

		@Override
		public Boolean doRetrieve(State state) {
			return state.isVarArgs;
		}

		@Override
		public FormalParameter.State doRebuildParentState(State state, Boolean value) {
			return state.setVarArgs(value);
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			when(data(VAR_ARGS), token(LToken.Ellipsis)),
			when(not(childIs(TYPE, LSCondition.kind(Kind.UnknownType))), none().withSpacingAfter(space())),
			child(ID)
	);

	public static final LexicalShape listShape = list(true,
			none(),
			token(LToken.Comma).withSpacingAfter(space()),
			none()
	);
}
