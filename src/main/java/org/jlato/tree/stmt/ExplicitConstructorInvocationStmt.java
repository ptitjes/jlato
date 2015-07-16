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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class ExplicitConstructorInvocationStmt extends TreeBase<SNodeState, Stmt, ExplicitConstructorInvocationStmt> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public ExplicitConstructorInvocationStmt instantiate(SLocation<SNodeState> location) {
			return new ExplicitConstructorInvocationStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ExplicitConstructorInvocationStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, NodeOption<Expr> expr, NodeList<Expr> args) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(dataOf(constructorKind(isThis)), treesOf(typeArgs, expr, args)))));
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGUMENTS, typeArgs);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGUMENTS, mutation);
	}

	public boolean isThis() {
		return location.data(CONSTRUCTOR_KIND) == ConstructorKind.This;
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.withData(CONSTRUCTOR_KIND, constructorKind(isThis));
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
		return location.safeTraversal(ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGUMENTS, args);
	}

	public ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGUMENTS, mutation);
	}

	private static final STraversal<SNodeState> TYPE_ARGUMENTS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> EXPR = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> ARGUMENTS = SNodeState.childTraversal(2);

	private static final int CONSTRUCTOR_KIND = 0;

	public final static LexicalShape shape = composite(
			when(childIs(EXPR, some()), composite(child(EXPR, element()), token(LToken.Dot))),
			child(TYPE_ARGUMENTS, Type.typeArgumentsShape),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((ConstructorKind) tree.state.data(CONSTRUCTOR_KIND)).token;
				}
			}),
			child(ARGUMENTS, Expr.argumentsShape),
			token(LToken.SemiColon)
	);

	private static ConstructorKind constructorKind(boolean isThis) {
		return isThis ? ConstructorKind.This : ConstructorKind.Super;
	}

	public enum ConstructorKind {
		This(LToken.This),
		Super(LToken.Super),
		// Keep last comma
		;

		protected final LToken token;

		ConstructorKind(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
