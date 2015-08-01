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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LSCondition.empty;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_BetweenConstants;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class EnumDecl extends TreeBase<EnumDecl.State, TypeDecl, EnumDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.EnumDecl;
	}

	private EnumDecl(SLocation<EnumDecl.State> location) {
		super(location);
	}

	public static STree<EnumDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> implementsClause, STree<SNodeListState> enumConstants, boolean trailingComma, STree<SNodeListState> members) {
		return new STree<EnumDecl.State>(new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members));
	}

	public EnumDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new SLocation<EnumDecl.State>(make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(implementsClause), TreeBase.<SNodeListState>treeOf(enumConstants), trailingComma, TreeBase.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public EnumDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public EnumDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Type;
	}

	public TypeKind typeKind() {
		return TypeKind.Enum;
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public EnumDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public EnumDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(IMPLEMENTS_CLAUSE);
	}

	public EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public EnumDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(IMPLEMENTS_CLAUSE, mutation);
	}

	public NodeList<EnumConstantDecl> enumConstants() {
		return location.safeTraversal(ENUM_CONSTANTS);
	}

	public EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.safeTraversalReplace(ENUM_CONSTANTS, enumConstants);
	}

	public EnumDecl withEnumConstants(Mutation<NodeList<EnumConstantDecl>> mutation) {
		return location.safeTraversalMutate(ENUM_CONSTANTS, mutation);
	}

	public boolean trailingComma() {
		return location.safeProperty(TRAILING_COMMA);
	}

	public EnumDecl withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(TRAILING_COMMA, trailingComma);
	}

	public EnumDecl withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(TRAILING_COMMA, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(MEMBERS);
	}

	public EnumDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(MEMBERS, members);
	}

	public EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(MEMBERS, mutation);
	}

	public static class State extends SNodeState<State> implements TypeDecl.State {

		public final STree<SNodeListState> modifiers;

		public final STree<Name.State> name;

		public final STree<SNodeListState> implementsClause;

		public final STree<SNodeListState> enumConstants;

		public final boolean trailingComma;

		public final STree<SNodeListState> members;

		State(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> implementsClause, STree<SNodeListState> enumConstants, boolean trailingComma, STree<SNodeListState> members) {
			this.modifiers = modifiers;
			this.name = name;
			this.implementsClause = implementsClause;
			this.enumConstants = enumConstants;
			this.trailingComma = trailingComma;
			this.members = members;
		}

		public EnumDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		public EnumDecl.State withName(STree<Name.State> name) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		public EnumDecl.State withImplementsClause(STree<SNodeListState> implementsClause) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		public EnumDecl.State withEnumConstants(STree<SNodeListState> enumConstants) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		public EnumDecl.State withTrailingComma(boolean trailingComma) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		public EnumDecl.State withMembers(STree<SNodeListState> members) {
			return new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members);
		}

		@Override
		public Kind kind() {
			return Kind.EnumDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<EnumDecl.State> location) {
			return new EnumDecl(location);
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
			if (!implementsClause.equals(state.implementsClause))
				return false;
			if (!enumConstants.equals(state.enumConstants))
				return false;
			if (trailingComma != state.trailingComma)
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
			result = 37 * result + implementsClause.hashCode();
			result = 37 * result + enumConstants.hashCode();
			result = 37 * result + (trailingComma ? 1 : 0);
			result = 37 * result + members.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.modifiers;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<EnumDecl.State, Name.State, Name> NAME = new STypeSafeTraversal<EnumDecl.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}
	};

	private static STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<QualifiedType>> IMPLEMENTS_CLAUSE = new STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.implementsClause;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withImplementsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ENUM_CONSTANTS;
		}
	};

	private static STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<EnumConstantDecl>> ENUM_CONSTANTS = new STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<EnumConstantDecl>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.enumConstants;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withEnumConstants(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	private static STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<EnumDecl.State, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.members;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ENUM_CONSTANTS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<EnumDecl.State, Boolean> TRAILING_COMMA = new STypeSafeProperty<EnumDecl.State, Boolean>() {

		@Override
		public Boolean doRetrieve(State state) {
			return state.trailingComma;
		}

		@Override
		public EnumDecl.State doRebuildParentState(State state, Boolean value) {
			return state.withTrailingComma(value);
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			keyword(LToken.Enum),
			child(NAME),
			child(IMPLEMENTS_CLAUSE, QualifiedType.implementsClauseShape),
			token(LToken.BraceLeft)
					.withSpacingBefore(space())
					.withIndentationAfter(indent(TYPE_BODY)),
			child(ENUM_CONSTANTS, EnumConstantDecl.listShape),
			when(data(TRAILING_COMMA), token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants))),
			when(childIs(MEMBERS, empty()),
					alternative(childIs(ENUM_CONSTANTS, empty()),
							none().withSpacingAfter(newLine()),
							none().withSpacingAfter(spacing(EnumBody_AfterConstants))
					)
			),
			when(childIs(MEMBERS, not(empty())),
					token(LToken.SemiColon).withSpacingAfter(spacing(EnumBody_AfterConstants))
			),
			child(MEMBERS, MemberDecl.membersShape),
			token(LToken.BraceRight)
					.withIndentationBefore(unIndent(TYPE_BODY))
	);
}
