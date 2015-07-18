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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class BreakStmt extends TreeBase<BreakStmt.State, Stmt, BreakStmt> implements Stmt {

	public Kind kind() {
		return Kind.BreakStmt;
	}

	private BreakStmt(SLocation<BreakStmt.State> location) {
		super(location);
	}

	public static STree<BreakStmt.State> make(NodeOption<Name> id) {
		return new STree<BreakStmt.State>(new BreakStmt.State(TreeBase.<SNodeOptionState>nodeOf(id)));
	}

	public BreakStmt(NodeOption<Name> id) {
		super(new SLocation<BreakStmt.State>(make(id)));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(ID);
	}

	public BreakStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(ID, id);
	}

	public BreakStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	private static final STraversal<BreakStmt.State> ID = new STraversal<BreakStmt.State>() {

		public STree<?> traverse(BreakStmt.State state) {
			return state.id;
		}

		public BreakStmt.State rebuildParentState(BreakStmt.State state, STree<?> child) {
			return state.withId((STree) child);
		}

		public STraversal<BreakStmt.State> leftSibling(BreakStmt.State state) {
			return null;
		}

		public STraversal<BreakStmt.State> rightSibling(BreakStmt.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.Break),
			when(childIs(ID, some()), child(ID, element())),
			token(LToken.SemiColon)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> id;

		State(STree<SNodeOptionState> id) {
			this.id = id;
		}

		public BreakStmt.State withId(STree<SNodeOptionState> id) {
			return new BreakStmt.State(id);
		}

		public STraversal<BreakStmt.State> firstChild() {
			return ID;
		}

		public STraversal<BreakStmt.State> lastChild() {
			return ID;
		}

		public Tree instantiate(SLocation<BreakStmt.State> location) {
			return new BreakStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.BreakStmt;
		}
	}
}
