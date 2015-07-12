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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class MethodInvocationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MethodInvocationExpr instantiate(SLocation location) {
			return new MethodInvocationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodInvocationExpr(SLocation location) {
		super(location);
	}

	public MethodInvocationExpr(Expr scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(scope, typeArgs, name, args)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public MethodInvocationExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArgs);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public MethodInvocationExpr withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGUMENTS);
	}

	public MethodInvocationExpr withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGUMENTS, args);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGUMENTS = 1;
	private static final int NAME = 2;
	private static final int ARGUMENTS = 3;

	public final static LexicalShape shape = composite(
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			child(TYPE_ARGUMENTS, Type.typeArgumentsShape),
			child(NAME),
			child(ARGUMENTS, Expr.argumentsShape)
	);
}
