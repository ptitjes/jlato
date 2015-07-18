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
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class FieldDecl extends TreeBase<FieldDecl.State, MemberDecl, FieldDecl> implements MemberDecl {

	public final static SKind<FieldDecl.State> kind = new SKind<FieldDecl.State>() {
		public FieldDecl instantiate(SLocation<FieldDecl.State> location) {
			return new FieldDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private FieldDecl(SLocation<FieldDecl.State> location) {
		super(location);
	}

	public static STree<FieldDecl.State> make(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		return new STree<FieldDecl.State>(kind, new FieldDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Type.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(variables)));
	}

	public FieldDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation<FieldDecl.State>(make(modifiers, type, variables)));
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
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public FieldDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final STraversal<FieldDecl.State> MODIFIERS = new STraversal<FieldDecl.State>() {

		public STree<?> traverse(FieldDecl.State state) {
			return state.modifiers;
		}

		public FieldDecl.State rebuildParentState(FieldDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<FieldDecl.State> leftSibling(FieldDecl.State state) {
			return null;
		}

		public STraversal<FieldDecl.State> rightSibling(FieldDecl.State state) {
			return TYPE;
		}
	};
	private static final STraversal<FieldDecl.State> TYPE = new STraversal<FieldDecl.State>() {

		public STree<?> traverse(FieldDecl.State state) {
			return state.type;
		}

		public FieldDecl.State rebuildParentState(FieldDecl.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<FieldDecl.State> leftSibling(FieldDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<FieldDecl.State> rightSibling(FieldDecl.State state) {
			return VARIABLES;
		}
	};
	private static final STraversal<FieldDecl.State> VARIABLES = new STraversal<FieldDecl.State>() {

		public STree<?> traverse(FieldDecl.State state) {
			return state.variables;
		}

		public FieldDecl.State rebuildParentState(FieldDecl.State state, STree<?> child) {
			return state.withVariables((STree) child);
		}

		public STraversal<FieldDecl.State> leftSibling(FieldDecl.State state) {
			return TYPE;
		}

		public STraversal<FieldDecl.State> rightSibling(FieldDecl.State state) {
			return null;
		}
	};
//	private static final int JAVADOC_COMMENT = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, VariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
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

		public FieldDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		public FieldDecl.State withType(STree<Type.State> type) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		public FieldDecl.State withVariables(STree<SNodeListState> variables) {
			return new FieldDecl.State(modifiers, type, variables);
		}

		public STraversal<FieldDecl.State> firstChild() {
			return null;
		}

		public STraversal<FieldDecl.State> lastChild() {
			return null;
		}
	}
}
