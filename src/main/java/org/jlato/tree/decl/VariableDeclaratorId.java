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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.STraversal;

public class VariableDeclaratorId extends TreeBase<SNodeState> implements Tree {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public VariableDeclaratorId instantiate(SLocation location) {
			return new VariableDeclaratorId(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclaratorId(SLocation<SNodeState> location) {
		super(location);
	}

	public VariableDeclaratorId(Name name, NodeList<ArrayDim> dims) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(name, dims)))));
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public VariableDeclaratorId withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public VariableDeclaratorId withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
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

	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> DIMS = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			child(NAME),
			child(DIMS, list())
	);
}
