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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnknownType;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.kind;
import static org.jlato.internal.shapes.LSCondition.not;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class FormalParameter extends TreeBase<FormalParameter.State, Tree, FormalParameter> implements Tree {

	public final static SKind<FormalParameter.State> kind = new SKind<FormalParameter.State>() {
		public FormalParameter instantiate(SLocation<FormalParameter.State> location) {
			return new FormalParameter(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private FormalParameter(SLocation<FormalParameter.State> location) {
		super(location);
	}

	public static STree<FormalParameter.State> make(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		return new STree<FormalParameter.State>(kind, new FormalParameter.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Type.State>nodeOf(type), isVarArgs, TreeBase.<VariableDeclaratorId.State>nodeOf(id)));
	}

	public FormalParameter(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new SLocation<FormalParameter.State>(make(modifiers, type, isVarArgs, id)));
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
		return location.<Boolean>data(VAR_ARGS);
	}

	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.withData(VAR_ARGS, isVarArgs);
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

	private static final STraversal<FormalParameter.State> MODIFIERS = new STraversal<FormalParameter.State>() {

		public STree<?> traverse(FormalParameter.State state) {
			return state.modifiers;
		}

		public FormalParameter.State rebuildParentState(FormalParameter.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<FormalParameter.State> leftSibling(FormalParameter.State state) {
			return null;
		}

		public STraversal<FormalParameter.State> rightSibling(FormalParameter.State state) {
			return TYPE;
		}
	};
	private static final STraversal<FormalParameter.State> TYPE = new STraversal<FormalParameter.State>() {

		public STree<?> traverse(FormalParameter.State state) {
			return state.type;
		}

		public FormalParameter.State rebuildParentState(FormalParameter.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<FormalParameter.State> leftSibling(FormalParameter.State state) {
			return MODIFIERS;
		}

		public STraversal<FormalParameter.State> rightSibling(FormalParameter.State state) {
			return ID;
		}
	};
	private static final STraversal<FormalParameter.State> ID = new STraversal<FormalParameter.State>() {

		public STree<?> traverse(FormalParameter.State state) {
			return state.id;
		}

		public FormalParameter.State rebuildParentState(FormalParameter.State state, STree<?> child) {
			return state.withId((STree) child);
		}

		public STraversal<FormalParameter.State> leftSibling(FormalParameter.State state) {
			return TYPE;
		}

		public STraversal<FormalParameter.State> rightSibling(FormalParameter.State state) {
			return null;
		}
	};

	private static final SProperty<FormalParameter.State> VAR_ARGS = new SProperty<FormalParameter.State>() {

		public Object retrieve(FormalParameter.State state) {
			return state.isVarArgs;
		}

		public FormalParameter.State rebuildParentState(FormalParameter.State state, Object value) {
			return state.withVarArgs((Boolean) value);
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			dataOption(VAR_ARGS, token(LToken.Ellipsis)),
			when(not(childIs(TYPE, kind(UnknownType.kind))), none().withSpacingAfter(space())),
			child(ID)
	);

	public static final LexicalShape listShape = list(true,
			none(),
			token(LToken.Comma).withSpacingAfter(space()),
			none()
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Type.State> type;

		public final boolean isVarArgs;

		public final STree<VariableDeclaratorId.State> id;

		State(STree<SNodeListState> modifiers, STree<Type.State> type, boolean isVarArgs, STree<VariableDeclaratorId.State> id) {
			this.modifiers = modifiers;
			this.type = type;
			this.isVarArgs = isVarArgs;
			this.id = id;
		}

		public FormalParameter.State withModifiers(STree<SNodeListState> modifiers) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State withType(STree<Type.State> type) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State withVarArgs(boolean isVarArgs) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public FormalParameter.State withId(STree<VariableDeclaratorId.State> id) {
			return new FormalParameter.State(modifiers, type, isVarArgs, id);
		}

		public STraversal<FormalParameter.State> firstChild() {
			return null;
		}

		public STraversal<FormalParameter.State> lastChild() {
			return null;
		}
	}
}
