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
import org.jlato.tree.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class MethodInvocationExpr extends TreeBase<MethodInvocationExpr.State, Expr, MethodInvocationExpr> implements Expr {

	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	private MethodInvocationExpr(SLocation<MethodInvocationExpr.State> location) {
		super(location);
	}

	public static STree<MethodInvocationExpr.State> make(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<Name.State> name, STree<SNodeListState> args) {
		return new STree<MethodInvocationExpr.State>(new MethodInvocationExpr.State(scope, typeArgs, name, args));
	}

	public MethodInvocationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation<MethodInvocationExpr.State>(make(TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<SNodeListState>treeOf(typeArgs), TreeBase.<Name.State>treeOf(name), TreeBase.<SNodeListState>treeOf(args))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SCOPE);
	}

	public MethodInvocationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public MethodInvocationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public MethodInvocationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public MethodInvocationExpr withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MethodInvocationExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(ARGS);
	}

	public MethodInvocationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGS, args);
	}

	public MethodInvocationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGS, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeOptionState> scope;

		public final STree<SNodeListState> typeArgs;

		public final STree<Name.State> name;

		public final STree<SNodeListState> args;

		State(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<Name.State> name, STree<SNodeListState> args) {
			this.scope = scope;
			this.typeArgs = typeArgs;
			this.name = name;
			this.args = args;
		}

		public MethodInvocationExpr.State withScope(STree<SNodeOptionState> scope) {
			return new MethodInvocationExpr.State(scope, typeArgs, name, args);
		}

		public MethodInvocationExpr.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new MethodInvocationExpr.State(scope, typeArgs, name, args);
		}

		public MethodInvocationExpr.State withName(STree<Name.State> name) {
			return new MethodInvocationExpr.State(scope, typeArgs, name, args);
		}

		public MethodInvocationExpr.State withArgs(STree<SNodeListState> args) {
			return new MethodInvocationExpr.State(scope, typeArgs, name, args);
		}

		@Override
		public Kind kind() {
			return Kind.MethodInvocationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<MethodInvocationExpr.State> location) {
			return new MethodInvocationExpr(location);
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
			return ARGS;
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
			if (!typeArgs.equals(state.typeArgs))
				return false;
			if (name == null ? state.name != null : !name.equals(state.name))
				return false;
			if (!args.equals(state.args))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + scope.hashCode();
			result = 37 * result + typeArgs.hashCode();
			if (name != null) result = 37 * result + name.hashCode();
			result = 37 * result + args.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<MethodInvocationExpr.State, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<MethodInvocationExpr.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.scope;
		}

		@Override
		public MethodInvocationExpr.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
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

	private static STypeSafeTraversal<MethodInvocationExpr.State, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<MethodInvocationExpr.State, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.typeArgs;
		}

		@Override
		public MethodInvocationExpr.State doRebuildParentState(State state, STree<SNodeListState> child) {
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

	private static STypeSafeTraversal<MethodInvocationExpr.State, Name.State, Name> NAME = new STypeSafeTraversal<MethodInvocationExpr.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.name;
		}

		@Override
		public MethodInvocationExpr.State doRebuildParentState(State state, STree<Name.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ARGS;
		}
	};

	private static STypeSafeTraversal<MethodInvocationExpr.State, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<MethodInvocationExpr.State, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.args;
		}

		@Override
		public MethodInvocationExpr.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(NAME),
			child(ARGS, Expr.argumentsShape)
	);
}
