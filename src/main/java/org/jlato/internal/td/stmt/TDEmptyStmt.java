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

import org.jlato.internal.bu.stmt.SEmptyStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.stmt.EmptyStmt;
import org.jlato.tree.stmt.Stmt;

/**
 * An empty statement.
 */
public class TDEmptyStmt extends TDTree<SEmptyStmt, Stmt, EmptyStmt> implements EmptyStmt {

	/**
	 * Returns the kind of this empty statement.
	 *
	 * @return the kind of this empty statement.
	 */
	public Kind kind() {
		return Kind.EmptyStmt;
	}

	/**
	 * Creates an empty statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEmptyStmt(TDLocation<SEmptyStmt> location) {
		super(location);
	}

	/**
	 * Creates an empty statement with the specified child trees.
	 */
	public TDEmptyStmt() {
		super(new TDLocation<SEmptyStmt>(SEmptyStmt.make()));
	}
}
