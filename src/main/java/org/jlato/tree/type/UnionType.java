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

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class UnionType extends TreeBase<UnionType.State, Type, UnionType> implements Type {

	public Kind kind() {
		return Kind.UnionType;
	}

	private UnionType(SLocation<UnionType.State> location) {
		super(location);
	}

	public static STree<UnionType.State> make(STree<SNodeListState> types) {
		return new STree<UnionType.State>(new UnionType.State(types));
	}

	public UnionType(NodeList<Type> types) {
		super(new SLocation<UnionType.State>(make(TreeBase.<SNodeListState>nodeOf(types))));
	}

	public NodeList<Type> types() {
		return location.safeTraversal(TYPES);
	}

	public UnionType withTypes(NodeList<Type> types) {
		return location.safeTraversalReplace(TYPES, types);
	}

	public UnionType withTypes(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPES, mutation);
	}

	public final static LexicalShape shape = composite(
			child(TYPES, Type.unionShape)
	);
}
