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

package org.jlato.tree;

import org.jlato.rewrite.Matcher;
import org.jlato.rewrite.Substitution;
import org.jlato.rewrite.TypeSafeMatcher;

/**
 * @author Didier Villevalois
 */
public interface Tree {

	Tree parent();

	Tree root();

	boolean hasProblems();

	Iterable<Problem> problems();

	// Non-typed combinators

	/**
	 * Returns the leading comment strings of this tree, apart from documentation comments.
	 *
	 * @return the leading comment strings.
	 */
	String[] leadingComments();

	/**
	 * Returns the trailing comment strings of this tree.
	 *
	 * @return the trailing comment strings.
	 */
	String[] trailingComments();

	Substitution match(Matcher matcher);

	boolean matches(Matcher matcher);

	<U extends Tree> Iterable<U> findAll(TypeSafeMatcher<U> matcher);

	<U extends Tree> Iterable<U> leftFindAll(TypeSafeMatcher<U> matcher);

	<U extends Tree> Iterable<U> rightFindAll(TypeSafeMatcher<U> matcher);
}
