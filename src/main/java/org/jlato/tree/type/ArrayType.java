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

package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ArrayType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ArrayType instantiate(SLocation location) {
			return new ArrayType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayType(SLocation location) {
		super(location);
	}

	public ArrayType(NodeList<AnnotationExpr> annotations, Type componentType, NodeList<ArrayDim> dimensions) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations, componentType, dimensions)))));
	}

	public Type componentType() {
		return location.nodeChild(COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.nodeWithChild(COMPONENT_TYPE, componentType);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public VariableDeclaratorId withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	private static final int COMPONENT_TYPE = 1;
	private static final int DIMENSIONS = 1;

	public final static LexicalShape shape = composite(
			children(ANNOTATIONS),
			child(COMPONENT_TYPE),
			children(DIMENSIONS)
	);
}
