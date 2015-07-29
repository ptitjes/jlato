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
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ObjectCreationExpr extends TreeBase<ObjectCreationExpr.State, Expr, ObjectCreationExpr> implements Expr {

	public Kind kind() {
		return Kind.ObjectCreationExpr;
	}

	private ObjectCreationExpr(SLocation<ObjectCreationExpr.State> location) {
		super(location);
	}

	public static STree<ObjectCreationExpr.State> make(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<QualifiedType.State> type, STree<SNodeListState> args, STree<SNodeOptionState> body) {
		return new STree<ObjectCreationExpr.State>(new ObjectCreationExpr.State(scope, typeArgs, type, args, body));
	}

	public ObjectCreationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		super(new SLocation<ObjectCreationExpr.State>(make(TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<SNodeListState>treeOf(typeArgs), TreeBase.<QualifiedType.State>treeOf(type), TreeBase.<SNodeListState>treeOf(args), TreeBase.<SNodeOptionState>treeOf(body))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SCOPE);
	}

	public ObjectCreationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public QualifiedType type() {
		return location.safeTraversal(TYPE);
	}

	public ObjectCreationExpr withType(QualifiedType type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ObjectCreationExpr withType(Mutation<QualifiedType> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(ARGS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGS, args);
	}

	public ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> body() {
		return location.safeTraversal(BODY);
	}

	public ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<SNodeOptionState> scope;

		public final STree<SNodeListState> typeArgs;

		public final STree<QualifiedType.State> type;

		public final STree<SNodeListState> args;

		public final STree<SNodeOptionState> body;

		State(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<QualifiedType.State> type, STree<SNodeListState> args, STree<SNodeOptionState> body) {
			this.scope = scope;
			this.typeArgs = typeArgs;
			this.type = type;
			this.args = args;
			this.body = body;
		}

		public ObjectCreationExpr.State withScope(STree<SNodeOptionState> scope) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withType(STree<QualifiedType.State> type) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withArgs(STree<SNodeListState> args) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withBody(STree<SNodeOptionState> body) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		@Override
		public Kind kind() {
			return Kind.ObjectCreationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<ObjectCreationExpr.State> location) {
			return new ObjectCreationExpr(location);
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
			return BODY;
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
			if (type == null ? state.type != null : !type.equals(state.type))
				return false;
			if (!args.equals(state.args))
				return false;
			if (!body.equals(state.body))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + scope.hashCode();
			result = 37 * result + typeArgs.hashCode();
			if (type != null) result = 37 * result + type.hashCode();
			result = 37 * result + args.hashCode();
			result = 37 * result + body.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ObjectCreationExpr.State, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<ObjectCreationExpr.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.scope;
		}

		@Override
		public ObjectCreationExpr.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
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

	private static STypeSafeTraversal<ObjectCreationExpr.State, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<ObjectCreationExpr.State, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.typeArgs;
		}

		@Override
		public ObjectCreationExpr.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	private static STypeSafeTraversal<ObjectCreationExpr.State, QualifiedType.State, QualifiedType> TYPE = new STypeSafeTraversal<ObjectCreationExpr.State, QualifiedType.State, QualifiedType>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.type;
		}

		@Override
		public ObjectCreationExpr.State doRebuildParentState(State state, STree<QualifiedType.State> child) {
			return state.withType(child);
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

	private static STypeSafeTraversal<ObjectCreationExpr.State, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<ObjectCreationExpr.State, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.args;
		}

		@Override
		public ObjectCreationExpr.State doRebuildParentState(State state, STree<SNodeListState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	private static STypeSafeTraversal<ObjectCreationExpr.State, SNodeOptionState, NodeOption<NodeList<MemberDecl>>> BODY = new STypeSafeTraversal<ObjectCreationExpr.State, SNodeOptionState, NodeOption<NodeList<MemberDecl>>>() {

		@Override
		public STree<?> doTraverse(State state) {
			return state.body;
		}

		@Override
		public ObjectCreationExpr.State doRebuildParentState(State state, STree<SNodeOptionState> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.New).withSpacingAfter(space()),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(TYPE),
			child(ARGS, Expr.argumentsShape),
			child(BODY, when(some(), element(MemberDecl.bodyShape)))
	);
}
