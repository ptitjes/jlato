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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_BetweenConstants;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class EnumDecl extends TreeBase<EnumDecl.State, TypeDecl, EnumDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.EnumDecl;
	}

	protected EnumDecl(SLocation<EnumDecl.State> location) {
		super(location);
	}

	public static STree<EnumDecl.State> make(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		return new STree<EnumDecl.State>(new EnumDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(implementsClause), TreeBase.<SNodeListState>nodeOf(enumConstants), trailingComma, TreeBase.<SNodeListState>nodeOf(members)));
	}

	public EnumDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new SLocation<EnumDecl.State>(make(modifiers, name, implementsClause, enumConstants, trailingComma, members)));
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

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(MEMBERS);
	}

	public EnumDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(MEMBERS, members);
	}

	public EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(MEMBERS, mutation);
	}

	private static final STraversal<EnumDecl.State> MODIFIERS = new STraversal<EnumDecl.State>() {

		public STree<?> traverse(EnumDecl.State state) {
			return state.modifiers;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<EnumDecl.State> leftSibling(EnumDecl.State state) {
			return null;
		}

		public STraversal<EnumDecl.State> rightSibling(EnumDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<EnumDecl.State> NAME = new STraversal<EnumDecl.State>() {

		public STree<?> traverse(EnumDecl.State state) {
			return state.name;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<EnumDecl.State> leftSibling(EnumDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<EnumDecl.State> rightSibling(EnumDecl.State state) {
			return IMPLEMENTS_CLAUSE;
		}
	};
	private static final STraversal<EnumDecl.State> IMPLEMENTS_CLAUSE = new STraversal<EnumDecl.State>() {

		public STree<?> traverse(EnumDecl.State state) {
			return state.implementsClause;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, STree<?> child) {
			return state.withImplementsClause((STree) child);
		}

		public STraversal<EnumDecl.State> leftSibling(EnumDecl.State state) {
			return NAME;
		}

		public STraversal<EnumDecl.State> rightSibling(EnumDecl.State state) {
			return ENUM_CONSTANTS;
		}
	};
	private static final STraversal<EnumDecl.State> ENUM_CONSTANTS = new STraversal<EnumDecl.State>() {

		public STree<?> traverse(EnumDecl.State state) {
			return state.enumConstants;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, STree<?> child) {
			return state.withEnumConstants((STree) child);
		}

		public STraversal<EnumDecl.State> leftSibling(EnumDecl.State state) {
			return IMPLEMENTS_CLAUSE;
		}

		public STraversal<EnumDecl.State> rightSibling(EnumDecl.State state) {
			return MEMBERS;
		}
	};
	private static final STraversal<EnumDecl.State> MEMBERS = new STraversal<EnumDecl.State>() {

		public STree<?> traverse(EnumDecl.State state) {
			return state.members;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, STree<?> child) {
			return state.withMembers((STree) child);
		}

		public STraversal<EnumDecl.State> leftSibling(EnumDecl.State state) {
			return ENUM_CONSTANTS;
		}

		public STraversal<EnumDecl.State> rightSibling(EnumDecl.State state) {
			return null;
		}
	};

	private static final SProperty<EnumDecl.State> TRAILING_COMMA = new SProperty<EnumDecl.State>() {

		public Object retrieve(EnumDecl.State state) {
			return state.trailingComma;
		}

		public EnumDecl.State rebuildParentState(EnumDecl.State state, Object value) {
			return state.withTrailingComma((Boolean) value);
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			token(LToken.Enum),
			child(NAME),
			child(IMPLEMENTS_CLAUSE, QualifiedType.implementsClauseShape),
			token(LToken.BraceLeft)
					.withSpacingBefore(space())
					.withIndentationAfter(indent(TYPE_BODY)),
			nonEmptyChildren(ENUM_CONSTANTS,
					child(ENUM_CONSTANTS, EnumConstantDecl.listShape)
			),
			dataOption(TRAILING_COMMA,
					token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants))
			),
			emptyChildren(MEMBERS,
					emptyChildren(ENUM_CONSTANTS,
							none().withSpacingAfter(newLine()),
							none().withSpacingAfter(spacing(EnumBody_AfterConstants))
					)
			),
			nonEmptyChildren(MEMBERS,
					token(LToken.SemiColon).withSpacingAfter(spacing(EnumBody_AfterConstants))
			),
			child(MEMBERS, MemberDecl.membersShape),
			token(LToken.BraceRight)
					.withIndentationBefore(unIndent(TYPE_BODY))
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<EnumDecl.State> firstChild() {
			return null;
		}

		public STraversal<EnumDecl.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<EnumDecl.State> location) {
			return new EnumDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.EnumDecl;
		}
	}
}
