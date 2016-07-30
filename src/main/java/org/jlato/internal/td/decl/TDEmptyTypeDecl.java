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

import org.jlato.internal.bu.decl.SEmptyTypeDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyTypeDecl;
import org.jlato.tree.decl.TypeDecl;

/**
 * An empty type declaration.
 */
public class TDEmptyTypeDecl extends TDTree<SEmptyTypeDecl, TypeDecl, EmptyTypeDecl> implements EmptyTypeDecl {

	/**
	 * Returns the kind of this empty type declaration.
	 *
	 * @return the kind of this empty type declaration.
	 */
	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	/**
	 * Creates an empty type declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEmptyTypeDecl(TDLocation<SEmptyTypeDecl> location) {
		super(location);
	}

	/**
	 * Creates an empty type declaration with the specified child trees.
	 */
	public TDEmptyTypeDecl() {
		super(new TDLocation<SEmptyTypeDecl>(SEmptyTypeDecl.make()));
	}
}
