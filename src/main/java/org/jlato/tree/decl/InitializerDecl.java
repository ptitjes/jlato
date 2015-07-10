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

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.Factory.child;
import static org.jlato.internal.shapes.LexicalShape.Factory.composite;

public class InitializerDecl extends Decl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public InitializerDecl instantiate(SLocation location) {
			return new InitializerDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private InitializerDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> InitializerDecl(NodeList<EM> modifiers, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, body/*, javadocComment*/)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> InitializerDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	/*
		public JavadocComment javadocComment() {
			return location.nodeChild(JAVADOC_COMMENT);
		}

		public InitializerDecl withJavadocComment(JavadocComment javadocComment) {
			return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
		}
	*/
	private static final int MODIFIERS = 0;
	private static final int BODY = 1;
//	private static final int JAVADOC_COMMENT = 3;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(BODY)
	);
}
