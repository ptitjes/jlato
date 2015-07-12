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

package org.jlato.tree.decl;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class VariableDeclaratorId extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclaratorId instantiate(SLocation location) {
			return new VariableDeclaratorId(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclaratorId(SLocation location) {
		super(location);
	}

	public VariableDeclaratorId(Name name, NodeList<ArrayDim> dims) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(name, dims)))));
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public VariableDeclaratorId withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<ArrayDim> dims() {
		return location.nodeChild(DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.nodeWithChild(DIMS, dims);
	}

	private static final int NAME = 0;
	private static final int DIMS = 1;

	public final static LexicalShape shape = composite(
			child(NAME),
			child(DIMS, list())
	);
}
