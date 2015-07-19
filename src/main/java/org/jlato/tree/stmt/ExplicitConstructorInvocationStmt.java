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
import org.jlato.tree.Kind;
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

import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

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
		super(new SLocation<ExplicitConstructorInvocationStmt.State>(make(TreeBase.<SNodeListState>nodeOf(typeArgs), isThis, TreeBase.<SNodeOptionState>nodeOf(expr), TreeBase.<SNodeListState>nodeOf(args))));
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
		return location.safeProperty(IS_THIS);
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.safePropertyReplace(IS_THIS, (Boolean) isThis);
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
