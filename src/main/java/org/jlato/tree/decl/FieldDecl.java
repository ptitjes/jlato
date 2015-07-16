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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class FieldDecl extends TreeBase<SNodeState, MemberDecl, FieldDecl> implements MemberDecl {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public FieldDecl instantiate(SLocation<SNodeState> location) {
			return new FieldDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private FieldDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public FieldDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(modifiers, type, variables/*, javadocComment*/)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Field;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public FieldDecl withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public FieldDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(VARIABLES);
	}

	public FieldDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(VARIABLES, variables);
	}

	public FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(VARIABLES, mutation);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public FieldDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final STraversal<SNodeState> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> VARIABLES = SNodeState.childTraversal(2);
//	private static final int JAVADOC_COMMENT = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, VariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
	);
}
