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
import org.jlato.tree.decl.Decl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ObjectCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ObjectCreationExpr instantiate(SLocation location) {
			return new ObjectCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ObjectCreationExpr(SLocation location) {
		super(location);
	}

	public ObjectCreationExpr(Expr scope, QualifiedType type, NodeList<Type> typeArgs, NodeList<Expr> args, NodeList<Decl> body) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(scope, type, typeArgs, args, body)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public ObjectCreationExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public QualifiedType type() {
		return location.nodeChild(TYPE);
	}

	public ObjectCreationExpr withType(QualifiedType type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArgs);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGUMENTS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGUMENTS, args);
	}

	public NodeList<Decl> body() {
		return location.nodeChild(BODY);
	}

	public ObjectCreationExpr withBody(NodeList<Decl> body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int SCOPE = 0;
	private static final int TYPE = 1;
	private static final int TYPE_ARGUMENTS = 2;
	private static final int ARGUMENTS = 3;
	private static final int BODY = 4;

	public final static LexicalShape shape = composite(
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			token(LToken.New).withSpacingAfter(space()),
			child(TYPE_ARGUMENTS, Type.typeArgumentsShape),
			child(TYPE),
			child(ARGUMENTS, Expr.argumentsShape),
			nonNullChild(BODY,
					child(BODY, MemberDecl.bodyShape)
			)
	);
}
