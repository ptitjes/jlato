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
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class InitializerDecl extends TreeBase<InitializerDecl.State, MemberDecl, InitializerDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.InitializerDecl;
	}

	private InitializerDecl(SLocation<InitializerDecl.State> location) {
		super(location);
	}

	public static STree<InitializerDecl.State> make(STree<SNodeListState> modifiers, STree<BlockStmt.State> body) {
		return new STree<InitializerDecl.State>(new InitializerDecl.State(modifiers, body));
	}

	public InitializerDecl(NodeList<ExtendedModifier> modifiers, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation<InitializerDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<BlockStmt.State>treeOf(body))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Initializer;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public BlockStmt body() {
		return location.safeTraversal(BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public InitializerDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public static class State extends SNodeState<State> implements MemberDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<BlockStmt.State> body;

		State(STree<SNodeListState> modifiers, STree<BlockStmt.State> body) {
			this.modifiers = modifiers;
			this.body = body;
		}

		public InitializerDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new InitializerDecl.State(modifiers, body);
		}

		public InitializerDecl.State withBody(STree<BlockStmt.State> body) {
			return new InitializerDecl.State(modifiers, body);
		}

		@Override
		public Kind kind() {
			return Kind.InitializerDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<InitializerDecl.State> location) {
			return new InitializerDecl(location);
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
			if (body == null ? state.body != null : !body.equals(state.body))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			if (body != null) result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<InitializerDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<InitializerDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.modifiers;
		}

		@Override
		public InitializerDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<InitializerDecl.State, BlockStmt.State, BlockStmt> BODY = new STypeSafeTraversal<InitializerDecl.State, BlockStmt.State, BlockStmt>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.body;
		}

		@Override
		public InitializerDecl.State doRebuildParentState(State state, STree<BlockStmt.State> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(BODY)
	);
}
