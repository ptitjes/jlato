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
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

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
		super(new SLocation<FieldAccessExpr.State>(make(TreeBase.<SNodeOptionState>nodeOf(scope), TreeBase.<Name.State>nodeOf(name))));
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

	private static final STraversal<FieldAccessExpr.State> SCOPE = new STraversal<FieldAccessExpr.State>() {

		public STree<?> traverse(FieldAccessExpr.State state) {
			return state.scope;
		}

		public FieldAccessExpr.State rebuildParentState(FieldAccessExpr.State state, STree<?> child) {
			return state.withScope((STree) child);
		}

		public STraversal<FieldAccessExpr.State> leftSibling(FieldAccessExpr.State state) {
			return null;
		}

		public STraversal<FieldAccessExpr.State> rightSibling(FieldAccessExpr.State state) {
			return NAME;
		}
	};
	private static final STraversal<FieldAccessExpr.State> NAME = new STraversal<FieldAccessExpr.State>() {

		public STree<?> traverse(FieldAccessExpr.State state) {
			return state.name;
		}

		public FieldAccessExpr.State rebuildParentState(FieldAccessExpr.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<FieldAccessExpr.State> leftSibling(FieldAccessExpr.State state) {
			return SCOPE;
		}

		public STraversal<FieldAccessExpr.State> rightSibling(FieldAccessExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			when(childIs(SCOPE, some()), composite(child(SCOPE, element()), token(LToken.Dot))),
			child(NAME)
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<FieldAccessExpr.State> firstChild() {
			return SCOPE;
		}

		public STraversal<FieldAccessExpr.State> lastChild() {
			return NAME;
		}

		public Tree instantiate(SLocation<FieldAccessExpr.State> location) {
			return new FieldAccessExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.FieldAccessExpr;
		}
	}
}
