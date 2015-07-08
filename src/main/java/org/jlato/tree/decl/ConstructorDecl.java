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
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.ClassOrInterfaceType;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class ConstructorDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConstructorDecl instantiate(SLocation location) {
			return new ConstructorDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ConstructorDecl(SLocation location) {
		super(location);
	}

	public ConstructorDecl(Modifiers modifiers, NodeList<TypeParameter> typeParameters, Name name, NodeList<Parameter> parameters, NodeList<ClassOrInterfaceType> throwsClause, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, typeParameters, name, parameters, throwsClause, body/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public ConstructorDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ConstructorDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public ConstructorDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public ConstructorDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public NodeList<ClassOrInterfaceType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<ClassOrInterfaceType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public ConstructorDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public ConstructorDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int NAME = 2;
	private static final int PARAMETERS = 3;
	private static final int THROWS_CLAUSE = 4;
	private static final int BODY = 5;
//	private static final int JAVADOC_COMMENT = 7;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			children(TYPE_PARAMETERS, token(LToken.Less), token(LToken.Comma).withSpacingAfter(space()), token(LToken.Greater).withSpacingAfter(space())),
			child(NAME),
			token(LToken.ParenthesisLeft),
			children(PARAMETERS, token(LToken.Comma).withSpacingAfter(space())),
			token(LToken.ParenthesisRight),
			children(THROWS_CLAUSE, token(LToken.Throws).withSpacingBefore(space()), token(LToken.Comma).withSpacingAfter(space()), none()),
			none().withSpacing(space()), child(BODY)
	);
}
