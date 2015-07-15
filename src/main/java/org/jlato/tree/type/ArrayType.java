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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.STraversal;

public class ArrayType extends ReferenceType {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public ArrayType instantiate(SLocation location) {
			return new ArrayType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayType(SLocation<SNodeState> location) {
		super(location);
	}

	public ArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(componentType, dims)))));
	}

	public Type componentType() {
		return location.safeTraversal(COMPONENT_TYPE);
	}

	public ArrayType withComponentType(Type componentType) {
		return location.safeTraversalReplace(COMPONENT_TYPE, componentType);
	}

	public ArrayType withComponentType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(COMPONENT_TYPE, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMS, dims);
	}

	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMS, mutation);
	}

	private static final STraversal<SNodeState> COMPONENT_TYPE = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> DIMS = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			child(COMPONENT_TYPE),
			child(DIMS, list())
	);
}
