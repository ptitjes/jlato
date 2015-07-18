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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;

public class MethodInvocationExpr extends TreeBase<MethodInvocationExpr.State, Expr, MethodInvocationExpr> implements Expr {

	public final static SKind<MethodInvocationExpr.State> kind = new SKind<MethodInvocationExpr.State>() {
		public MethodInvocationExpr instantiate(SLocation<MethodInvocationExpr.State> location) {
			return new MethodInvocationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodInvocationExpr(SLocation<MethodInvocationExpr.State> location) {
		super(location);
	}

	public static STree<MethodInvocationExpr.State> make(NodeOption<Expr> scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		return new STree<MethodInvocationExpr.State>(kind, new MethodInvocationExpr.State(TreeBase.<SNodeOptionState>nodeOf(scope), TreeBase.<SNodeListState>nodeOf(typeArgs), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(args)));
	}

	public MethodInvocationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation<MethodInvocationExpr.State>(make(scope, typeArgs, name, args)));
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

	private static final STraversal<MethodInvocationExpr.State> SCOPE = SNodeState.childTraversal(0);
	private static final STraversal<MethodInvocationExpr.State> TYPE_ARGS = SNodeState.childTraversal(1);
	private static final STraversal<MethodInvocationExpr.State> NAME = SNodeState.childTraversal(2);
	private static final STraversal<MethodInvocationExpr.State> ARGS = SNodeState.childTraversal(3);

	public final static LexicalShape shape = composite(
			when(childIs(SCOPE, some()), composite(child(SCOPE, element()), token(LToken.Dot))),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(NAME),
			child(ARGS, Expr.argumentsShape)
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<MethodInvocationExpr.State> firstChild() {
			return null;
		}

		public STraversal<MethodInvocationExpr.State> lastChild() {
			return null;
		}
	}
}
