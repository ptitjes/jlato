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
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;

public class EnumConstantDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnumConstantDecl instantiate(SLocation location) {
			return new EnumConstantDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EnumConstantDecl(SLocation location) {
		super(location);
	}

	public EnumConstantDecl(Modifiers modifiers, Name name, NodeList<Expr> args, NodeList<Decl> classBody/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, name, args, classBody/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public LocalVariableDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public EnumConstantDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGS);
	}

	public EnumConstantDecl withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGS, args);
	}

	public NodeList<Decl> classBody() {
		return location.nodeChild(CLASS_BODY);
	}

	public EnumConstantDecl withClassBody(NodeList<Decl> classBody) {
		return location.nodeWithChild(CLASS_BODY, classBody);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public EnumConstantDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int ARGS = 2;
	private static final int CLASS_BODY = 3;
//	private static final int JAVADOC_COMMENT = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			child(NAME),
			children(ARGS, token(LToken.ParenthesisLeft), token(LToken.Comma), token(LToken.ParenthesisRight)),
			children(CLASS_BODY,
					composite(token(LToken.BraceLeft).withSpacing(space(), spacing(ClassBody_BeforeMembers)), indent(TYPE_BODY)),
					none().withSpacing(spacing(ClassBody_BetweenMembers)),
					composite(
							unIndent(TYPE_BODY),
							token(LToken.BraceRight).withSpacing(spacing(ClassBody_AfterMembers), spacing(EnumConstant_AfterBody))
					)
			)
	);
}
