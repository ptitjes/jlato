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

package org.jlato.tree;

import org.jlato.pattern.MatchVisitor;
import org.jlato.pattern.Matcher;

/**
 * @author Didier Villevalois
 */
public interface TreeCombinators<T extends Tree> extends Tree {

	<U extends Tree> T forAll(Matcher<? extends U> matcher, MatchVisitor<U> visitor);

	<U extends Tree> T leftForAll(Matcher<? extends U> matcher, MatchVisitor<U> visitor);

	<U extends Tree> T rightForAll(Matcher<? extends U> matcher, MatchVisitor<U> visitor);

	/**
	 * Inserts a leading comment to this tree with the specified comment string.
	 * This uses sensible defaults to chose between single-line or multi-line comment,
	 * depending on the context of this tree (expression, statement, ...).
	 *
	 * @param commentString the comment string (without comment start and stop markers).
	 * @return this tree with the inserted leading comment.
	 */
	T appendLeadingComment(String commentString);

	/**
	 * Inserts a leading comment to this tree with the specified comment string.
	 * This uses sensible defaults to chose between single-line or multi-line comment,
	 * depending on the context of this tree (expression, statement, ...).
	 *
	 * @param commentString the comment string (without comment start and stop markers).
	 * @param forceMultiLine whether to force the use of a single-line comment.
	 * @return this tree with the inserted leading comment.
	 */
	T appendLeadingComment(String commentString, boolean forceMultiLine);

	/**
	 * Inserts a trailing comment to this tree with the specified comment string.
	 * This uses sensible defaults to chose between single-line or multi-line comment,
	 * depending on the context of this tree (expression, statement, ...).
	 *
	 * @param commentString the comment string (without comment start and stop markers).
	 * @return this tree with the inserted trailing comment.
	 */
	T prependTrailingComment(String commentString);

	/**
	 * Inserts a trailing comment to this tree with the specified comment string.
	 * This uses sensible defaults to chose between single-line or multi-line comment,
	 * depending on the context of this tree (expression, statement, ...).
	 *
	 * @param commentString the comment string (without comment start and stop markers).
	 * @param forceMultiLine whether to force the use of a single-line comment.
	 * @return this tree with the inserted trailing comment.
	 */
	T prependTrailingComment(String commentString, boolean forceMultiLine);

	T prependLeadingNewLine();

	T appendLeadingNewLine();

	T appendTrailingNewLine();
}
