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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ExplicitConstructorInvocationStmt extends TreeBase<ExplicitConstructorInvocationStmt.State, Stmt, ExplicitConstructorInvocationStmt> implements Stmt {

	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	private ExplicitConstructorInvocationStmt(SLocation<ExplicitConstructorInvocationStmt.State> location) {
		super(location);
	}

	public static STree<ExplicitConstructorInvocationStmt.State> make(STree<SNodeListState> typeArgs, boolean isThis, STree<SNodeOptionState> expr, STree<SNodeListState> args) {
		return new STree<ExplicitConstructorInvocationStmt.State>(new ExplicitConstructorInvocationStmt.State(typeArgs, isThis, expr, args));
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, NodeOption<Expr> expr, NodeList<Expr> args) {
		super(new SLocation<ExplicitConstructorInvocationStmt.State>(make(TreeBase.<SNodeListState>treeOf(typeArgs), isThis, TreeBase.<SNodeOptionState>treeOf(expr), TreeBase.<SNodeListState>treeOf(args))));
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public boolean isThis() {
		return location.safeProperty(THIS);
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.safePropertyReplace(THIS, (Boolean) isThis);
	}

	public ExplicitConstructorInvocationStmt setThis(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(THIS, mutation);
	}

	public NodeOption<Expr> expr() {
		return location.safeTraversal(EXPR);
	}

	public ExplicitConstructorInvocationStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ExplicitConstructorInvocationStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(ARGS);
	}

	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGS, args);
	}

	public ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGS, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<SNodeListState> typeArgs;

		public final boolean isThis;

		public final STree<SNodeOptionState> expr;

		public final STree<SNodeListState> args;

		State(STree<SNodeListState> typeArgs, boolean isThis, STree<SNodeOptionState> expr, STree<SNodeListState> args) {
			this.typeArgs = typeArgs;
			this.isThis = isThis;
			this.expr = expr;
			this.args = args;
		}

		public ExplicitConstructorInvocationStmt.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new ExplicitConstructorInvocationStmt.State(typeArgs, isThis, expr, args);
		}

		public ExplicitConstructorInvocationStmt.State setThis(boolean isThis) {
			return new ExplicitConstructorInvocationStmt.State(typeArgs, isThis, expr, args);
		}

		public ExplicitConstructorInvocationStmt.State withExpr(STree<SNodeOptionState> expr) {
			return new ExplicitConstructorInvocationStmt.State(typeArgs, isThis, expr, args);
		}

		public ExplicitConstructorInvocationStmt.State withArgs(STree<SNodeListState> args) {
			return new ExplicitConstructorInvocationStmt.State(typeArgs, isThis, expr, args);
		}

		@Override
		public Kind kind() {
			return Kind.ExplicitConstructorInvocationStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<ExplicitConstructorInvocationStmt.State> location) {
			return new ExplicitConstructorInvocationStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return TYPE_ARGS;
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
			ExplicitConstructorInvocationStmt.State state = (ExplicitConstructorInvocationStmt.State) o;
			if (!typeArgs.equals(state.typeArgs))
				return false;
			if (isThis != state.isThis)
				return false;
			if (!expr.equals(state.expr))
				return false;
			if (!args.equals(state.args))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + typeArgs.hashCode();
			result = 37 * result + (isThis ? 1 : 0);
			result = 37 * result + expr.hashCode();
			result = 37 * result + args.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeListState, NodeList<Type>>() {

		@Override
		protected STree<?> doTraverse(ExplicitConstructorInvocationStmt.State state) {
			return state.typeArgs;
		}

		@Override
		protected ExplicitConstructorInvocationStmt.State doRebuildParentState(ExplicitConstructorInvocationStmt.State state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	private static STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeOptionState, NodeOption<Expr>> EXPR = new STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		protected STree<?> doTraverse(ExplicitConstructorInvocationStmt.State state) {
			return state.expr;
		}

		@Override
		protected ExplicitConstructorInvocationStmt.State doRebuildParentState(ExplicitConstructorInvocationStmt.State state, STree<SNodeOptionState> child) {
			return state.withExpr(child);
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

	private static STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<ExplicitConstructorInvocationStmt.State, SNodeListState, NodeList<Expr>>() {

		@Override
		protected STree<?> doTraverse(ExplicitConstructorInvocationStmt.State state) {
			return state.args;
		}

		@Override
		protected ExplicitConstructorInvocationStmt.State doRebuildParentState(ExplicitConstructorInvocationStmt.State state, STree<SNodeListState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<ExplicitConstructorInvocationStmt.State, Boolean> THIS = new STypeSafeProperty<ExplicitConstructorInvocationStmt.State, Boolean>() {

		@Override
		protected Boolean doRetrieve(ExplicitConstructorInvocationStmt.State state) {
			return state.isThis;
		}

		@Override
		protected ExplicitConstructorInvocationStmt.State doRebuildParentState(ExplicitConstructorInvocationStmt.State state, Boolean value) {
			return state.setThis(value);
		}
	};

	public final static LexicalShape shape = composite(
			when(childIs(EXPR, some()), composite(child(EXPR, element()), token(LToken.Dot))),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).isThis ? LToken.This : LToken.Super;
				}
			}),
			child(ARGS, Expr.argumentsShape),
			token(LToken.SemiColon)
	);
}
