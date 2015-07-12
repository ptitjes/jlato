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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;

public class EnumDecl extends TypeDecl implements TopLevel, Member {

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

	public <EM extends Tree & ExtendedModifier, M extends Decl & Member> EnumDecl(NodeList<EM> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, NodeList<M> members) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, name, implementsClause, enumConstants, members)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> EnumDecl withModifiers(NodeList<EM> modifiers) {
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

	public NodeList<QualifiedType> implementsClause() {
		return location.nodeChild(IMPLEMENTS_CLAUSE);
	}

	public EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public NodeList<EnumConstantDecl> enumConstants() {
		return location.nodeChild(ENUM_CONSTANTS);
	}

	public EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.nodeWithChild(ENUM_CONSTANTS, enumConstants);
	}

	public <M extends Decl & Member> NodeList<M> members() {
		return location.nodeChild(MEMBERS);
	}

	public <M extends Decl & Member> EnumDecl withMembers(NodeList<M> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int IMPLEMENTS_CLAUSE = 2;
	private static final int ENUM_CONSTANTS = 3;
	private static final int MEMBERS = 4;

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
			nonEmptyChildren(MEMBERS,
					nonEmptyChildren(ENUM_CONSTANTS,
							token(LToken.SemiColon).withSpacingAfter(spacing(EnumBody_AfterConstants))
					)
			),
			emptyChildren(MEMBERS,
					emptyChildren(ENUM_CONSTANTS,
							none().withSpacing(newLine()),
							none().withSpacing(spacing(EnumBody_AfterConstants))
					)
			),
			child(MEMBERS, Decl.membersShape),
			token(LToken.BraceRight)
					.withIndentationBefore(unIndent(TYPE_BODY))
	);
}
