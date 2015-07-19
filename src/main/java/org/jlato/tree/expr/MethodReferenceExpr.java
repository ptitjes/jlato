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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class MethodReferenceExpr extends TreeBase<MethodReferenceExpr.State, Expr, MethodReferenceExpr> implements Expr {

	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	private MethodReferenceExpr(SLocation<MethodReferenceExpr.State> location) {
		super(location);
	}

	public static STree<MethodReferenceExpr.State> make(STree<? extends Expr.State> scope, STree<SNodeListState> typeArgs, STree<Name.State> name) {
		return new STree<MethodReferenceExpr.State>(new MethodReferenceExpr.State(scope, typeArgs, name));
	}

	public MethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new SLocation<MethodReferenceExpr.State>(make(TreeBase.<Expr.State>nodeOf(scope), TreeBase.<SNodeListState>nodeOf(typeArgs), TreeBase.<Name.State>nodeOf(name))));
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

	public static class State extends SNodeState<State>implements Expr.State {

		public final STree<? extends Expr.State> scope;

		public final STree<SNodeListState> typeArgs;

		public final STree<Name.State> name;

		State(STree<? extends Expr.State> scope, STree<SNodeListState> typeArgs, STree<Name.State> name) {
			this.scope = scope;
			this.typeArgs = typeArgs;
			this.name = name;
		}

		public MethodReferenceExpr.State withScope(STree<? extends Expr.State> scope) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		public MethodReferenceExpr.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		public MethodReferenceExpr.State withName(STree<Name.State> name) {
			return new MethodReferenceExpr.State(scope, typeArgs, name);
		}

		@Override
		public Kind kind() {
			return Kind.MethodReferenceExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<MethodReferenceExpr.State> location) {
			return new MethodReferenceExpr(location);
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
	}

	private static STypeSafeTraversal<MethodReferenceExpr.State, Expr.State, Expr> SCOPE = new STypeSafeTraversal<MethodReferenceExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(MethodReferenceExpr.State state) {
			return state.scope;
		}

		@Override
		protected MethodReferenceExpr.State doRebuildParentState(MethodReferenceExpr.State state, STree<Expr.State> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_ARGS;
		}
	};

	private static STypeSafeTraversal<MethodReferenceExpr.State, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<MethodReferenceExpr.State, SNodeListState, NodeList<Type>>() {

		@Override
		protected STree<?> doTraverse(MethodReferenceExpr.State state) {
			return state.typeArgs;
		}

		@Override
		protected MethodReferenceExpr.State doRebuildParentState(MethodReferenceExpr.State state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	private static STypeSafeTraversal<MethodReferenceExpr.State, Name.State, Name> NAME = new STypeSafeTraversal<MethodReferenceExpr.State, Name.State, Name>() {

		@Override
		protected STree<?> doTraverse(MethodReferenceExpr.State state) {
			return state.name;
		}

		@Override
		protected MethodReferenceExpr.State doRebuildParentState(MethodReferenceExpr.State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(NAME)
	);
}
