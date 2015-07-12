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

public class CallExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public CallExpr instantiate(SLocation location) {
			return new CallExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CallExpr(SLocation location) {
		super(location);
	}

	public CallExpr(Expr scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(scope, typeArgs, name, args)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public CallExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public CallExpr withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public CallExpr withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> arguments() {
		return location.nodeChild(ARGUMENTS);
	}

	public CallExpr withArguments(NodeList<Expr> arguments) {
		return location.nodeWithChild(ARGUMENTS, arguments);
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
