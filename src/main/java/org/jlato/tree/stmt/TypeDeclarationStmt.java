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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class TypeDeclarationStmt extends TreeBase<TypeDeclarationStmt.State, Stmt, TypeDeclarationStmt> implements Stmt {

	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	private TypeDeclarationStmt(SLocation<TypeDeclarationStmt.State> location) {
		super(location);
	}

	public static STree<TypeDeclarationStmt.State> make(STree<? extends TypeDecl.State> typeDecl) {
		return new STree<TypeDeclarationStmt.State>(new TypeDeclarationStmt.State(typeDecl));
	}

	public TypeDeclarationStmt(TypeDecl typeDecl) {
		super(new SLocation<TypeDeclarationStmt.State>(make(TreeBase.<TypeDecl.State>treeOf(typeDecl))));
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

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<? extends TypeDecl.State> typeDecl;

		State(STree<? extends TypeDecl.State> typeDecl) {
			this.typeDecl = typeDecl;
		}

		public TypeDeclarationStmt.State withTypeDecl(STree<? extends TypeDecl.State> typeDecl) {
			return new TypeDeclarationStmt.State(typeDecl);
		}

		@Override
		public Kind kind() {
			return Kind.TypeDeclarationStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<TypeDeclarationStmt.State> location) {
			return new TypeDeclarationStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPE_DECL;
		}

		@Override
		public STraversal lastChild() {
			return TYPE_DECL;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TypeDeclarationStmt.State state = (TypeDeclarationStmt.State) o;
			if (typeDecl == null ? state.typeDecl != null : !typeDecl.equals(state.typeDecl))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (typeDecl != null) result = 37 * result + typeDecl.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<TypeDeclarationStmt.State, TypeDecl.State, TypeDecl> TYPE_DECL = new STypeSafeTraversal<TypeDeclarationStmt.State, TypeDecl.State, TypeDecl>() {

		@Override
		protected STree<?> doTraverse(TypeDeclarationStmt.State state) {
			return state.typeDecl;
		}

		@Override
		protected TypeDeclarationStmt.State doRebuildParentState(TypeDeclarationStmt.State state, STree<TypeDecl.State> child) {
			return state.withTypeDecl(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPE_DECL)
	);
}
