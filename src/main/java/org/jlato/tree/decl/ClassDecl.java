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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ClassDecl extends TreeBase<ClassDecl.State, TypeDecl, ClassDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.ClassDecl;
	}

	protected ClassDecl(SLocation<ClassDecl.State> location) {
		super(location);
	}

	public static STree<ClassDecl.State> make(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		return new STree<ClassDecl.State>(new ClassDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<SNodeOptionState>nodeOf(extendsClause), TreeBase.<SNodeListState>nodeOf(implementsClause), TreeBase.<SNodeListState>nodeOf(members)));
	}

	public ClassDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new SLocation<ClassDecl.State>(make(modifiers, name, typeParams, extendsClause, implementsClause, members)));
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

	private static final STraversal<ClassDecl.State> MODIFIERS = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.modifiers;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return null;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<ClassDecl.State> NAME = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.name;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
			return TYPE_PARAMS;
		}
	};
	private static final STraversal<ClassDecl.State> TYPE_PARAMS = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.typeParams;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withTypeParams((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return NAME;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
			return EXTENDS_CLAUSE;
		}
	};
	private static final STraversal<ClassDecl.State> EXTENDS_CLAUSE = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.extendsClause;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withExtendsClause((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return TYPE_PARAMS;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
			return IMPLEMENTS_CLAUSE;
		}
	};
	private static final STraversal<ClassDecl.State> IMPLEMENTS_CLAUSE = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.implementsClause;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withImplementsClause((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return EXTENDS_CLAUSE;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
			return MEMBERS;
		}
	};
	private static final STraversal<ClassDecl.State> MEMBERS = new STraversal<ClassDecl.State>() {

		public STree<?> traverse(ClassDecl.State state) {
			return state.members;
		}

		public ClassDecl.State rebuildParentState(ClassDecl.State state, STree<?> child) {
			return state.withMembers((STree) child);
		}

		public STraversal<ClassDecl.State> leftSibling(ClassDecl.State state) {
			return IMPLEMENTS_CLAUSE;
		}

		public STraversal<ClassDecl.State> rightSibling(ClassDecl.State state) {
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

	public static class State extends SNodeState<State> {

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

		public STraversal<ClassDecl.State> firstChild() {
			return null;
		}

		public STraversal<ClassDecl.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ClassDecl.State> location) {
			return new ClassDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ClassDecl;
		}
	}
}
