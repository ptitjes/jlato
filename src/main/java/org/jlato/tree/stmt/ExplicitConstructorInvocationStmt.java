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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ExplicitConstructorInvocationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ExplicitConstructorInvocationStmt instantiate(SLocation location) {
			return new ExplicitConstructorInvocationStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ExplicitConstructorInvocationStmt(SLocation location) {
		super(location);
	}

	public ExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, Expr expr, NodeList<Expr> args) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(typeArgs, expr, args), dataOf(constructorKind(isThis))))));
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArgs);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.nodeMutateChild(TYPE_ARGUMENTS, mutation);
	}

	public boolean isThis() {
		return location.data(CONSTRUCTOR_KIND) == ConstructorKind.This;
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.withData(CONSTRUCTOR_KIND, constructorKind(isThis));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public ExplicitConstructorInvocationStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public ExplicitConstructorInvocationStmt withExpr(Mutation<Expr> mutation) {
		return location.nodeMutateChild(EXPR, mutation);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGUMENTS);
	}

	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGUMENTS, args);
	}

	public ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.nodeMutateChild(ARGUMENTS, mutation);
	}

	private static final int TYPE_ARGUMENTS = 0;
	private static final int EXPR = 1;
	private static final int ARGUMENTS = 2;

	private static final int CONSTRUCTOR_KIND = 0;

	public final static LexicalShape shape = composite(
			nonNullChild(EXPR, composite(child(EXPR), token(LToken.Dot))),
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
