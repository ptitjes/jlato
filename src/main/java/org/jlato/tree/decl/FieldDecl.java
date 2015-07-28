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

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class FieldDecl extends TreeBase<FieldDecl.State, MemberDecl, FieldDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.FieldDecl;
	}

	private FieldDecl(SLocation<FieldDecl.State> location) {
		super(location);
	}

	public static STree<FieldDecl.State> make(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<SNodeListState> variables) {
		return new STree<FieldDecl.State>(new FieldDecl.State(modifiers, type, variables));
	}

	public FieldDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation<FieldDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Type.State>treeOf(type), TreeBase.<SNodeListState>treeOf(variables))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Field;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public FieldDecl withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public FieldDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(VARIABLES);
	}

	public FieldDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(VARIABLES, variables);
	}

	public FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(VARIABLES, mutation);
	}

	public static class State extends SNodeState<State> implements MemberDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<? extends Type.State> type;

		public final STree<SNodeListState> variables;

		State(STree<SNodeListState> modifiers, STree<? extends Type.State> type, STree<SNodeListState> variables) {
			this.modifiers = modifiers;
			this.type = type;
			this.variables = variables;
		}

		public FieldDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		public FieldDecl.State withType(STree<? extends Type.State> type) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		public FieldDecl.State withVariables(STree<SNodeListState> variables) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		@Override
		public Kind kind() {
			return Kind.FieldDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<FieldDecl.State> location) {
			return new FieldDecl(location);
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
			FieldDecl.State state = (FieldDecl.State) o;
			if (!modifiers.equals(state.modifiers))
				return false;
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (!variables.equals(state.variables))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			if (type != null) result = 37 * result + type.hashCode();
			result = 37 * result + variables.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<FieldDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<FieldDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.modifiers;
		}

		@Override
		public FieldDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<FieldDecl.State, Type.State, Type> TYPE = new STypeSafeTraversal<FieldDecl.State, Type.State, Type>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.type;
		}

		@Override
		public FieldDecl.State doRebuildParentState(State state, STree<Type.State> child) {
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

	private static STypeSafeTraversal<FieldDecl.State, SNodeListState, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<FieldDecl.State, SNodeListState, NodeList<VariableDeclarator>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.variables;
		}

		@Override
		public FieldDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
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
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, VariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
	);
}
