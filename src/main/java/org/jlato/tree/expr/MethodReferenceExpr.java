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
import org.jlato.tree.NodeList;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class MethodReferenceExpr extends TreeBase<MethodReferenceExpr.State, Expr, MethodReferenceExpr> implements Expr {

	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	private MethodReferenceExpr(SLocation<MethodReferenceExpr.State> location) {
		super(location);
	}

	public static STree<MethodReferenceExpr.State> make(Expr scope, NodeList<Type> typeArgs, Name name) {
		return new STree<MethodReferenceExpr.State>(new MethodReferenceExpr.State(TreeBase.<Expr.State>nodeOf(scope), TreeBase.<SNodeListState>nodeOf(typeArgs), TreeBase.<Name.State>nodeOf(name)));
	}

	public MethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new SLocation<MethodReferenceExpr.State>(make(scope, typeArgs, name)));
	}

	public Expr scope() {
		return location.safeTraversal(SCOPE);
	}

	public MethodReferenceExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public MethodReferenceExpr withScope(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public MethodReferenceExpr withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MethodReferenceExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	private static final STraversal<MethodReferenceExpr.State> SCOPE = new STraversal<MethodReferenceExpr.State>() {

		public STree<?> traverse(MethodReferenceExpr.State state) {
			return state.scope;
		}

		public MethodReferenceExpr.State rebuildParentState(MethodReferenceExpr.State state, STree<?> child) {
			return state.withScope((STree) child);
		}

		public STraversal<MethodReferenceExpr.State> leftSibling(MethodReferenceExpr.State state) {
			return null;
		}

		public STraversal<MethodReferenceExpr.State> rightSibling(MethodReferenceExpr.State state) {
			return TYPE_ARGS;
		}
	};
	private static final STraversal<MethodReferenceExpr.State> TYPE_ARGS = new STraversal<MethodReferenceExpr.State>() {

		public STree<?> traverse(MethodReferenceExpr.State state) {
			return state.typeArgs;
		}

		public MethodReferenceExpr.State rebuildParentState(MethodReferenceExpr.State state, STree<?> child) {
			return state.withTypeArgs((STree) child);
		}

		public STraversal<MethodReferenceExpr.State> leftSibling(MethodReferenceExpr.State state) {
			return SCOPE;
		}

		public STraversal<MethodReferenceExpr.State> rightSibling(MethodReferenceExpr.State state) {
			return NAME;
		}
	};
	private static final STraversal<MethodReferenceExpr.State> NAME = new STraversal<MethodReferenceExpr.State>() {

		public STree<?> traverse(MethodReferenceExpr.State state) {
			return state.name;
		}

		public MethodReferenceExpr.State rebuildParentState(MethodReferenceExpr.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<MethodReferenceExpr.State> leftSibling(MethodReferenceExpr.State state) {
			return TYPE_ARGS;
		}

		public STraversal<MethodReferenceExpr.State> rightSibling(MethodReferenceExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(NAME)
	);

	public static class State extends SNodeState<State> {

		public final STree<Expr.State> scope;

		public final STree<SNodeListState> typeArgs;

		public final STree<Name.State> name;

		State(STree<Expr.State> scope, STree<SNodeListState> typeArgs, STree<Name.State> name) {
			this.scope = scope;
			this.typeArgs = typeArgs;
			this.name = name;
		}

		public MethodReferenceExpr.State withScope(STree<Expr.State> scope) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		public MethodReferenceExpr.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		public MethodReferenceExpr.State withName(STree<Name.State> name) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		public STraversal<MethodReferenceExpr.State> firstChild() {
			return null;
		}

		public STraversal<MethodReferenceExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<MethodReferenceExpr.State> location) {
			return new MethodReferenceExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.MethodReferenceExpr;
		}
	}
}
