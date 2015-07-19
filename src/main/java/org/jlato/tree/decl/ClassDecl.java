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

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ClassDecl extends TreeBase<ClassDecl.State, TypeDecl, ClassDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.ClassDecl;
	}

	protected ClassDecl(SLocation<ClassDecl.State> location) {
		super(location);
	}

	public static STree<ClassDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> typeParams, STree<SNodeOptionState> extendsClause, STree<SNodeListState> implementsClause, STree<SNodeListState> members) {
		return new STree<ClassDecl.State>(new ClassDecl.State(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	public ClassDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new SLocation<ClassDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<SNodeOptionState>nodeOf(extendsClause), TreeBase.<SNodeListState>nodeOf(implementsClause), TreeBase.<SNodeListState>nodeOf(members))));
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

	@Override
	public MemberKind memberKind() {
		return MemberKind.Type;
	}

	public TypeKind typeKind() {
		return TypeKind.Class;
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
	}

	private static STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<ClassDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.modifiers;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
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
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.name;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<Name.State> child) {
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
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.typeParams;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
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
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.extendsClause;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeOptionState> child) {
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
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.implementsClause;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
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
		protected STree<?> doTraverse(ClassDecl.State state) {
			return state.members;
		}

		@Override
		protected ClassDecl.State doRebuildParentState(ClassDecl.State state, STree<SNodeListState> child) {
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

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Class),
			child(NAME),
			child(TYPE_PARAMS, TypeParameter.listShape),
			when(childIs(EXTENDS_CLAUSE, some()),
					child(EXTENDS_CLAUSE, composite(
							token(LToken.Extends).withSpacing(space(), space()),
							element()
					))
			),
			child(IMPLEMENTS_CLAUSE, QualifiedType.implementsClauseShape),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
