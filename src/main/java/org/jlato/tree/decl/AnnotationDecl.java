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
import org.jlato.printer.SpacingConstraint;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class AnnotationDecl extends TreeBase<AnnotationDecl.State, TypeDecl, AnnotationDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	private AnnotationDecl(SLocation<AnnotationDecl.State> location) {
		super(location);
	}

	public static STree<AnnotationDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> members) {
		return new STree<AnnotationDecl.State>(new AnnotationDecl.State(modifiers, name, members));
	}

	public AnnotationDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<MemberDecl> members) {
		super(new SLocation<AnnotationDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(members))));
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

	public static class State extends SNodeState<State> implements TypeDecl.State {

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

		@Override
		public Kind kind() {
			return Kind.AnnotationDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<AnnotationDecl.State> location) {
			return new AnnotationDecl(location);
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
			return MEMBERS;
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
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!members.equals(state.members))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + modifiers.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + members.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<AnnotationDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<AnnotationDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(AnnotationDecl.State state) {
			return state.modifiers;
		}

		@Override
		public AnnotationDecl.State doRebuildParentState(AnnotationDecl.State state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<AnnotationDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<AnnotationDecl.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(AnnotationDecl.State state) {
			return state.name;
		}

		@Override
		public AnnotationDecl.State doRebuildParentState(AnnotationDecl.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	private static STypeSafeTraversal<AnnotationDecl.State, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<AnnotationDecl.State, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public STree<?> doTraverse(AnnotationDecl.State state) {
			return state.members;
		}

		@Override
		public AnnotationDecl.State doRebuildParentState(AnnotationDecl.State state, STree<SNodeListState> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.At), token(LToken.Interface).withSpacingAfter(space()),
			child(NAME),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
