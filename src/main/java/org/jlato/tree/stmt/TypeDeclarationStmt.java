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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

import org.jlato.tree.Tree;

public class TypeDeclarationStmt extends TreeBase<TypeDeclarationStmt.State, Stmt, TypeDeclarationStmt> implements Stmt {

	public final static SKind<TypeDeclarationStmt.State> kind = new SKind<TypeDeclarationStmt.State>() {

	};

	private TypeDeclarationStmt(SLocation<TypeDeclarationStmt.State> location) {
		super(location);
	}

	public static STree<TypeDeclarationStmt.State> make(TypeDecl typeDecl) {
		return new STree<TypeDeclarationStmt.State>(kind, new TypeDeclarationStmt.State(TreeBase.<TypeDecl.State>nodeOf(typeDecl)));
	}

	public TypeDeclarationStmt(TypeDecl typeDecl) {
		super(new SLocation<TypeDeclarationStmt.State>(make(typeDecl)));
	}

	public TypeDecl typeDecl() {
		return location.safeTraversal(TYPE_DECL);
	}

	public TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl) {
		return location.safeTraversalReplace(TYPE_DECL, typeDecl);
	}

	public TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation) {
		return location.safeTraversalMutate(TYPE_DECL, mutation);
	}

	private static final STraversal<TypeDeclarationStmt.State> TYPE_DECL = new STraversal<TypeDeclarationStmt.State>() {

		public STree<?> traverse(TypeDeclarationStmt.State state) {
			return state.typeDecl;
		}

		public TypeDeclarationStmt.State rebuildParentState(TypeDeclarationStmt.State state, STree<?> child) {
			return state.withTypeDecl((STree) child);
		}

		public STraversal<TypeDeclarationStmt.State> leftSibling(TypeDeclarationStmt.State state) {
			return null;
		}

		public STraversal<TypeDeclarationStmt.State> rightSibling(TypeDeclarationStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPE_DECL)
	);

	public static class State extends SNodeState<State> {

		public final STree<TypeDecl.State> typeDecl;

		State(STree<TypeDecl.State> typeDecl) {
			this.typeDecl = typeDecl;
		}

		public TypeDeclarationStmt.State withTypeDecl(STree<TypeDecl.State> typeDecl) {
			return new TypeDeclarationStmt.State(typeDecl);
		}

		public STraversal<TypeDeclarationStmt.State> firstChild() {
			return null;
		}

		public STraversal<TypeDeclarationStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<TypeDeclarationStmt.State> location) {
			return new TypeDeclarationStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
