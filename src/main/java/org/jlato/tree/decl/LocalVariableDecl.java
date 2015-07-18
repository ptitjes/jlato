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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class LocalVariableDecl extends TreeBase<LocalVariableDecl.State, Decl, LocalVariableDecl> implements Decl {

	public final static SKind<LocalVariableDecl.State> kind = new SKind<LocalVariableDecl.State>() {

	};

	protected LocalVariableDecl(SLocation<LocalVariableDecl.State> location) {
		super(location);
	}

	public static STree<LocalVariableDecl.State> make(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		return new STree<LocalVariableDecl.State>(kind, new LocalVariableDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Type.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(variables)));
	}

	public LocalVariableDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new SLocation<LocalVariableDecl.State>(make(modifiers, type, variables)));
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

	private static final STraversal<LocalVariableDecl.State> MODIFIERS = new STraversal<LocalVariableDecl.State>() {

		public STree<?> traverse(LocalVariableDecl.State state) {
			return state.modifiers;
		}

		public LocalVariableDecl.State rebuildParentState(LocalVariableDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<LocalVariableDecl.State> leftSibling(LocalVariableDecl.State state) {
			return null;
		}

		public STraversal<LocalVariableDecl.State> rightSibling(LocalVariableDecl.State state) {
			return TYPE;
		}
	};
	private static final STraversal<LocalVariableDecl.State> TYPE = new STraversal<LocalVariableDecl.State>() {

		public STree<?> traverse(LocalVariableDecl.State state) {
			return state.type;
		}

		public LocalVariableDecl.State rebuildParentState(LocalVariableDecl.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<LocalVariableDecl.State> leftSibling(LocalVariableDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<LocalVariableDecl.State> rightSibling(LocalVariableDecl.State state) {
			return VARIABLES;
		}
	};
	private static final STraversal<LocalVariableDecl.State> VARIABLES = new STraversal<LocalVariableDecl.State>() {

		public STree<?> traverse(LocalVariableDecl.State state) {
			return state.variables;
		}

		public LocalVariableDecl.State rebuildParentState(LocalVariableDecl.State state, STree<?> child) {
			return state.withVariables((STree) child);
		}

		public STraversal<LocalVariableDecl.State> leftSibling(LocalVariableDecl.State state) {
			return TYPE;
		}

		public STraversal<LocalVariableDecl.State> rightSibling(LocalVariableDecl.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			child(VARIABLES, VariableDeclarator.listShape).withSpacingBefore(space())
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Type.State> type;

		public final STree<SNodeListState> variables;

		State(STree<SNodeListState> modifiers, STree<Type.State> type, STree<SNodeListState> variables) {
			this.modifiers = modifiers;
			this.type = type;
			this.variables = variables;
		}

		public LocalVariableDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		public LocalVariableDecl.State withType(STree<Type.State> type) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		public LocalVariableDecl.State withVariables(STree<SNodeListState> variables) {
			return new LocalVariableDecl.State(modifiers, type, variables);
		}

		public STraversal<LocalVariableDecl.State> firstChild() {
			return null;
		}

		public STraversal<LocalVariableDecl.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<LocalVariableDecl.State> location) {
			return new LocalVariableDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
