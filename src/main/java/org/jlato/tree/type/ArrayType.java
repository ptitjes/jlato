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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ArrayType extends TreeBase<ArrayType.State, ReferenceType, ArrayType> implements ReferenceType {

	public Kind kind() {
		return Kind.ArrayType;
	}

	private ArrayType(SLocation<ArrayType.State> location) {
		super(location);
	}

	public static STree<ArrayType.State> make(STree<Type.State> componentType, STree<SNodeListState> dims) {
		return new STree<ArrayType.State>(new ArrayType.State(componentType, dims));
	}

	public ArrayType(Type componentType, NodeList<ArrayDim> dims) {
		super(new SLocation<ArrayType.State>(make(TreeBase.<Type.State>nodeOf(componentType), TreeBase.<SNodeListState>nodeOf(dims))));
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

	public final static LexicalShape shape = composite(
			child(COMPONENT_TYPE),
			child(DIMS, list())
	);
}
