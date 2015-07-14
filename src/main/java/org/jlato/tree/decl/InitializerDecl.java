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
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class InitializerDecl extends MemberDecl {

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
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, body/*, javadocComment*/)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Initializer;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> InitializerDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> InitializerDecl withModifiers(Mutation<NodeList<EM>> mutation) {
		return location.nodeMutateChild(MODIFIERS, mutation);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public InitializerDecl withBody(Mutation<BlockStmt> mutation) {
		return location.nodeMutateChild(BODY, mutation);
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
