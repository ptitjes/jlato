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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class InitializerDecl extends TreeBase<InitializerDecl.State, MemberDecl, InitializerDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.InitializerDecl;
	}

	private InitializerDecl(SLocation<InitializerDecl.State> location) {
		super(location);
	}

	public static STree<InitializerDecl.State> make(STree<SNodeListState> modifiers, STree<BlockStmt.State> body) {
		return new STree<InitializerDecl.State>(new InitializerDecl.State(modifiers, body));
	}

	public InitializerDecl(NodeList<ExtendedModifier> modifiers, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation<InitializerDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<BlockStmt.State>nodeOf(body))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Initializer;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public BlockStmt body() {
		return location.safeTraversal(BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public InitializerDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(BODY)
	);
}
