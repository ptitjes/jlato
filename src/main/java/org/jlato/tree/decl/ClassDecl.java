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
import org.jlato.tree.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ClassDecl extends TreeBase<ClassDecl.State, TypeDecl, ClassDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.ClassDecl;
	}

	private ClassDecl(SLocation<ClassDecl.State> location) {
		super(location);
	}

	public static STree<ClassDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> typeParams, STree<SNodeOptionState> extendsClause, STree<SNodeListState> implementsClause, STree<SNodeListState> members) {
		return new STree<ClassDecl.State>(new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	public ClassDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new SLocation<ClassDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(typeParams), TreeBase.<SNodeOptionState>treeOf(extendsClause), TreeBase.<SNodeListState>treeOf(implementsClause), TreeBase.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public ClassDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public ClassDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public ClassDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ClassDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMS);
	}

	public ClassDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMS, typeParams);
	}

	public ClassDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMS, mutation);
	}

	public NodeOption<QualifiedType> extendsClause() {
		return location.safeTraversal(EXTENDS_CLAUSE);
	}

	public ClassDecl withExtendsClause(NodeOption<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(EXTENDS_CLAUSE, extendsClause);
	}

	public ClassDecl withExtendsClause(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(EXTENDS_CLAUSE, mutation);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(IMPLEMENTS_CLAUSE);
	}

	public ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public ClassDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(IMPLEMENTS_CLAUSE, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(MEMBERS);
	}

	public ClassDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(MEMBERS, members);
	}

	public ClassDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(MEMBERS, mutation);
	}

	public static class State extends SNodeState<State> implements TypeDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<Name.State> name;

		public final STree<SNodeListState> typeParams;

		public final STree<SNodeOptionState> extendsClause;

		public final STree<SNodeListState> implementsClause;

		public final STree<SNodeListState> members;

		State(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> typeParams, STree<SNodeOptionState> extendsClause, STree<SNodeListState> implementsClause, STree<SNodeListState> members) {
			this.modifiers = modifiers;
			this.name = name;
			this.typeParams = typeParams;
			this.extendsClause = extendsClause;
			this.implementsClause = implementsClause;
			this.members = members;
		}

		public ClassDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		public ClassDecl.State withName(STree<Name.State> name) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		public ClassDecl.State withTypeParams(STree<SNodeListState> typeParams) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		public ClassDecl.State withExtendsClause(STree<SNodeOptionState> extendsClause) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		public ClassDecl.State withImplementsClause(STree<SNodeListState> implementsClause) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		public ClassDecl.State withMembers(STree<SNodeListState> members) {
			return new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members);
		}

		@Override
		public Kind kind() {
			return Kind.ClassDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<ClassDecl.State> location) {
			return new ClassDecl(location);
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
			if (!typeParams.equals(state.typeParams))
				return false;
			if (!extendsClause.equals(state.extendsClause))
				return false;
			if (!implementsClause.equals(state.implementsClause))
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
			result = 37 * result + typeParams.hashCode();
			result = 37 * result + extendsClause.hashCode();
			result = 37 * result + implementsClause.hashCode();
			result = 37 * result + members.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.modifiers;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<ClassDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<ClassDecl.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.name;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_PARAMS;
		}
	};

	private static STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.typeParams;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXTENDS_CLAUSE;
		}
	};

	private static STypeSafeTraversal<ClassDecl.State, SNodeOptionState, NodeOption<QualifiedType>> EXTENDS_CLAUSE = new STypeSafeTraversal<ClassDecl.State, SNodeOptionState, NodeOption<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.extendsClause;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeOptionState> child) {
			return state.withExtendsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}
	};

	private static STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<QualifiedType>> IMPLEMENTS_CLAUSE = new STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.implementsClause;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
			return state.withImplementsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXTENDS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	private static STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public STree<?> doTraverse(ClassDecl.State state) {
			return state.members;
		}

		@Override
		public ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			keyword(LToken.Class),
			child(NAME),
			child(TYPE_PARAMS, TypeParameter.listShape),
			child(EXTENDS_CLAUSE, when(some(),
					composite(keyword(LToken.Extends), element())
			)),
			child(IMPLEMENTS_CLAUSE, QualifiedType.implementsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
