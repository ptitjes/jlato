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

package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnknownType;

/**
 * An unknown type.
 */
public class TDUnknownType extends TDTree<SUnknownType, Type, UnknownType> implements UnknownType {

	/**
	 * Returns the kind of this unknown type.
	 *
	 * @return the kind of this unknown type.
	 */
	public Kind kind() {
		return Kind.UnknownType;
	}

	/**
	 * Creates an unknown type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDUnknownType(TDLocation<SUnknownType> location) {
		super(location);
	}

	/**
	 * Creates an unknown type with the specified child trees.
	 */
	public TDUnknownType() {
		super(new TDLocation<SUnknownType>(SUnknownType.make()));
	}
}
