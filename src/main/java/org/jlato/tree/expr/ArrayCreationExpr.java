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
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class ArrayCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayCreationExpr instantiate(SLocation location) {
			return new ArrayCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayCreationExpr(SLocation location) {
		super(location);
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimensionExpressions, NodeList<ArrayDim> dimensions, ArrayInitializerExpr initializer) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(type, dimensionExpressions, dimensions, initializer)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<ArrayDimExpr> dimensionExpressions() {
		return location.nodeChild(DIMENSION_EXPRESSIONS);
	}

	public ArrayCreationExpr withDimensionExpressions(NodeList<ArrayDimExpr> dimensionExpressions) {
		return location.nodeWithChild(DIMENSION_EXPRESSIONS, dimensionExpressions);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public ArrayCreationExpr withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	public ArrayInitializerExpr initializer() {
		return location.nodeChild(INITIALIZER);
	}

	public ArrayCreationExpr withInitializer(ArrayInitializerExpr initializer) {
		return location.nodeWithChild(INITIALIZER, initializer);
	}

	private static final int TYPE = 0;
	private static final int DIMENSION_EXPRESSIONS = 1;
	private static final int DIMENSIONS = 2;
	private static final int INITIALIZER = 3;

	public final static LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIMENSION_EXPRESSIONS, list()),
			child(DIMENSIONS, list()),
			nonNullChild(INITIALIZER,
					composite(
							none().withSpacing(space()),
							child(INITIALIZER)
					)
			)
	);
}
