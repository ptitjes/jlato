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

package org.jlato.tree.stmt;

import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Modifiable;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.list;
import static org.jlato.internal.shapes.LexicalShape.Factory.none;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;

/**
 * @author Didier Villevalois
 */
public abstract class Stmt extends Tree implements Modifiable<Stmt> {

	protected Stmt(SLocation location) {
		super(location);
	}

	public  Stmt replace(Stmt replacement) {
		return location.replace(replacement);
	}

	public static final LexicalShape listShape = list(none().withSpacing(newLine()));
}
