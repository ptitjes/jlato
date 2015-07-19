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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class MarkerAnnotationExpr extends TreeBase<MarkerAnnotationExpr.State, AnnotationExpr, MarkerAnnotationExpr> implements AnnotationExpr {

	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	private MarkerAnnotationExpr(SLocation<MarkerAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<MarkerAnnotationExpr.State> make(STree<QualifiedName.State> name) {
		return new STree<MarkerAnnotationExpr.State>(new MarkerAnnotationExpr.State(name));
	}

	public MarkerAnnotationExpr(QualifiedName name) {
		super(new SLocation<MarkerAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>nodeOf(name))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public MarkerAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	private static final STraversal NAME = new STraversal() {

		public STree<?> traverse(MarkerAnnotationExpr.State state) {
			return state.name;
		}

		public MarkerAnnotationExpr.State rebuildParentState(MarkerAnnotationExpr.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal leftSibling(MarkerAnnotationExpr.State state) {
			return null;
		}

		public STraversal rightSibling(MarkerAnnotationExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME)
	);

	public static class State extends SNodeState<State> {

		public final STree<QualifiedName.State> name;

		State(STree<QualifiedName.State> name) {
			this.name = name;
		}

		public MarkerAnnotationExpr.State withName(STree<QualifiedName.State> name) {
			return new MarkerAnnotationExpr.State(name);
		}

		public STraversal firstChild() {
			return NAME;
		}

		public STraversal lastChild() {
			return NAME;
		}

		public Tree instantiate(SLocation<MarkerAnnotationExpr.State> location) {
			return new MarkerAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.MarkerAnnotationExpr;
		}
	}
}
