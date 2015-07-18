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
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;

public class PackageDecl extends TreeBase<PackageDecl.State, Tree, PackageDecl> implements Tree {

	public final static SKind<PackageDecl.State> kind = new SKind<PackageDecl.State>() {

	};

	private PackageDecl(SLocation<PackageDecl.State> location) {
		super(location);
	}

	public static STree<PackageDecl.State> make(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		return new STree<PackageDecl.State>(kind, new PackageDecl.State(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<QualifiedName.State>nodeOf(name)));
	}

	public PackageDecl(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		super(new SLocation<PackageDecl.State>(make(annotations, name)));
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

	private static final STraversal<PackageDecl.State> ANNOTATIONS = new STraversal<PackageDecl.State>() {

		public STree<?> traverse(PackageDecl.State state) {
			return state.annotations;
		}

		public PackageDecl.State rebuildParentState(PackageDecl.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal<PackageDecl.State> leftSibling(PackageDecl.State state) {
			return null;
		}

		public STraversal<PackageDecl.State> rightSibling(PackageDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<PackageDecl.State> NAME = new STraversal<PackageDecl.State>() {

		public STree<?> traverse(PackageDecl.State state) {
			return state.name;
		}

		public PackageDecl.State rebuildParentState(PackageDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<PackageDecl.State> leftSibling(PackageDecl.State state) {
			return ANNOTATIONS;
		}

		public STraversal<PackageDecl.State> rightSibling(PackageDecl.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.Package),
			child(NAME),
			token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<PackageDecl.State> firstChild() {
			return null;
		}

		public STraversal<PackageDecl.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<PackageDecl.State> location) {
			return new PackageDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
