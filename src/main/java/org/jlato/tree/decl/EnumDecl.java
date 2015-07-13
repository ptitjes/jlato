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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Mutator;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_BetweenConstants;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class EnumDecl extends TypeDecl {

	public final static Kind kind = new Kind() {
		public EnumDecl instantiate(SLocation location) {
			return new EnumDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected EnumDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> EnumDecl(NodeList<EM> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, name, implementsClause, enumConstants, members), dataOf(trailingComma)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> EnumDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> EnumDecl withModifiers(Mutator<NodeList<EM>> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return TypeKind.Enum;
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public EnumDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public EnumDecl withName(Mutator<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.nodeChild(IMPLEMENTS_CLAUSE);
	}

	public EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public EnumDecl withImplementsClause(Mutator<NodeList<QualifiedType>> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public NodeList<EnumConstantDecl> enumConstants() {
		return location.nodeChild(ENUM_CONSTANTS);
	}

	public EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.nodeWithChild(ENUM_CONSTANTS, enumConstants);
	}

	public EnumDecl withEnumConstants(Mutator<NodeList<EnumConstantDecl>> enumConstants) {
		return location.nodeWithChild(ENUM_CONSTANTS, enumConstants);
	}

	public NodeList<MemberDecl> members() {
		return location.nodeChild(MEMBERS);
	}

	public EnumDecl withMembers(NodeList<MemberDecl> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	public EnumDecl withMembers(Mutator<NodeList<MemberDecl>> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int IMPLEMENTS_CLAUSE = 2;
	private static final int ENUM_CONSTANTS = 3;
	private static final int MEMBERS = 4;

	private static final int TRAILING_COMMA = 0;

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
