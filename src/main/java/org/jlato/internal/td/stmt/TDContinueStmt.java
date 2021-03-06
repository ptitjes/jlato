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

package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SContinueStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.ContinueStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A 'continue' statement.
 */
public class TDContinueStmt extends TDTree<SContinueStmt, Stmt, ContinueStmt> implements ContinueStmt {

	/**
	 * Returns the kind of this 'continue' statement.
	 *
	 * @return the kind of this 'continue' statement.
	 */
	public Kind kind() {
		return Kind.ContinueStmt;
	}

	/**
	 * Creates a 'continue' statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDContinueStmt(TDLocation<SContinueStmt> location) {
		super(location);
	}

	/**
	 * Creates a 'continue' statement with the specified child trees.
	 *
	 * @param id the identifier child tree.
	 */
	public TDContinueStmt(NodeOption<Name> id) {
		super(new TDLocation<SContinueStmt>(SContinueStmt.make(TDTree.<SNodeOption>treeOf(id))));
	}

	/**
	 * Returns the identifier of this 'continue' statement.
	 *
	 * @return the identifier of this 'continue' statement.
	 */
	public NodeOption<Name> id() {
		return location.safeTraversal(SContinueStmt.ID);
	}

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @param id the replacement for the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	public ContinueStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(SContinueStmt.ID, id);
	}

	/**
	 * Mutates the identifier of this 'continue' statement.
	 *
	 * @param mutation the mutation to apply to the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	public ContinueStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(SContinueStmt.ID, mutation);
	}

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @param id the replacement for the identifier of this 'continue' statement.
	 * @return the resulting mutated 'continue' statement.
	 */
	public ContinueStmt withId(Name id) {
		return location.safeTraversalReplace(SContinueStmt.ID, Trees.some(id));
	}

	/**
	 * Replaces the identifier of this 'continue' statement.
	 *
	 * @return the resulting mutated 'continue' statement.
	 */
	public ContinueStmt withNoId() {
		return location.safeTraversalReplace(SContinueStmt.ID, Trees.<Name>none());
	}
}
