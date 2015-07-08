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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class ReferenceExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ReferenceExpr instantiate(SLocation location) {
			return new ReferenceExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ReferenceExpr(SLocation location) {
		super(location);
	}

	public ReferenceExpr(Expr scope, NodeList<Type> typeArguments, Name identifier) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, typeArguments, identifier)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public ReferenceExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ReferenceExpr withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	public Name identifier() {
		return location.nodeChild(IDENTIFIER);
	}

	public ReferenceExpr withIdentifier(Name identifier) {
		return location.nodeWithChild(IDENTIFIER, identifier);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGUMENTS = 1;
	private static final int IDENTIFIER = 2;

	public final static LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			children(TYPE_ARGUMENTS, token(LToken.Less), token(LToken.Comma).withSpacingAfter(space()), token(LToken.Greater)),
			child(IDENTIFIER)
	);
}
