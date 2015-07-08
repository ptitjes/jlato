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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.ClassOrInterfaceType;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

public class TypeDecl extends Decl implements TopLevel, Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TypeDecl instantiate(SLocation location) {
			return new TypeDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	public static <M extends Decl & Member> TypeDecl ofClass(Modifiers modifiers, Name name, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsClause, NodeList<ClassOrInterfaceType> implementsClause, NodeList<M> members) {
		return new TypeDecl(TypeKind.Class, modifiers, name, typeParameters, extendsClause, implementsClause, null, members);
	}

	public static <M extends Decl & Member> TypeDecl ofInterface(Modifiers modifiers, Name name, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsClause, NodeList<M> members) {
		return new TypeDecl(TypeKind.Interface, modifiers, name, typeParameters, extendsClause, null, null, members);
	}

	public static <M extends Decl & Member> TypeDecl ofEnum(Modifiers modifiers, Name name, NodeList<ClassOrInterfaceType> implementsClause, NodeList<EnumConstantDecl> enumConstants, NodeList<M> members) {
		return new TypeDecl(TypeKind.Enum, modifiers, name, null, null, implementsClause, enumConstants, members);
	}

	public static <M extends Decl & Member> TypeDecl ofAnnotationType(Modifiers modifiers, Name name, NodeList<M> members) {
		return new TypeDecl(TypeKind.AnnotationType, modifiers, name, null, null, null, null, members);
	}

	protected TypeDecl(SLocation location) {
		super(location);
	}

	public <M extends Decl & Member> TypeDecl(TypeKind typeKind, Modifiers modifiers, Name name, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsClause, NodeList<ClassOrInterfaceType> implementsClause, NodeList<EnumConstantDecl> enumConstants, NodeList<M> members/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, name, typeParameters, extendsClause, implementsClause, enumConstants, members), dataOf(typeKind)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public TypeDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return location.nodeData(TYPE_KIND);
	}

	public TypeDecl withTypeKind(TypeKind typeKind) {
		return location.nodeWithData(TYPE_KIND, typeKind);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public TypeDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public TypeDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public NodeList<ClassOrInterfaceType> extendsClause() {
		return location.nodeChild(EXTENDS_CLAUSE);
	}

	public TypeDecl withExtendsClause(NodeList<ClassOrInterfaceType> extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public NodeList<ClassOrInterfaceType> implementsClause() {
		return location.nodeChild(IMPLEMENTS_CLAUSE);
	}

	public TypeDecl withImplementsClause(NodeList<ClassOrInterfaceType> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public NodeList<EnumConstantDecl> enumConstants() {
		return location.nodeChild(ENUM_CONSTANTS);
	}

	public TypeDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.nodeWithChild(ENUM_CONSTANTS, enumConstants);
	}

	public <M extends Decl & Member> NodeList<M> members() {
		return location.nodeChild(MEMBERS);
	}

	public <M extends Decl & Member> TypeDecl withMembers(NodeList<M> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int EXTENDS_CLAUSE = 3;
	private static final int IMPLEMENTS_CLAUSE = 4;
	private static final int ENUM_CONSTANTS = 5;
	private static final int MEMBERS = 6;

	private static final int TYPE_KIND = 0;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((TypeKind) tree.state.data(TYPE_KIND)).token;
				}
			}),
			child(NAME),
			children(TYPE_PARAMETERS, token(LToken.Less), token(LToken.Comma).withSpacingAfter(space()), token(LToken.Greater).withSpacingAfter(space())),
			children(EXTENDS_CLAUSE, token(LToken.Extends), token(LToken.Comma).withSpacingAfter(space()), null),
			children(IMPLEMENTS_CLAUSE, token(LToken.Implements), token(LToken.Comma).withSpacingAfter(space()), null),
			token(LToken.BraceLeft)
					.withSpacingBefore(space())
					.withIndentationAfter(indent(TYPE_BODY)),
			nonEmptyChildren(ENUM_CONSTANTS,
					composite(
							children(ENUM_CONSTANTS,
									none().withSpacing(spacing(EnumBody_BeforeConstants)),
									token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants)),
									null
							),
							nonEmptyChildren(MEMBERS,
									token(LToken.SemiColon).withSpacingAfter(spacing(EnumBody_AfterConstants)),
									none().withSpacing(spacing(EnumBody_AfterConstants))
							)
					)
			),
			nonEmptyChildren(MEMBERS,
					children(MEMBERS,
							none().withSpacing(spacing(ClassBody_BeforeMembers)),
							none().withSpacing(spacing(ClassBody_BetweenMembers)),
							none().withSpacing(spacing(ClassBody_AfterMembers))
					),
					emptyChildren(ENUM_CONSTANTS,
							none().withSpacing(newLine())
					)
			),
			token(LToken.BraceRight)
					.withIndentationBefore(unIndent(TYPE_BODY))
	);

	public enum TypeKind {
		Class(LToken.Class),
		Interface(LToken.Interface),
		Enum(LToken.Enum),
		AnnotationType(LToken.AnnotationType)
		// Keep last comma
		;

		protected final LToken token;

		TypeKind(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
