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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

public class PackageDecl extends TreeBase<PackageDecl.State, Tree, PackageDecl> implements Tree {

	public Kind kind() {
		return Kind.PackageDecl;
	}

	private PackageDecl(SLocation<PackageDecl.State> location) {
		super(location);
	}

	public static STree<PackageDecl.State> make(STree<SNodeListState> annotations, STree<QualifiedName.State> name) {
		return new STree<PackageDecl.State>(new PackageDecl.State(annotations, name));
	}

	public PackageDecl(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		super(new SLocation<PackageDecl.State>(make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<QualifiedName.State>treeOf(name))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public PackageDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public PackageDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeListState> annotations;

		public final STree<QualifiedName.State> name;

		State(STree<SNodeListState> annotations, STree<QualifiedName.State> name) {
			this.annotations = annotations;
			this.name = name;
		}

		public PackageDecl.State withAnnotations(STree<SNodeListState> annotations) {
			return new PackageDecl.State(annotations, name);
		}

		public PackageDecl.State withName(STree<QualifiedName.State> name) {
			return new PackageDecl.State(annotations, name);
		}

		@Override
		public Kind kind() {
			return Kind.PackageDecl;
		}

		@Override
		protected Tree doInstantiate(SLocation<PackageDecl.State> location) {
			return new PackageDecl(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return ANNOTATIONS;
		}

		@Override
		public STraversal lastChild() {
			return NAME;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (!annotations.equals(state.annotations))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + annotations.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<PackageDecl.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<PackageDecl.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.annotations;
		}

		@Override
		public PackageDecl.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<PackageDecl.State, QualifiedName.State, QualifiedName> NAME = new STypeSafeTraversal<PackageDecl.State, QualifiedName.State, QualifiedName>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public PackageDecl.State doRebuildParentState(State state, STree<QualifiedName.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.Package),
			child(NAME),
			token(LToken.SemiColon)
	);
}
