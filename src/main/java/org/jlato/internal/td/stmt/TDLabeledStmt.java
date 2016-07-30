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

import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SLabeledStmt;
import org.jlato.internal.bu.stmt.SStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Trees;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.LabeledStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.util.Mutation;

/**
 * A labeled statement.
 */
public class TDLabeledStmt extends TDTree<SLabeledStmt, Stmt, LabeledStmt> implements LabeledStmt {

	/**
	 * Returns the kind of this labeled statement.
	 *
	 * @return the kind of this labeled statement.
	 */
	public Kind kind() {
		return Kind.LabeledStmt;
	}

	/**
	 * Creates a labeled statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDLabeledStmt(TDLocation<SLabeledStmt> location) {
		super(location);
	}

	/**
	 * Creates a labeled statement with the specified child trees.
	 *
	 * @param label the label child tree.
	 * @param stmt  the statement child tree.
	 */
	public TDLabeledStmt(Name label, Stmt stmt) {
		super(new TDLocation<SLabeledStmt>(SLabeledStmt.make(TDTree.<SName>treeOf(label), TDTree.<SStmt>treeOf(stmt))));
	}

	/**
	 * Returns the label of this labeled statement.
	 *
	 * @return the label of this labeled statement.
	 */
	public Name label() {
		return location.safeTraversal(SLabeledStmt.LABEL);
	}

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	public LabeledStmt withLabel(Name label) {
		return location.safeTraversalReplace(SLabeledStmt.LABEL, label);
	}

	/**
	 * Mutates the label of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	public LabeledStmt withLabel(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SLabeledStmt.LABEL, mutation);
	}

	/**
	 * Replaces the label of this labeled statement.
	 *
	 * @param label the replacement for the label of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	public LabeledStmt withLabel(String label) {
		return location.safeTraversalReplace(SLabeledStmt.LABEL, Trees.name(label));
	}

	/**
	 * Returns the statement of this labeled statement.
	 *
	 * @return the statement of this labeled statement.
	 */
	public Stmt stmt() {
		return location.safeTraversal(SLabeledStmt.STMT);
	}

	/**
	 * Replaces the statement of this labeled statement.
	 *
	 * @param stmt the replacement for the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	public LabeledStmt withStmt(Stmt stmt) {
		return location.safeTraversalReplace(SLabeledStmt.STMT, stmt);
	}

	/**
	 * Mutates the statement of this labeled statement.
	 *
	 * @param mutation the mutation to apply to the statement of this labeled statement.
	 * @return the resulting mutated labeled statement.
	 */
	public LabeledStmt withStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(SLabeledStmt.STMT, mutation);
	}
}
