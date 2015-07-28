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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

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
		super(new SLocation<MarkerAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>treeOf(name))));
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

	public static class State extends SNodeState<State> implements AnnotationExpr.State {

		public final STree<QualifiedName.State> name;

		State(STree<QualifiedName.State> name) {
			this.name = name;
		}

		public MarkerAnnotationExpr.State withName(STree<QualifiedName.State> name) {
			return new MarkerAnnotationExpr.State(name);
		}

		@Override
		public Kind kind() {
			return Kind.MarkerAnnotationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<MarkerAnnotationExpr.State> location) {
			return new MarkerAnnotationExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
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
			MarkerAnnotationExpr.State state = (MarkerAnnotationExpr.State) o;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (name != null) result = 37 * result + name.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<MarkerAnnotationExpr.State, QualifiedName.State, QualifiedName> NAME = new STypeSafeTraversal<MarkerAnnotationExpr.State, QualifiedName.State, QualifiedName>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public MarkerAnnotationExpr.State doRebuildParentState(State state, STree<QualifiedName.State> child) {
			return state.withName(child);
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
			token(LToken.At), child(NAME)
	);
}
