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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

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
		super(new SLocation<InitializerDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<BlockStmt.State>nodeOf(body))));
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

	/*
		public JavadocComment javadocComment() {
			return location.nodeChild(JAVADOC_COMMENT);
		}

		public InitializerDecl withJavadocComment(JavadocComment javadocComment) {
			return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
		}
	*/
	private static final STraversal<InitializerDecl.State> MODIFIERS = new STraversal<InitializerDecl.State>() {

		public STree<?> traverse(InitializerDecl.State state) {
			return state.modifiers;
		}

		public InitializerDecl.State rebuildParentState(InitializerDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<InitializerDecl.State> leftSibling(InitializerDecl.State state) {
			return null;
		}

		public STraversal<InitializerDecl.State> rightSibling(InitializerDecl.State state) {
			return BODY;
		}
	};
	private static final STraversal<InitializerDecl.State> BODY = new STraversal<InitializerDecl.State>() {

		public STree<?> traverse(InitializerDecl.State state) {
			return state.body;
		}

		public InitializerDecl.State rebuildParentState(InitializerDecl.State state, STree<?> child) {
			return state.withBody((STree) child);
		}

		public STraversal<InitializerDecl.State> leftSibling(InitializerDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<InitializerDecl.State> rightSibling(InitializerDecl.State state) {
			return null;
		}
	};
//	private static final int JAVADOC_COMMENT = 3;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(BODY)
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<InitializerDecl.State> firstChild() {
			return MODIFIERS;
		}

		public STraversal<InitializerDecl.State> lastChild() {
			return BODY;
		}

		public Tree instantiate(SLocation<InitializerDecl.State> location) {
			return new InitializerDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.InitializerDecl;
		}
	}
}
