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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class EnumDecl extends TreeBase<EnumDecl.State, TypeDecl, EnumDecl> implements TypeDecl {

	public Kind kind() {
		return Kind.EnumDecl;
	}

	protected EnumDecl(SLocation<EnumDecl.State> location) {
		super(location);
	}

	public static STree<EnumDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeListState> implementsClause, STree<SNodeListState> enumConstants, boolean trailingComma, STree<SNodeListState> members) {
		return new STree<EnumDecl.State>(new EnumDecl.State(modifiers, name, implementsClause, enumConstants, trailingComma, members));
	}

	public EnumDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new SLocation<EnumDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(implementsClause), TreeBase.<SNodeListState>nodeOf(enumConstants), trailingComma, TreeBase.<SNodeListState>nodeOf(members))));
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
}
