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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class LocalVariableDecl extends Decl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LocalVariableDecl instantiate(SLocation location) {
			return new LocalVariableDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected LocalVariableDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> LocalVariableDecl(NodeList<EM> modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, type, variables/*, javadocComment*/)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> LocalVariableDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public LocalVariableDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.nodeChild(VARIABLES);
	}

	public LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.nodeWithChild(VARIABLES, variables);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public FieldDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int VARIABLES = 2;
//	private static final int JAVADOC_COMMENT = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			none().withSpacing(space()),
			child(VARIABLES, VariableDeclarator.listShape)
	);
}