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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class IntersectionType extends TreeBase<SNodeState, Type, IntersectionType> implements Type {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public IntersectionType instantiate(SLocation<SNodeState> location) {
			return new IntersectionType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private IntersectionType(SLocation<SNodeState> location) {
		super(location);
	}

	public IntersectionType(NodeList<Type> types) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(types)))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(TYPES);
	}

	public IntersectionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(TYPES, types);
	}

	public IntersectionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPES, mutation);
	}

	private static final STraversal<SNodeState> TYPES = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			child(TYPES, Type.intersectionShape)
	);
}
