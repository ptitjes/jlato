/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A variable declarator identifier.
 */
public class TDVariableDeclaratorId extends TDTree<SVariableDeclaratorId, Node, VariableDeclaratorId> implements VariableDeclaratorId {

	/**
	 * Returns the kind of this variable declarator identifier.
	 *
	 * @return the kind of this variable declarator identifier.
	 */
	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	/**
	 * Creates a variable declarator identifier for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDVariableDeclaratorId(TDLocation<SVariableDeclaratorId> location) {
		super(location);
	}

	/**
	 * Creates a variable declarator identifier with the specified child trees.
	 *
	 * @param name the name child tree.
	 * @param dims the dimensions child tree.
	 */
	public TDVariableDeclaratorId(Name name, NodeList<ArrayDim> dims) {
		super(new TDLocation<SVariableDeclaratorId>(SVariableDeclaratorId.make(TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(dims))));
	}

	/**
	 * Returns the name of this variable declarator identifier.
	 *
	 * @return the name of this variable declarator identifier.
	 */
	public Name name() {
		return location.safeTraversal(SVariableDeclaratorId.NAME);
	}

	/**
	 * Replaces the name of this variable declarator identifier.
	 *
	 * @param name the replacement for the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	public VariableDeclaratorId withName(Name name) {
		return location.safeTraversalReplace(SVariableDeclaratorId.NAME, name);
	}

	/**
	 * Mutates the name of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	public VariableDeclaratorId withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SVariableDeclaratorId.NAME, mutation);
	}

	/**
	 * Replaces the name of this variable declarator identifier.
	 *
	 * @param name the replacement for the name of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	public VariableDeclaratorId withName(String name) {
		return location.safeTraversalReplace(SVariableDeclaratorId.NAME, Trees.name(name));
	}

	/**
	 * Returns the dimensions of this variable declarator identifier.
	 *
	 * @return the dimensions of this variable declarator identifier.
	 */
	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SVariableDeclaratorId.DIMS);
	}

	/**
	 * Replaces the dimensions of this variable declarator identifier.
	 *
	 * @param dims the replacement for the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SVariableDeclaratorId.DIMS, dims);
	}

	/**
	 * Mutates the dimensions of this variable declarator identifier.
	 *
	 * @param mutation the mutation to apply to the dimensions of this variable declarator identifier.
	 * @return the resulting mutated variable declarator identifier.
	 */
	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SVariableDeclaratorId.DIMS, mutation);
	}
}
