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

package org.jlato.tree.name;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class QualifiedName extends TreeBase<QualifiedName.State, Tree, QualifiedName> implements Tree {

	public Kind kind() {
		return Kind.QualifiedName;
	}

	private QualifiedName(SLocation<QualifiedName.State> location) {
		super(location);
	}

	public static STree<QualifiedName.State> make(STree<SNodeOptionState> qualifier, STree<Name.State> name) {
		return new STree<QualifiedName.State>(new QualifiedName.State(qualifier, name));
	}

	public QualifiedName(NodeOption<QualifiedName> qualifier, Name name) {
		super(new SLocation<QualifiedName.State>(make(TreeBase.<SNodeOptionState>treeOf(qualifier), TreeBase.<Name.State>treeOf(name))));
	}

	public NodeOption<QualifiedName> qualifier() {
		return location.safeTraversal(QUALIFIER);
	}

	public QualifiedName withQualifier(NodeOption<QualifiedName> qualifier) {
		return location.safeTraversalReplace(QUALIFIER, qualifier);
	}

	public QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation) {
		return location.safeTraversalMutate(QUALIFIER, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public QualifiedName withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public QualifiedName withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public static class State extends SNodeState<State> implements STreeState {

		public final STree<SNodeOptionState> qualifier;

		public final STree<Name.State> name;

		State(STree<SNodeOptionState> qualifier, STree<Name.State> name) {
			this.qualifier = qualifier;
			this.name = name;
		}

		public QualifiedName.State withQualifier(STree<SNodeOptionState> qualifier) {
			return new QualifiedName.State(qualifier, name);
		}

		public QualifiedName.State withName(STree<Name.State> name) {
			return new QualifiedName.State(qualifier, name);
		}

		@Override
		public Kind kind() {
			return Kind.QualifiedName;
		}

		@Override
		protected Tree doInstantiate(SLocation<QualifiedName.State> location) {
			return new QualifiedName(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return QUALIFIER;
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
			if (!qualifier.equals(state.qualifier))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + qualifier.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<QualifiedName.State, SNodeOptionState, NodeOption<QualifiedName>> QUALIFIER = new STypeSafeTraversal<QualifiedName.State, SNodeOptionState, NodeOption<QualifiedName>>() {

		@Override
		public STree<?> doTraverse(QualifiedName.State state) {
			return state.qualifier;
		}

		@Override
		public QualifiedName.State doRebuildParentState(QualifiedName.State state, STree<SNodeOptionState> child) {
			return state.withQualifier(child);
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

	private static STypeSafeTraversal<QualifiedName.State, Name.State, Name> NAME = new STypeSafeTraversal<QualifiedName.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(QualifiedName.State state) {
			return state.name;
		}

		@Override
		public QualifiedName.State doRebuildParentState(QualifiedName.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return QUALIFIER;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(QUALIFIER, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
