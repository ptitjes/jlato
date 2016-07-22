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

import org.jlato.tree.Tree;

/**
 * A tree that can hold a documentation comment (javadoc).
 *
 * @author Didier Villevalois
 */
public interface Documentable<T extends Tree> {

	/**
	 * Sets the documentation comment to the specified string.
	 * If a documentation comment already exists, then it is replaced.
	 *
	 * @param commentString the documentation comment string (without comment start and stop markers).
	 * @return this tree with the set documentation comment.
	 */
	T withDocComment(String commentString);

	/**
	 * Retrieves the documentation comment hold by this tree, if it exists.
	 *
	 * @return the documentation comment (without comment start and stop markers), or <code>null</code>.
	 */
	String docComment();
}
