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
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class AnnotationDecl extends TreeBase<AnnotationDecl.State, TypeDecl, AnnotationDecl> implements TypeDecl {

	public final static SKind<AnnotationDecl.State> kind = new SKind<AnnotationDecl.State>() {
		public AnnotationDecl instantiate(SLocation<AnnotationDecl.State> location) {
			return new AnnotationDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected AnnotationDecl(SLocation<AnnotationDecl.State> location) {
		super(location);
	}

	public static STree<AnnotationDecl.State> make(NodeList<ExtendedModifier> modifiers, Name name, NodeList<MemberDecl> members) {
		return new STree<AnnotationDecl.State>(kind, new AnnotationDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(members)));
	}

	public AnnotationDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<MemberDecl> members) {
		super(new SLocation<AnnotationDecl.State>(make(modifiers, name, members)));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public AnnotationDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public AnnotationDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Type;
	}

	public TypeKind typeKind() {
		return TypeKind.AnnotationType;
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public AnnotationDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public AnnotationDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(MEMBERS);
	}

	public AnnotationDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(MEMBERS, members);
	}

	public AnnotationDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(MEMBERS, mutation);
	}

	private static final STraversal<AnnotationDecl.State> MODIFIERS = new STraversal<AnnotationDecl.State>() {

		public STree<?> traverse(AnnotationDecl.State state) {
			return state.modifiers;
		}

		public AnnotationDecl.State rebuildParentState(AnnotationDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<AnnotationDecl.State> leftSibling(AnnotationDecl.State state) {
			return null;
		}

		public STraversal<AnnotationDecl.State> rightSibling(AnnotationDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<AnnotationDecl.State> NAME = new STraversal<AnnotationDecl.State>() {

		public STree<?> traverse(AnnotationDecl.State state) {
			return state.name;
		}

		public AnnotationDecl.State rebuildParentState(AnnotationDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<AnnotationDecl.State> leftSibling(AnnotationDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<AnnotationDecl.State> rightSibling(AnnotationDecl.State state) {
			return MEMBERS;
		}
	};
	private static final STraversal<AnnotationDecl.State> MEMBERS = new STraversal<AnnotationDecl.State>() {

		public STree<?> traverse(AnnotationDecl.State state) {
			return state.members;
		}

		public AnnotationDecl.State rebuildParentState(AnnotationDecl.State state, STree<?> child) {
			return state.withMembers((STree) child);
		}

		public STraversal<AnnotationDecl.State> leftSibling(AnnotationDecl.State state) {
			return NAME;
		}

		public STraversal<AnnotationDecl.State> rightSibling(AnnotationDecl.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			token(LToken.At), token(LToken.Interface),
			child(NAME),
			child(MEMBERS, MemberDecl.bodyShape)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Name.State> name;

		public final STree<SNodeListState> members;

		State(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> members) {
			this.modifiers = modifiers;
			this.name = name;
			this.members = members;
		}

		public AnnotationDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new AnnotationDecl.State(modifiers, name, members);
		}

		public AnnotationDecl.State withName(STree<Name.State> name) {
			return new AnnotationDecl.State(modifiers, name, members);
		}

		public AnnotationDecl.State withMembers(STree<SNodeListState> members) {
			return new AnnotationDecl.State(modifiers, name, members);
		}

		public STraversal<AnnotationDecl.State> firstChild() {
			return null;
		}

		public STraversal<AnnotationDecl.State> lastChild() {
			return null;
		}
	}
}
