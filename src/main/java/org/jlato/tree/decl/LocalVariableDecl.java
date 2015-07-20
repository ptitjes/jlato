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
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.printer.SpacingConstraint.space;

public class LocalVariableDecl extends TreeBase<LocalVariableDecl.State, Decl, LocalVariableDecl> implements Decl {

	public Kind kind() {
		return Kind.LocalVariableDecl;
	}

	protected LocalVariableDecl(SLocation<LocalVariableDecl.State> location) {
		super(location);
	}

	public static STree<LocalVariableDecl.State> make(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<SNodeListState> variables) {
		return new STree<LocalVariableDecl.State>(new LocalVariableDecl.State(modifiers, type, variables));
	}

	public LocalVariableDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new SLocation<LocalVariableDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Type.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(variables))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public LocalVariableDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public LocalVariableDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public LocalVariableDecl withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public LocalVariableDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(VARIABLES);
	}

	public LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(VARIABLES, variables);
	}

	public LocalVariableDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(VARIABLES, mutation);
	}

	public static class State extends SNodeState<State> implements Decl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<? extends Type.State> type;

		public final STree<SNodeListState> variables;

		State(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<SNodeListState> variables) {
			this.modifiers = modifiers;
			this.type = type;
			this.variables = variables;
		}

		public LocalVariableDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		public LocalVariableDecl.State withType(STree<? extends Type.State> type) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		public LocalVariableDecl.State withVariables(STree<SNodeListState> variables) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		@Override
		public Kind kind() {
			return Kind.LocalVariableDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<LocalVariableDecl.State> location) {
			return new LocalVariableDecl(location);
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
			return VARIABLES;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			LocalVariableDecl.State state = (LocalVariableDecl.State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (!type.equals(state.type))
				return false;
			if (!variables.equals(state.variables))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			result = 37 * result + type.hashCode();
			result = 37 * result + variables.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<LocalVariableDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<LocalVariableDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		protected STree<?> doTraverse(LocalVariableDecl.State state) {
			return state.modifiers;
		}

		@Override
		protected LocalVariableDecl.State doRebuildParentState(LocalVariableDecl.State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<LocalVariableDecl.State, Type.State, Type> TYPE = new STypeSafeTraversal<LocalVariableDecl.State, Type.State, Type>() {

		@Override
		protected STree<?> doTraverse(LocalVariableDecl.State state) {
			return state.type;
		}

		@Override
		protected LocalVariableDecl.State doRebuildParentState(LocalVariableDecl.State state, STree<Type.State> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return VARIABLES;
		}
	};

	private static STypeSafeTraversal<LocalVariableDecl.State, SNodeListState, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<LocalVariableDecl.State, SNodeListState, NodeList<VariableDeclarator>>() {

		@Override
		protected STree<?> doTraverse(LocalVariableDecl.State state) {
			return state.variables;
		}

		@Override
		protected LocalVariableDecl.State doRebuildParentState(LocalVariableDecl.State state, STree<SNodeListState> child) {
			return state.withVariables(child);
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

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			child(VARIABLES, VariableDeclarator.listShape).withSpacingBefore(space())
	);
}
