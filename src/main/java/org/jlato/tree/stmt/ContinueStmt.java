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
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ContinueStmt extends TreeBase<ContinueStmt.State, Stmt, ContinueStmt> implements Stmt {

	public Kind kind() {
		return Kind.ContinueStmt;
	}

	private ContinueStmt(SLocation<ContinueStmt.State> location) {
		super(location);
	}

	public static STree<ContinueStmt.State> make(STree<SNodeOptionState> id) {
		return new STree<ContinueStmt.State>(new ContinueStmt.State(id));
	}

	public ContinueStmt(NodeOption<Name> id) {
		super(new SLocation<ContinueStmt.State>(make(TreeBase.<SNodeOptionState>treeOf(id))));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(ID);
	}

	public ContinueStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(ID, id);
	}

	public ContinueStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeOptionState> id;

		State(STree<SNodeOptionState> id) {
			this.id = id;
		}

		public ContinueStmt.State withId(STree<SNodeOptionState> id) {
			return new ContinueStmt.State(id);
		}

		@Override
		public Kind kind() {
			return Kind.ContinueStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ContinueStmt.State> location) {
			return new ContinueStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return ID;
		}

		@Override
		public STraversal lastChild() {
			return ID;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (!id.equals(state.id))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + id.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ContinueStmt.State, SNodeOptionState, NodeOption<Name>> ID = new STypeSafeTraversal<ContinueStmt.State, SNodeOptionState, NodeOption<Name>>() {

		@Override
		public STree<?> doTraverse(ContinueStmt.State state) {
			return state.id;
		}

		@Override
		public ContinueStmt.State doRebuildParentState(ContinueStmt.State state, STree<SNodeOptionState> child) {
			return state.withId(child);
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
			token(LToken.Continue),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
