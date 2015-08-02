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
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class FieldAccessExpr extends TreeBase<FieldAccessExpr.State, Expr, FieldAccessExpr> implements Expr {

	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	private FieldAccessExpr(SLocation<FieldAccessExpr.State> location) {
		super(location);
	}

	public static STree<FieldAccessExpr.State> make(STree<SNodeOptionState> scope, STree<Name.State> name) {
		return new STree<FieldAccessExpr.State>(new FieldAccessExpr.State(scope, name));
	}

	public FieldAccessExpr(NodeOption<Expr> scope, Name name) {
		super(new SLocation<FieldAccessExpr.State>(make(TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<Name.State>treeOf(name))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SCOPE);
	}

	public FieldAccessExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public FieldAccessExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public FieldAccessExpr withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public FieldAccessExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeOptionState> scope;

		public final STree<Name.State> name;

		State(STree<SNodeOptionState> scope, STree<Name.State> name) {
			this.scope = scope;
			this.name = name;
		}

		public FieldAccessExpr.State withScope(STree<SNodeOptionState> scope) {
			return new FieldAccessExpr.State(scope, name);
		}

		public FieldAccessExpr.State withName(STree<Name.State> name) {
			return new FieldAccessExpr.State(scope, name);
		}

		@Override
		public Kind kind() {
			return Kind.FieldAccessExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<FieldAccessExpr.State> location) {
			return new FieldAccessExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return SCOPE;
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
			if (!scope.equals(state.scope))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + scope.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<FieldAccessExpr.State, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<FieldAccessExpr.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(FieldAccessExpr.State state) {
			return state.scope;
		}

		@Override
		public FieldAccessExpr.State doRebuildParentState(FieldAccessExpr.State state, STree<SNodeOptionState> child) {
			return state.withScope(child);
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

	private static STypeSafeTraversal<FieldAccessExpr.State, Name.State, Name> NAME = new STypeSafeTraversal<FieldAccessExpr.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(FieldAccessExpr.State state) {
			return state.name;
		}

		@Override
		public FieldAccessExpr.State doRebuildParentState(FieldAccessExpr.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
